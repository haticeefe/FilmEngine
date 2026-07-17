package com.example.filmengine.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmengine.data.local.entity.FavoriteMovie
import com.example.filmengine.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

// Favoriler ekranının ViewModel'i.
// Hilt sayesinde FavoriteRepository otomatik olarak buraya sağlanır.
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    repository: FavoriteRepository
) : ViewModel() {

    // Repository'den gelen Flow'u StateFlow'a çeviriyoruz.
    // StateFlow'un farkı: her zaman elinde bir "en güncel değer" tutar,
    // bu sayede ekran ilk açıldığında bile bir başlangıç değeri (boş liste) olur.

    val favorites: StateFlow<List<FavoriteMovie>> = repository.getAllFavorites()
        .stateIn(
            // ViewModel yaşam döngüsüne bağlı olarak çalışır
            scope = viewModelScope,
            // Ekran kapatılıp 5 saniye içinde tekrar açılırsa veri akışını yeniden başlatmaz,
            // gereksiz veritabanı sorgusunu önler
            started = SharingStarted.WhileSubscribed(5000),
            // Veri daha gelmeden önceki başlangıç değeri
            initialValue = emptyList()
        )
}


// Bu ViewModel, Room veritabanındaki favori filmleri Repository üzerinden çeker
// ve FavoriteScreen'in kolayca gözlemleyebileceği bir state haline getirir.