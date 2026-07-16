package com.example.filmengine.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Bu sınıfın Room'da bir tablo olduğunu belirtir.
// favorite_movies, veritabanında oluşturulacak tablonun adıdır.
@Entity(tableName = "favorite_movies")
data class FavoriteMovie(

    // Bu alan tablonun birincil anahtarıdır (Primary Key).
    // Her filmin kendine ait benzersiz bir id'si vardır.
    // autoGenerate = false olduğu için id'yi Room değil, API'den gelen değer belirler.
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    // Filmin adı.
    val title: String,

    // Filmin açıklaması.
    val overview: String,

    // Filmin afiş görselinin yolu.
    val posterPath: String?,

    // Filmin arka plan görselinin yolu.
    val backdropPath: String?,

    // Filmin çıkış tarihi.
    val releaseDate: String?,

    // Filmin puanı.
    val voteAverage: Double
)

// Bu sınıf, veritabanında saklanacak film bilgilerini tutar.