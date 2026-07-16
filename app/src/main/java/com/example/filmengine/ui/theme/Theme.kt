package com.example.filmengine.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


//uygulamada kullanacağım renkleri tanımladım.

private val DarkBackground = Color(0xFF0D0F14)
private val DarkSurface = Color(0xFF1A1D24)
private val AccentRed = Color(0xFFE63946)
private val TextPrimary = Color(0xFFF5F5F5)
private val TextSecondary = Color(0xFFA0A0A8)


//karanlık tema ayarları; zaten sadece karanlık tema kullanıcam.

private val FilmEngineDarkColorScheme = darkColorScheme(
    background = DarkBackground,
    surface = DarkSurface,
    primary = AccentRed,
    onBackground = TextPrimary,
    onSurface = TextPrimary,
    secondary = TextSecondary
)


//genel temayı belirleyen compose fonks.

@Composable
fun FilmEngineTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = FilmEngineDarkColorScheme,
        content = content
    )
}

//karanlık tema yapısını ve kullanılacak renk paletini oluşturarak
// tüm Compose ekranlarında
// aynı tasarımın kullanılmasını sağlayacak ksıım