package com.care.anga.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "client")
data class Client(
    @PrimaryKey(autoGenerate = true)
    val clientId:Int = 0,
    val name:String,
    val address :String,
    val phone: String
)
