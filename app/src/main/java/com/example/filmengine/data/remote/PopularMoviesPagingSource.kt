package com.example.filmengine.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.filmengine.data.remote.dto.MovieDto

// Bu sınıf, popüler filmleri internetten sayfa sayfa çekmek için kullanılır.
// PagingSource sayesinde filmler listeye parça parça yüklenir.
class PopularMoviesPagingSource(
    private val api: TmdbApiService
) : PagingSource<Int, MovieDto>() {


    // Verileri yüklemek için kullanılan fonksiyon.
    // Kullanıcı listeyi aşağı kaydırdığında yeni sayfa verileri buradan alınır.
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {

        // Eğer daha önce yüklenmiş bir sayfa yoksa ilk sayfadan başlar.
        val page = params.key ?: 1

        return try {

            // TMDB API üzerinden belirtilen sayfadaki popüler filmleri getirir.
            val response = api.getPopularMovies(page = page)


            // Başarılı şekilde gelen film listesini Paging sistemine gönderir.
            LoadResult.Page(
                data = response.results,

                // Eğer ilk sayfadaysak geri gidilecek sayfa yoktur.
                prevKey = if (page == 1) null else page - 1,

                // Son sayfaya ulaşıldıysa daha fazla veri çekilmez.
                // Değilse bir sonraki sayfa yüklenir demek
                nextKey = if (page >= response.total_pages) null else page + 1
            )

        } catch (e: Exception) {

            // Veri çekme sırasında hata olursa hata sonucu döndürülür.
            LoadResult.Error(e)
        }
    }


    // Liste yenilendiğinde hangi sayfadan devam edileceğini belirler.
    // Kullanıcının kaldığı yere yakın bir noktadan devam etmesini sağlar.
    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {

        return state.anchorPosition?.let { anchorPosition ->

            // Kullanıcının bulunduğu konuma en yakın sayfa bulunur.
            val anchorPage = state.closestPageToPosition(anchorPosition)

            // Önceki veya sonraki sayfaya göre tekrar başlanacak sayfa hesaplanır.
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}