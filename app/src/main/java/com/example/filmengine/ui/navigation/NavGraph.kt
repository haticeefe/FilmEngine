package com.example.filmengine.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument  //ekranda gösterilcek parametreler için
import com.example.filmengine.ui.detail.DetailScreen
import com.example.filmengine.ui.favorite.FavoriteScreen
import com.example.filmengine.ui.popular.PopularScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {

    // Uygulamadaki ekranlar burada tanımlanır.
    // İlk açılan ekran Popüler Filmler ekranıdır.

    NavHost(
        navController = navController,
        startDestination = Screen.Popular.route //startdestination başlangıç ekranı
    ) {

        // Popüler Filmler ekranı
        //composable bloklar ile yeni ekran ekliyoruz.

        composable(route = Screen.Popular.route) {
            PopularScreen(
                onMovieClick = { movieId ->
                    // Filme tıklanınca detay ekranına geçilir.
                    navController.navigate(Screen.Detail.createRoute(movieId))
                }
            )
        }

        // Favoriler ekranı
        composable(route = Screen.Favorite.route) {
            FavoriteScreen(
                onMovieClick = { movieId ->
                    // Filme tıklanınca detay ekranına geçilir.
                    navController.navigate(Screen.Detail.createRoute(movieId))
                }
            )
        }

        // Detay ekranı
        // Bu ekran film id bilgisini alır.

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            // Gönderilen film id bilgisi alınır.

            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1

            DetailScreen(
                movieId = movieId,
                // Geri tuşuna basılınca çalışacak fonksiyon: her zaman Popular'a döner
                onBackToPopular = {
                    navController.navigate(Screen.Popular.route) {
                        // Popular ekranını stack'ten tamamen çıkarıp yeniden ekliyoruz,
                        // böylece Favorite'ten gelinmiş olsa bile geri tuşu
                        // Favorite'e değil Popular'a gider

                        popUpTo(Screen.Popular.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                // Benzer filmler bölümünden bir filme tıklanınca
                // yeni bir Detail ekranı üstüne eklenir (normal push davranışı).

                onMovieClick = { newMovieId ->
                    navController.navigate(Screen.Detail.createRoute(newMovieId))
                }
            )
        }
    }
}

// Bu dosya uygulamadaki tüm ekran geçişlerini (Popular, Favorite, Detail) yönetir.
// NavController artık MainScreen'den geldiği için tüm ekranlar
// (hem NavGraph hem de alt navigasyon barı) aynı geçmişi (back stack) paylaşır.