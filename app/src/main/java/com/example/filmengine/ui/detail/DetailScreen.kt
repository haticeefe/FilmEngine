package com.example.filmengine.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier  //Compose bileşenlerinin görünüm özelliklerini düzenlemek için kullanılır.

// Film detay ekranını oluşturan Compose fonksiyonu.
// Seçilen filmin id bilgisi bu ekrana gönderilir.

@Composable
fun DetailScreen(
    movieId: Int
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Detail Screen - Movie ID: $movieId")
    }
}


//bu kod seçilen filmin ID bilgisini alarak basit bir
// film detay ekranı oluşturur ve bu bilgiyi ekranda gösterir.