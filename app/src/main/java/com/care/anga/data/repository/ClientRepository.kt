package com.care.anga.data.repository

import com.care.anga.data.dao.ClientDao
import com.care.anga.data.entity.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class ClientRepository @Inject constructor(private val clientDao: ClientDao) {

    fun getAllClients(): Flow<List<Client>> = clientDao.getAllClients()

    suspend fun insertClient(client: Client) {
        clientDao.insert(client)
    }

    suspend fun deleteClient(client: Client) {
        clientDao.delete(client)
    }

    fun getClientById(clientId: Int): Flow<Client?> = clientDao.getClientById(clientId)
}
