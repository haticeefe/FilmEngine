package com.example.filmengine.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmengine.data.local.entity.FavoriteMovie
import com.example.filmengine.data.remote.dto.MovieDetailDto
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

    // Film detayını tutar.
    // Veri değiştiğinde ekran da otomatik güncellenir.
    private val _movieDetail = MutableStateFlow<MovieDetailDto?>(null)
    val movieDetail: StateFlow<MovieDetailDto?> = _movieDetail.asStateFlow()

    // Filmin favori durumunu tutar.
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    // Film detayını getirir ve favoride olup olmadığını kontrol eder.
    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _movieDetail.value = movieRepository.getMovieDetail(movieId)
            _isFavorite.value = favoriteRepository.isFavorite(movieId)
        }
    }

    // Favori butonuna basıldığında filmi ekler veya çıkarır.
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

    // API'den gelen film bilgisini veritabanına uygun hale çevirir.
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


//Bu ViewModel, film detaylarını getirir,
// filmin favori olup olmadığını kontrol eder ve favorilere ekleme/çıkarma işlemlerini yönetir.