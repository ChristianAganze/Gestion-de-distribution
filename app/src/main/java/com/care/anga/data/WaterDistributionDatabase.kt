package com.care.anga.data

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.care.anga.data.dao.ClientDao
import com.care.anga.data.dao.CommandeDao
import com.care.anga.data.dao.LivraisonDao
import com.care.anga.data.dao.VenteDao
import com.care.anga.data.entity.Client
import com.care.anga.data.entity.Commande
import com.care.anga.data.entity.Livraison
import com.care.anga.data.entity.Vente

@Database(entities = [Client::class, Commande::class,
    Livraison::class, Vente::class], version = 1)
abstract class WaterDistributionDatabase : RoomDatabase() {
    abstract fun clientDao(): ClientDao
    abstract fun commandeDao(): CommandeDao
    abstract fun livraisonDao(): LivraisonDao
    abstract fun venteDao(): VenteDao
}
