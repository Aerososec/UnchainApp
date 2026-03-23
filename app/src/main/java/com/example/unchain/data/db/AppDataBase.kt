package com.example.unchain.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unchain.data.models.AddictionDbModel
import com.example.unchain.data.models.AddictionInWidgetDbModel
import com.example.unchain.data.models.AddictionWithProgressDbModel
import com.example.unchain.data.models.UserProgressDbModel

@Database(entities = [AddictionDbModel::class, UserProgressDbModel::class, AddictionInWidgetDbModel::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase(){

    abstract fun addictionDao() : AddictionDao
    abstract fun userProgressDao() : UserProgressDao
    abstract fun addictionInWidgetDao() : AddictionInWidgetDao
}