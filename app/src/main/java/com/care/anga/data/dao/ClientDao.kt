package com.care.anga.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.care.anga.data.entity.Client
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(client: Client)

    @Delete
   suspend fun delete(client: Client)

    @Query("SELECT * FROM client")
    fun getAllClients():Flow<List<Client>>

    @Query("SELECT * FROM client WHERE clientId = :clientId")
     fun getClientById(clientId: Int): Flow<Client>

}