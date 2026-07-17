package com.example.filmengine.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding



// Alt navigasyon barındaki her bir sekmeyi temsil eden basit bir veri yapısı.
private data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private val bottomNavItems = listOf(
    BottomNavItem(Screen.Popular.route, "Popular", Icons.Filled.Home),
    BottomNavItem(Screen.Favorite.route, "Favorites", Icons.Filled.Favorite)
)

// Uygulamanın ana iskeleti: içinde hem NavGraph'ı hem de alt navigasyon barını barındırır.
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    // Şu an hangi ekranda olduğumuzu takip ediyoruz,
    // hem seçili sekmeyi işaretlemek hem de Detail ekranında bar'ı gizlemek için kullanıyoruz.
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Detay ekranındaysak alt bar'ı gösterme, sadece Popular/Favorite'te göster.
    val showBottomBar = currentRoute == Screen.Popular.route || currentRoute == Screen.Favorite.route

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                // Aynı sekmeye tekrar tekrar basılınca yeni bir kopya
                                // oluşturulmasını engelliyoruz, geçmişi de biriktirmiyoruz.

                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavGraph(navController = navController)
        }
    }
}

// Bu ekran, uygulamanın tamamını saran ana kapsayıcıdır.
// Popular ve Favorite sekmeleri arasında geçiş yapılmasını sağlayan
// alt navigasyon barını yönetir, Detail ekranında ise bu barı gizler.

