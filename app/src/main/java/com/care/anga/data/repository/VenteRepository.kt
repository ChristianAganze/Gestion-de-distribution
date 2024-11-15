package com.care.anga.data.repository

import com.care.anga.data.dao.VenteDao
import com.care.anga.data.entity.Vente
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VenteRepository @Inject constructor(
    private val venteDao: VenteDao
) {

    // Récupérer toutes les ventes
    fun getAllVentes(): Flow<List<Vente>> {
        return venteDao.getAllVente()
    }

    // Insertion d'une vente (doit être exécutée sur un thread d'arrière-plan)
    suspend fun insertVente(vente: Vente) {
        // Exécuter cette opération sur le thread d'arrière-plan
        withContext(Dispatchers.IO) {
            venteDao.InsertVente(vente)
        }
    }

    // Suppression d'une vente
    suspend fun deleteVente(vente: Vente) {
        withContext(Dispatchers.IO) {
            venteDao.deleteVente(vente)
        }
    }

    // Récupérer une vente par son ID
    fun getVenteById(venteId: Long): Flow<Vente> {
        return venteDao.getVenteById(venteId)
    }
}

