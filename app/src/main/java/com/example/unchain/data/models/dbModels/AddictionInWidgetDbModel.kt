package com.example.unchain.data.models.dbModels

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addiction_in_widget")
data class AddictionInWidgetDbModel(
    @PrimaryKey
    val widgetId : Int,
    val addictionId : Int
)
