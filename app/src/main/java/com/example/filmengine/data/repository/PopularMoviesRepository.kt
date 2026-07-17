package com.example.filmengine.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.filmengine.data.remote.PopularMoviesPagingSource
import com.example.filmengine.data.remote.TmdbApiService
import com.example.filmengine.data.remote.dto.MovieDto
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.example.filmengine.data.remote.dto.MovieDetailDto
import com.example.filmengine.data.remote.dto.CastMemberDto;



// Bu sınıf popüler filmlerle ilgili veri işlemlerini yönetir.
// Hilt sayesinde gerekli olan API bağlantısı otomatik olarak alınır.
class PopularMoviesRepository @Inject constructor(
    private val api: TmdbApiService
) {

    // Popüler filmleri sayfalı şekilde almak için kullanılır.

    suspend fun getMovieDetail(movieId: Int): MovieDetailDto {
        return api.getMovieDetail(movieId)
    }
    //oyuncu kadrosu
    suspend fun getMovieCredits(movieId: Int): List<CastMemberDto> {
        return api.getMovieCredits(movieId).cast
    }

    // 3. Filme benzer önerileri almak için getMovieRecommendations() fonksiyonu eklendi.
    suspend fun getMovieRecommendations(movieId: Int): List<MovieDto> {
        return api.getMovieRecommendations(movieId).results
    }
    fun getPopularMovies(): Flow<PagingData<MovieDto>> {

        return Pager(

            // Paging davranışını ayarladığımız bölüm.
            config = PagingConfig(

                // Her sayfada kaç film yükleneceğini belirler.
                pageSize = 20,

                // Liste sonuna yaklaşınca yeni sayfanın önceden yüklenmesini sağlar.
                prefetchDistance = 5,

                // Yüklenmeyen veriler için boş alan göstermeyi kapatır.
                enablePlaceholders = false
            ),

            // Film verilerini nereden alacağını belirtir.
            pagingSourceFactory = {
                PopularMoviesPagingSource(api)
            }

        ).flow
    }
}


// Popüler filmleri, film detayını, oyuncu kadrosunu ve
// benzer film önerilerini TMDB API'den alıp uygulamanın
// geri kalanına sunan Repository sınıfıdır.
