package com.example.filmengine.data.remote.dto

data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String?,
    val vote_average: Double,
    val runtime: Int?,
    val genres: List<GenreDto>
)

data class GenreDto(
    val id: Int,
    val name: String
)



//Bu dosya, API'den gelen film detaylarını ve film türlerini Kotlin nesnelerine dönüştürmek
// için kullanılan DTO sınıflarını içeriyor.