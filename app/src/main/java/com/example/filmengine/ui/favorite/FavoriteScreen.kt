package com.example.filmengine.ui.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.filmengine.BuildConfig
import com.example.filmengine.ui.common.MovieCard

// Favori filmler ekranını oluşturan Compose fonksiyonu.
@Composable
fun FavoriteScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    // ViewModel'deki favori listesini dinliyoruz, liste her değiştiğinde ekran güncellenir
    val favorites by viewModel.favorites.collectAsState()

    if (favorites.isEmpty()) {
        // Hiç favori yoksa kullanıcıya bilgi mesajı göster
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Henüz favori film eklemedin", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Popular ekranındaki ile aynı MovieCard bileşenini tekrar kullanıyoruz
            items(favorites, key = { it.id }) { movie ->
                MovieCard(
                    title = movie.title,
                    posterUrl = movie.posterPath?.let { "${BuildConfig.TMDB_IMAGE_BASE_URL}$it" },
                    rating = movie.voteAverage,
                    onClick = { onMovieClick(movie.id) },
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}

// Bu ekran, Room veritabanındaki favori filmleri listeler.
// İnternet olmasa bile veriler yerelden geldiği için sorunsuz çalışır.
// Bir karta tıklanırsa DetailScreen'e yönlendirme yapılır.