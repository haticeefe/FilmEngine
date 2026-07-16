package com.example.filmengine

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FilmEngineApplication : Application()


//Bu sınıfın içine hiçbir şey yazmamıza gerek yok sadece
// @HiltAndroidApp annotation'ı ve Application'dan miras alması yeterli
// Hilt geri kalan her şeyi arka planda otomatik hallediyor.


//kısaca Bu sınıf, Hilt'i uygulamada başlatır.