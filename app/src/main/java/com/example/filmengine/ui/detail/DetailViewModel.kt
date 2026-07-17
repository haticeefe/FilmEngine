package com.example.filmengine.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmengine.data.local.entity.FavoriteMovie
import com.example.filmengine.data.remote.dto.CastMemberDto
import com.example.filmengine.data.remote.dto.MovieDetailDto
import com.example.filmengine.data.remote.dto.MovieDto
import com.example.filmengine.data.repository.FavoriteRepository
import com.example.filmengine.data.repository.PopularMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: PopularMoviesRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _movieDetail = MutableStateFlow<MovieDetailDto?>(null)
    val movieDetail: StateFlow<MovieDetailDto?> = _movieDetail.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    // Oyuncu kadrosunu tutar (Starring bölümü için).
    private val _cast = MutableStateFlow<List<CastMemberDto>>(emptyList())
    val cast: StateFlow<List<CastMemberDto>> = _cast.asStateFlow()

    // Benzer filmleri tutar (Similar To This bölümü için).
    private val _recommendations = MutableStateFlow<List<MovieDto>>(emptyList())
    val recommendations: StateFlow<List<MovieDto>> = _recommendations.asStateFlow()

    fun loadMovie(movieId: Int) {
        // Ana film detayı ve favori durumu
        viewModelScope.launch {
            _movieDetail.value = movieRepository.getMovieDetail(movieId)
            _isFavorite.value = favoriteRepository.isFavorite(movieId)
        }

        // Oyuncu kadrosu ayrı bir coroutine'de çekiliyor,
        // böylece ana detay verisini beklemeden paralel yüklenir.
        // İlk 10 oyuncuyla sınırlıyoruz, tüm kadroyu göstermeye gerek yok.
        viewModelScope.launch {
            _cast.value = movieRepository.getMovieCredits(movieId).take(10)
        }

        // Benzer filmler de ayrı bir coroutine'de çekiliyor.
        viewModelScope.launch {
            _recommendations.value = movieRepository.getMovieRecommendations(movieId).take(10)
        }
    }

    fun toggleFavorite() {
        val movie = _movieDetail.value ?: return

        viewModelScope.launch {
            if (_isFavorite.value) {
                favoriteRepository.removeFavorite(movie.toFavoriteMovie())
                _isFavorite.value = false
            } else {
                favoriteRepository.addFavorite(movie.toFavoriteMovie())
                _isFavorite.value = true
            }
        }
    }

    private fun MovieDetailDto.toFavoriteMovie() = FavoriteMovie(
        id = id,
        title = title,
        overview = overview,
        posterPath = poster_path,
        backdropPath = backdrop_path,
        releaseDate = release_date,
        voteAverage = vote_average
    )
}

// Bu ViewModel, film detaylarını, oyuncu kadrosunu, benzer film önerilerini getirir,
// filmin favori olup olmadığını kontrol eder ve favorilere ekleme/çıkarma işlemlerini yönetir.