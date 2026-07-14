package com.example.filmengine.data.remote

import com.example.filmengine.BuildConfig
import com.example.filmengine.data.remote.dto.MovieDetailDto
import com.example.filmengine.data.remote.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {

    // Popüler filmleri çeken endpoint

    @GET("movie/popular")
    //suspend fun; Coroutines  yapısını kullandığını belirtir
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): MovieResponseDto

    // Film detaylarını ID'ye göre getiriyoruz path değişkeni önemli
    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US"
    ): MovieDetailDto
}