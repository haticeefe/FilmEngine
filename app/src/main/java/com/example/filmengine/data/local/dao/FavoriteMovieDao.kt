package com.example.filmengine.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmengine.data.local.entity.FavoriteMovie
import kotlinx.coroutines.flow.Flow //flow listeyi dürekli dinler

// Room'a bunun veritabanı işlemleri yapan bir DAO olduğunu söylüyoruz.
@Dao
interface FavoriteMovieDao {

    // Filme favorilere ekleme işlemi yapıyor.
    // Eğer aynı id'ye sahip film varsa hata vermek yerine onu güncelliyor.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(movie: FavoriteMovie)

    // Filmi favorilerden siliyor.
    @Delete
    suspend fun deleteFavorite(movie: FavoriteMovie)

    // Veritabanındaki tüm favori filmleri getiriyor.
    // Flow kullandığımız için favoriler değiştiğinde liste de otomatik güncelleniyor.
    @Query("SELECT * FROM favorite_movies ORDER BY id DESC")
    fun getAllFavorites(): Flow<List<FavoriteMovie>>

    // Verilen id'ye sahip film favorilerde var mı diye kontrol ediyor.
    // Varsa filmi, yoksa null döndürüyor.
    @Query("SELECT * FROM favorite_movies WHERE id = :movieId LIMIT 1")
    suspend fun getFavoriteById(movieId: Int): FavoriteMovie?

}

//suspend fun:Veritabanına film ekleyen ve
// coroutine içinde çalışması gereken bir fonksiyon.

//kısaca  veritabanı üzerinde CRUD