package com.example.filmengine.ui.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.filmengine.data.remote.dto.MovieDto
import com.example.filmengine.data.repository.PopularMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Bu sınıf ViewModel olarak kullanılır.
// Hilt sayesinde gerekli olan Repository otomatik olarak oluşturulur.

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val repository: PopularMoviesRepository
) : ViewModel() {

    // Repository'den popüler filmleri alır.
    // cachedIn sayesinde veriler ViewModel açık olduğu sürece korunur.

    val popularMovies: Flow<PagingData<MovieDto>> =
        repository.getPopularMovies()
            .cachedIn(viewModelScope)
}


//bu kod popüler filmleri Repository'den alır ve ViewModel üzerinden ekrana gönderir.
// Ayrıca verilerin gereksiz yere tekrar yüklenmesini önler.