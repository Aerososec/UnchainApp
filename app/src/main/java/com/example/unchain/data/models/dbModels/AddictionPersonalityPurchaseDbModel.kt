package com.example.unchain.data.models.dbModels

import androidx.room.Entity

@Entity(tableName = "addiction_personality_purchase", primaryKeys = ["addictionId", "personalityId"])
data class AddictionPersonalityPurchaseDbModel(
    val addictionId : Int,
    val personalityId : Int
)