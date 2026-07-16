package com.example.filmengine.ui.navigation

// Uygulamadaki ekranların isimlerini tek bir yerde topluyoruz.
// Böylece ekran geçişlerinde aynı isimleri tekrar yazmamıza gerek kalmıyor.
sealed class Screen(val route: String) {

    // Popüler filmler ekranı.
    object Popular : Screen("popular")

    // Favori filmler ekranı.
    object Favorite : Screen("favorite")

    // Film detay ekranı.
    // Bu ekrana giderken film id'si de gönderilir.
    object Detail : Screen("detail/{movieId}") {

        // Film id'sini route'a ekleyerek detay ekranına geçiş yapar.
        fun createRoute(movieId: Int) = "detail/$movieId"
    }
}


//Bu kod, uygulamadaki ekranların (Popüler Filmler, Favoriler ve Detay)
//adreslerini tanımlar ve ekranlar arasında geçiş yapılmasını sağlar.