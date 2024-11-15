package com.care.anga.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commande")

data class Commande(
    @PrimaryKey(autoGenerate = true)
    val commandeId: Long = 0,
    val clientId: Int,
    val dateCommande: String,
    val quantiteCommande: Int
)