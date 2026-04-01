package com.example.unchain.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unchain.data.models.AddictionDbModel
import com.example.unchain.data.models.AddictionInWidgetDbModel
import com.example.unchain.data.models.AddictionWithProgressDbModel
import com.example.unchain.data.models.MessageDbModel
import com.example.unchain.data.models.UserProgressDbModel

@Database(
    entities = [
        AddictionDbModel::class,
        UserProgressDbModel::class,
        AddictionInWidgetDbModel::class,
        MessageDbModel::class], version = 1, exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
    abstract fun addictionDao(): AddictionDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun addictionInWidgetDao(): AddictionInWidgetDao
}