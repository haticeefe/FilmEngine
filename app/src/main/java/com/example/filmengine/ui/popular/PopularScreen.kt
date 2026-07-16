package com.example.filmengine.ui.popular


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.filmengine.BuildConfig
import com.example.filmengine.ui.common.MovieCard
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues



// Popüler filmler ekranını oluşturan Compose fonksiyonu.
// Bir filme tıklandığında yapılacak işlem dışarıdan gönderilir.

@Composable
fun PopularScreen(
    onMovieClick: (Int) -> Unit,

    // Hilt sayesinde ViewModel otomatik olarak oluşturulur.
    viewModel: PopularViewModel = hiltViewModel()
) {

    // ViewModel'den gelen film listesini ekranda kullanıma hazır hale getirir.
    val movies = viewModel.popularMovies.collectAsLazyPagingItems()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        // Filmleri liste halinde ekrana ekler.
        // Her film kendi id'si ile takip edilir.
        items(
            count = movies.itemCount,
            key = movies.itemKey { it.id }
        ) { index ->

            val movie = movies[index]
            if (movie != null) {
                MovieCard(
                    title = movie.title,
                    posterUrl = movie.poster_path?.let { "${BuildConfig.TMDB_IMAGE_BASE_URL}$it" },
                    rating = movie.vote_average,
                    onClick = { onMovieClick(movie.id) },
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}


//uygulamadaki Popüler Filmler ekranını oluşturur ve bir filme tıklanması
// durumunda yapılacak işlemi dışarıdan alacak şekilde hazırlar.
//2. gerçek hali tamamlandı