package com.example.filmengine

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint

// Bu annotation, Hilt'e bu Activity'de dependency kullanabileceğimizi söyler.
// Yani ileride ViewModel Repository gibi yapıları Hilt üzerinden kolayca kullanabilmek için eklenir.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Uygulamanın ekranın tamamını kullanmasını sağlar.
        enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        // Ekran tam kullanıldığı için bazı içerikler üstteki veya alttaki sistem çubuklarının altında kalabilir.
        // Bu kod, sistem çubuklarının kapladığı alanı hesaplayıp ekrana uygun boşluk ekliyor

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}