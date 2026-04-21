package com.example.unchain.data.models.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addiction")
data class AddictionDbModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val isActive: Boolean,
)
