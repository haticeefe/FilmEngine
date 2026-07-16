package com.example.filmengine.ui.popular


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey


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
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp)
    ) {

        // Filmleri liste halinde ekrana ekler.
        // Her film kendi id'si ile takip edilir.
        items(
            count = movies.itemCount,
            key = movies.itemKey { it.id }
        ) { index ->

            val movie = movies[index]

            if (movie != null) {

                // Şimdilik filmin adı ve puanı gösteriliyor.
                // Daha sonra buraya film kartı eklenecek.
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Text(text = movie.title)
                    Text(text = "⭐ ${movie.vote_average}")
                }
            }
        }
    }
}


//uygulamadaki Popüler Filmler ekranını oluşturur ve bir filme tıklanması
// durumunda yapılacak işlemi dışarıdan alacak şekilde hazırlar.
//2. gerçek hali tamamlandı