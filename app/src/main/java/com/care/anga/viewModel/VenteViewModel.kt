package com.care.anga.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.care.anga.data.entity.Vente
import com.care.anga.data.repository.VenteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class VenteViewModel @Inject constructor(
    private val venteRepository: VenteRepository
) : ViewModel() {

    // L'état des ventes collecté
    private val _allVentes = MutableStateFlow<List<Vente>>(emptyList())
    val allVentes: StateFlow<List<Vente>> = _allVentes.asStateFlow()

    // L'état des erreurs lors de l'ajout ou de la suppression de ventes
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        viewModelScope.launch {
            try {
                venteRepository.getAllVentes().collect { ventes ->
                    _allVentes.value = ventes
                }
            } catch (e: Exception) {
                _error.value = "Erreur de récupération des ventes : ${e.message}"
            }
        }
    }

    // Ajouter une vente
    fun addVente(vente: Vente) {
        viewModelScope.launch {
            try {
                venteRepository.insertVente(vente)
            } catch (e: Exception) {
                _error.value = "Erreur lors de l'ajout de la vente : ${e.message}"
            }
        }
    }

    // Supprimer une vente
    fun deleteVente(vente: Vente) {
        viewModelScope.launch {
            try {
                venteRepository.deleteVente(vente)
            } catch (e: Exception) {
                _error.value = "Erreur lors de la suppression de la vente : ${e.message}"
            }
        }
    }

    // Obtenir une vente par ID
    fun getVenteById(venteId: Long): Flow<Vente> {
        return venteRepository.getVenteById(venteId)
    }
}
