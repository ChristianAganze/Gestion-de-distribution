package com.care.anga.data.repository

import com.care.anga.data.dao.CommandeDao
import com.care.anga.data.entity.Commande
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class CommandeRepository @Inject constructor(
    private val commandeDao: CommandeDao
) {

    fun getAllCommandes() = commandeDao.getAllCommandes()

    suspend fun insert(commande: Commande) {
        commandeDao.insert(commande)
    }

    suspend fun deleteCommande(commande: Commande) {
        commandeDao.delete(commande)
    }

    fun getCommandeById(commandeId: Long) = commandeDao.getCommandeById(commandeId)
}

