package com.care.anga.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.care.anga.data.entity.Livraison
import com.care.anga.data.repository.LivraisonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LivraisonViewModel @Inject constructor(
    private val livraisonRepository: LivraisonRepository
) : ViewModel() {

    private val _allLivraisons = MutableStateFlow<List<Livraison>>(emptyList())
    val allLivraisons: StateFlow<List<Livraison>> = _allLivraisons.asStateFlow()

    init {
        viewModelScope.launch {
            livraisonRepository.getAllLivraisons().collect { livraisons ->
                _allLivraisons.value = livraisons
            }
        }
    }

    fun addLivraison(livraison: Livraison) {
        viewModelScope.launch {
            livraisonRepository.insertLivraison(livraison)
        }
    }

    fun deleteLivraison(livraison: Livraison) {
        viewModelScope.launch {
            livraisonRepository.deleteLivraison(livraison)
        }
    }

    fun getLivraisonById(livraisonId: Long): StateFlow<Livraison?> {
        return livraisonRepository.getLivraisonById(livraisonId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }
}
