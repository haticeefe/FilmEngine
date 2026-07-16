package com.example.filmengine.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.filmengine.data.local.dao.FavoriteMovieDao
import com.example.filmengine.data.local.entity.FavoriteMovie

// Room'a bunun veritabanı sınıfı olduğunu söylüyoruz.
// entities: Veritabanında hangi tabloların olacağını belirtiyoruz.
// version: Veritabanının sürüm numarası.
// exportSchema: Şema dosyası oluşturmasını istemediğimiz için false.
@Database(
    entities = [FavoriteMovie::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    // FavoriteMovieDao'ya buradan erişiyoruz.
    // Room bu fonksiyonun içini kendisi oluşturuyor.
    abstract fun favoriteMovieDao(): FavoriteMovieDao

}

// Bu sınıf, uygulamanın Room veritabanını oluşturur.
// Favori filmlerle ilgili işlemleri yapabilmek için FavoriteMovieDao'ya erişim sağlar.