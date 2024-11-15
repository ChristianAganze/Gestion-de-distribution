package com.care.anga.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.care.anga.data.entity.Commande
import kotlinx.coroutines.flow.Flow

@Dao
interface CommandeDao {

    @Query("SELECT * FROM commande")
    fun getAllCommandes(): Flow<List<Commande>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(commande: Commande)

    @Delete
    suspend fun delete(commande: Commande)

    @Query("SELECT * FROM commande WHERE commandeId = :commandeId")
    fun getCommandeById(commandeId: Long): Flow<Commande?>
}
