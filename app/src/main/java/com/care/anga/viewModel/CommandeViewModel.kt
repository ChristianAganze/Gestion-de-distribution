package com.care.anga.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.care.anga.data.entity.Commande
import com.care.anga.data.repository.CommandeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class CommandeViewModel @Inject constructor(
    private val commandeRepository: CommandeRepository
) : ViewModel() {

    private val _allCommandes = MutableStateFlow<List<Commande>>(emptyList())
    val allCommandes = _allCommandes.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) { // Utilisation explicite de Dispatchers.IO
            commandeRepository.getAllCommandes().collect {
                _allCommandes.value = it
            }
        }
    }

    fun addCommande(commande: Commande) {
        viewModelScope.launch(Dispatchers.IO) { // Utilisation explicite de Dispatchers.IO
            commandeRepository.insert(commande)
        }
    }

    fun deleteCommande(commande: Commande) {
        viewModelScope.launch(Dispatchers.IO) { // Utilisation explicite de Dispatchers.IO
            commandeRepository.deleteCommande(commande)
        }
    }

    fun getCommandeById(commandeId: Long): StateFlow<Commande?> {
        return commandeRepository.getCommandeById(commandeId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )
    }
}
