package com.care.anga.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.care.anga.data.entity.Livraison
import kotlinx.coroutines.flow.Flow

@Dao
interface LivraisonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLivraison(livraison: Livraison)

    @Delete
    suspend fun deleteLivraison(livraison: Livraison)

    @Query("SELECT * FROM livraison")
    fun getAllLivraison():Flow<List<Livraison>>

    @Query("SELECT * FROM livraison WHERE livraisonId = :livraisonId")
     fun getLivraisonById(livraisonId: Long): Flow<Livraison?>
}