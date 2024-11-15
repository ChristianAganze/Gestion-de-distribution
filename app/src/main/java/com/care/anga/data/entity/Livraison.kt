package com.care.anga.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "livraison")
data class Livraison(
    @PrimaryKey(autoGenerate = true)
    val livraisonId:Long = 0,
    val commandeId:Long,
    val dateLivraison: String,
    val quantiteLivree:String,
    val status:String
)