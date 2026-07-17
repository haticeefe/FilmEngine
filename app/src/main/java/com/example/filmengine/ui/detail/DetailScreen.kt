package com.example.filmengine.ui.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.filmengine.BuildConfig
import com.example.filmengine.data.remote.dto.CastMemberDto
import com.example.filmengine.data.remote.dto.MovieDto
import androidx.compose.foundation.clickable

@Composable
fun DetailScreen(
    movieId: Int,
    onBackToPopular: () -> Unit,
    onMovieClick: (Int) -> Unit = {},
    viewModel: DetailViewModel = hiltViewModel()
) {
    BackHandler { onBackToPopular() }

    LaunchedEffect(movieId) {
        viewModel.loadMovie(movieId)
    }

    val movie by viewModel.movieDetail.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()
    val cast by viewModel.cast.collectAsState()
    val recommendations by viewModel.recommendations.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        val detail = movie

        if (detail == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    Box {
                        AsyncImage(
                            model = detail.backdrop_path?.let { "${BuildConfig.TMDB_IMAGE_BASE_URL}$it" },
                            contentDescription = detail.title,
                            modifier = Modifier.fillMaxWidth().height(220.dp),
                            contentScale = ContentScale.Crop
                        )

                        IconButton(
                            onClick = onBackToPopular,
                            modifier = Modifier.align(Alignment.TopStart).padding(12.dp)
                        ) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Geri", tint = Color.White)
                        }

                        FloatingActionButton(
                            onClick = { viewModel.toggleFavorite() },
                            modifier = Modifier.align(Alignment.TopEnd).padding(16.dp),
                            containerColor = MaterialTheme.colorScheme.primary
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favori"
                            )
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = detail.title, style = MaterialTheme.typography.headlineSmall)

                        Spacer(modifier = Modifier.height(4.dp))
                        Row {
                            detail.release_date?.take(4)?.let { Text(text = "$it  •  ") }
                            Text(text = "⭐ ${detail.vote_average}")
                            detail.runtime?.let { Text(text = "  •  ${it} dk") }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Overview", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = detail.overview, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                // Oyuncu kadrosu bölümü — sadece veri geldiyse gösterilir.
                if (cast.isNotEmpty()) {
                    item {
                        Text(
                            text = "Starring",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(cast, key = { it.id }) { member ->
                                CastItem(member)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Benzer filmler bölümü — sadece veri geldiyse gösterilir.
                if (recommendations.isNotEmpty()) {
                    item {
                        Text(
                            text = "Similar To This",
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(recommendations, key = { it.id }) { similar ->
                                SimilarMovieItem(similar) { onMovieClick(similar.id) }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

// Tek bir oyuncuyu (yuvarlak fotoğraf + isim) gösteren küçük bileşen.
@Composable
private fun CastItem(member: CastMemberDto) {
    Column(
        modifier = Modifier.width(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = member.profile_path?.let { "${BuildConfig.TMDB_IMAGE_BASE_URL}$it" },
            contentDescription = member.name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = member.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Benzer filmler satırındaki tek bir film posterini gösteren küçük bileşen.
@Composable
private fun SimilarMovieItem(movie: MovieDto, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(110.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = movie.poster_path?.let { "${BuildConfig.TMDB_IMAGE_BASE_URL}$it" },
            contentDescription = movie.title,
            modifier = Modifier
                .width(110.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// Bu ekran, seçilen filmin tüm detaylarını, oyuncu kadrosunu ve benzer film
// önerilerini gösterir. Kullanıcı filmi favorilere ekleyip çıkarabilir,
// benzer bir filme tıklarsa o filmin detayına geçilir.