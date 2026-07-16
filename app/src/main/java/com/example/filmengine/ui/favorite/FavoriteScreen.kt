package com.example.filmengine.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.filmengine.ui.navigation.Screen

// Favori filmler ekranını oluşturan Compose fonksiyonu.

@Composable
fun FavoriteScreen(
    onMovieClick: (Int) -> Unit //tıklandığında filmin idsini alan parametre
){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Favorite Screen")
    }
}


//favori filmler ekranını oluşturur ve bir filme tıklanması
// durumunda yapılacak işlemi dışarıdan alacak şekilde hazırlar.
