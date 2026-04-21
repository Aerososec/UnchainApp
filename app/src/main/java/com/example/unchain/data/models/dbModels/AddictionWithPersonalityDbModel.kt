package com.example.unchain.data.models.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addiction_with_personality")
data class AddictionWithPersonalityDbModel(
    @PrimaryKey
    val addictionId : Int,
    val personalityId : Int
)
