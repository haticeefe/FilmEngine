package com.example.filmengine.data.local

import android.content.Context
import androidx.room.Room
import com.example.filmengine.data.local.dao.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Bu sınıf Room veritabanı ile ilgili gerekli nesneleri Hilt'e sağlar.
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // AppDatabase oluşturuyor.
    // Veritabanını oluşturabilmek için Context'e ihtiyaç vardır.
    // @ApplicationContext sayesinde uygulamanın Context'i kullanılır.

    @Provides //Bu nesneyi gerektiğinde bu fonksiyonla oluştur demek
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "filmengine_database" // Telefonda oluşturulacak veritabanının adı.
        ).build()
    }

    // FavoriteMovieDao oluşturuyor.
    // Böylece veritabanı işlemlerini yapabiliyoruz.
    // AppDatabase'i Hilt otomatik olarak buraya gönderiyor.
    @Provides
    @Singleton
    fun provideFavoriteMovieDao(appDatabase: AppDatabase): FavoriteMovieDao {
        return appDatabase.favoriteMovieDao()
    }
}


//room-hilt ilişkisi
//Room veritabanını yönetir,
// Hilt ise Room'u kullanabilmek için gerekli nesneleri otomatik oluşturup yönetir.