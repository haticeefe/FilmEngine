package com.example.filmengine

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.filmengine.ui.theme.FilmEngineTheme
import com.example.filmengine.ui.navigation.MainScreen



import dagger.hilt.android.AndroidEntryPoint

// Bu annotation, Hilt'e bu Activity'de dependency kullanabileceğimizi söyler.
// Yani ileride ViewModel Repository gibi yapıları Hilt üzerinden kolayca kullanabilmek için eklenir.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {  //compose için değişti
            FilmEngineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()

                }
            }
        }
    }

}