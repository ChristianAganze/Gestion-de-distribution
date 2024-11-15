package com.care.anga.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "vente")
data class Vente(
    @PrimaryKey(autoGenerate = true)
    val venteId:Long = 0,
    val commandeId:Long,
    val dateVente:String,
    val montant:String
)
