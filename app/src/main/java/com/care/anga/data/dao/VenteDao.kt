package com.care.anga.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.care.anga.data.entity.Vente
import kotlinx.coroutines.flow.Flow

@Dao
interface VenteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun InsertVente(vente: Vente)

    @Delete
    fun deleteVente(vente: Vente)

    @Query("SELECT * FROM vente")
    fun getAllVente():Flow<List<Vente>>

    @Query("SELECT * FROM vente WHERE venteId = :venteId")
    abstract fun getVenteById(venteId: Long): Flow<Vente>

}