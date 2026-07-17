package com.example.filmengine.ui.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.filmengine.BuildConfig

@Composable
fun DetailScreen(
    movieId: Int,
    onBackToPopular: () -> Unit,
    viewModel: DetailViewModel = hiltViewModel()
) {
    // NOT: sistem geri tuşuna basılınca nereden gelinirse gelinsin Popular'a dön
    BackHandler { onBackToPopular() }

    LaunchedEffect(movieId) {
        viewModel.loadMovie(movieId)
    }

    val movie by viewModel.movieDetail.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

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
            }
        }
    }
}