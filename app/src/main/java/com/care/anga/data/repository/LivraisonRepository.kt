package com.care.anga.data.repository

import com.care.anga.data.dao.LivraisonDao
import com.care.anga.data.entity.Livraison
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class LivraisonRepository @Inject constructor(
    private val database: LivraisonDao
) {

    // Récupérer toutes les livraisons
    fun getAllLivraisons(): Flow<List<Livraison>> {
        return database.getAllLivraison()
    }

    // Insérer une livraison
    suspend fun insertLivraison(livraison: Livraison) {
        database.insertLivraison(livraison)
    }

    // Supprimer une livraison
    suspend fun deleteLivraison(livraison: Livraison) {
        database.deleteLivraison(livraison)
    }

    // Récupérer une livraison par son ID
    fun getLivraisonById(livraisonId: Long): Flow<Livraison?> {
        return database.getLivraisonById(livraisonId)
    }
}
