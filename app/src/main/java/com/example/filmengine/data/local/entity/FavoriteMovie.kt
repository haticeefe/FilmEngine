package com.example.filmengine.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// @Entity: Bu class'ın bir veritabanı TABLOSU olduğunu Room'a söylüyor.
// tableName: Bu tablonun SQLite içindeki gerçek adı "favorite_movies" olacak.
@Entity(tableName = "favorite_movies")
data class FavoriteMovie(

    // @PrimaryKey: Her satırı benzersiz şekilde tanımlayan alan (birincil anahtar).
    // TMDB'nin verdiği film id'sini kullanıyoruz, çünkü zaten benzersiz.
    // autoGenerate = false çünkü id'yi biz kendimiz (API'den gelen) veriyoruz, Room üretmesin.

    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double
)