package com.care.anga.di

import android.content.Context
import androidx.room.Room
import com.care.anga.data.WaterDistributionDatabase
import com.care.anga.data.dao.ClientDao
import com.care.anga.data.dao.CommandeDao
import com.care.anga.data.dao.LivraisonDao
import com.care.anga.data.dao.VenteDao
import com.care.anga.data.repository.ClientRepository
import com.care.anga.data.repository.CommandeRepository
import com.care.anga.data.repository.LivraisonRepository
import com.care.anga.data.repository.VenteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // Fournit la base de données Room
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WaterDistributionDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            WaterDistributionDatabase::class.java,
            "water_distribution_db"
        ).build()
    }

    @Provides
    fun provideClientDao(database: WaterDistributionDatabase): ClientDao {
        return database.clientDao()
    }

    @Provides
    fun provideCommandeDao(database: WaterDistributionDatabase): CommandeDao {
        return database.commandeDao()
    }

    @Provides
    fun provideLivraisonDao(database: WaterDistributionDatabase): LivraisonDao {
        return database.livraisonDao()
    }

    @Provides
    fun provideVenteDao(database: WaterDistributionDatabase): VenteDao {
        return database.venteDao()
    }


    @Provides
    @Singleton
    fun provideClientRepository(clientDao: ClientDao): ClientRepository {
        return ClientRepository(clientDao)
    }

    @Provides
    @Singleton
    fun provideCommandeRepository(commandeDao: CommandeDao): CommandeRepository {
        return CommandeRepository(commandeDao)
    }

    @Provides
    @Singleton
    fun provideLivraisonRepository(livraisonDao: LivraisonDao): LivraisonRepository {
        return LivraisonRepository(livraisonDao)
    }

    @Provides
    @Singleton
    fun provideVenteRepository(venteDao: VenteDao): VenteRepository {
        return VenteRepository(venteDao)
    }
}
