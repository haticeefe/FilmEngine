package com.example.filmengine.data.repository

import com.example.filmengine.data.local.dao.FavoriteMovieDao
import com.example.filmengine.data.local.entity.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject //Hilt'e bu nesneyi benim yerime oluştur ve buraya ver demek

// Bu sınıf, favori filmlerle ilgili işlemleri yapar.
// @Inject sayesinde Hilt, FavoriteMovieDao'yu otomatik olarak oluşturup buraya verir.
class FavoriteRepository @Inject constructor(
    private val dao: FavoriteMovieDao
) {

    // Veritabanındaki tüm favori filmleri getirir.
    // Flow kullandığımız için veriler değiştiğinde liste otomatik güncellenir.
    fun getAllFavorites(): Flow<List<FavoriteMovie>> {
        return dao.getAllFavorites()
    }

    // Bir filmi favorilere ekler.
    suspend fun addFavorite(movie: FavoriteMovie) {
        dao.insertFavorite(movie)
    }

    // Bir filmi favorilerden siler.
    suspend fun removeFavorite(movie: FavoriteMovie) {
        dao.deleteFavorite(movie)
    }

    // Filmin favorilerde olup olmadığını kontrol eder.
    // Eğer varsa true, yoksa false döner.
    suspend fun isFavorite(movieId: Int): Boolean {
        return dao.getFavoriteById(movieId) != null
    }
}

//Telefon içindeki veritabanından gelir veri room ile çalışır