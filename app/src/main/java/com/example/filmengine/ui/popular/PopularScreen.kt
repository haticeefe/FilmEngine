package com.example.filmengine.ui.popular

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Button


// Popüler filmler ekranını oluşturan Compose fonksiyonu.
// Bir filme tıklandığında yapılacak işlem dışarıdan gönderilir.

@Composable
fun PopularScreen(
    onMovieClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        //deneme butonu çalışıyor mu diye
        Button(onClick = { onMovieClick(550) }) {
            Text("Popular Movies Screen (fake movie'ye git: 550)")
        }
    }
}




//uygulamadaki Popüler Filmler ekranını oluşturur ve bir filme tıklanması
// durumunda yapılacak işlemi dışarıdan alacak şekilde hazırlar.
