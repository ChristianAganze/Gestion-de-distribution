package com.care.anga.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.care.anga.data.entity.Client
import com.care.anga.data.repository.ClientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : ViewModel() {

    private val _allClients = MutableStateFlow<List<Client>>(emptyList())
    val allClients = _allClients.asStateFlow()

    init {
        viewModelScope.launch {
            clientRepository.getAllClients().collect {
                _allClients.value =it
            }
        }
    }

    fun addClient(client: Client) {
        viewModelScope.launch {
            clientRepository.insertClient(client)
        }
    }

    fun deleteClient(client: Client) {
        viewModelScope.launch {
            clientRepository.deleteClient(client)
        }
    }

    fun getClientById(clientId: Int): StateFlow<Client?> {
        return clientRepository.getClientById(clientId)
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null
            )
    }
}
