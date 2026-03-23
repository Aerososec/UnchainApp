package com.example.unchain.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addiction_in_widget")
data class AddictionInWidgetDbModel(
    @PrimaryKey
    val widgetId : Int,
    val addictionId : Int
)
