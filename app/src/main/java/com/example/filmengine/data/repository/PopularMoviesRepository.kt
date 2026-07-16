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


// Bu sınıf popüler filmlerle ilgili veri işlemlerini yönetir.
// Hilt sayesinde gerekli olan API bağlantısı otomatik olarak alınır.
class PopularMoviesRepository @Inject constructor(
    private val api: TmdbApiService
) {

    // Popüler filmleri sayfalı şekilde almak için kullanılır.

    suspend fun getMovieDetail(movieId: Int): MovieDetailDto {
        return api.getMovieDetail(movieId)
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


//veri internetten geliyor.
//yani kısaca Popüler filmleri TMDB API'den alıp
// Paging 3 kullanarak sayfa sayfa yüklenmesini sağlayan Repository sınıfıdır.