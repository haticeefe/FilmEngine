package com.example.filmengine.data.remote

import com.example.filmengine.BuildConfig
import com.example.filmengine.data.remote.dto.MovieDetailDto
import com.example.filmengine.data.remote.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path   //doldurmak için mesela movie 550 gibi id yi doldurmak
import retrofit2.http.Query  //sorgu parametresi eklemk için
import com.example.filmengine.data.remote.dto.CreditsDto;



interface TmdbApiService {

    // Popüler filmleri getiren API isteği.
    @GET("movie/popular")

    // suspend,bu fonksiyon arka planda çalışır.
    // Böylece API isteği yapılırken uygulama donmaz.
    suspend fun getPopularMovies(

        // API'ye erişebilmek için gerekli anahtar.
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,

        // Kaçıncı sayfadaki filmlerin getirileceğini belirtir.
        @Query("page") page: Int,

        // Sonuçların hangi dilde geleceğini belirtir.
        @Query("language") language: String = "en-US"

    ): MovieResponseDto

    // Seçilen filmin detaylarını getirir.
    @GET("movie/{movie_id}")

    // movieId değeri URL'deki {movie_id} yerine yazılır.
    // Örneğin: movie/550
    suspend fun getMovieDetail(

        @Path("movie_id") movieId: Int,

        // API anahtarı.
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,

        // Sonuçların hangi dilde geleceğini belirtir.
        @Query("language") language: String = "en-US"

    ): MovieDetailDto



    //Filmin oyuncu kadrosunu getirmek için getMovieCredits() fonksiyonu eklendi
    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US"
    ): CreditsDto



    // 3. Filme benzer önerileri getirmek için getMovieRecommendations() fonksiyonu eklendi.
    @GET("movie/{movie_id}/recommendations")
    suspend fun getMovieRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MovieResponseDto

}

//Bu interface TMDB API'sine gönderilecek istekleri tanımlıyor.
// Popüler filmleri ve film detaylarını almak için gerekli API fonksiyonları burada bulunuyor.
// Retrofit de bu tanımları kullanarak internet isteklerini otomatik olarak oluşturuyor
// ve gelen verileri DTO sınıflarına dönüştürüyor.


// Popüler filmler, film detayı, oyuncu kadrosu ve benzer filmler için
// gerekli API fonksiyonları burada bulunuyor.