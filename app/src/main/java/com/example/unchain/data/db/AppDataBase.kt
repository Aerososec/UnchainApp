package com.example.unchain.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.unchain.data.models.dbModels.AddictionDbModel
import com.example.unchain.data.models.dbModels.AddictionInWidgetDbModel
import com.example.unchain.data.models.dbModels.AddictionWithPersonalityDbModel
import com.example.unchain.data.models.dbModels.MessageDbModel
import com.example.unchain.data.models.dbModels.PersonalityDbModel
import com.example.unchain.data.models.dbModels.ThemeDbModel
import com.example.unchain.data.models.dbModels.UserProgressDbModel
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Theme

@Database(
    entities = [
        AddictionDbModel::class,
        UserProgressDbModel::class,
        AddictionInWidgetDbModel::class,
        MessageDbModel::class,
        ThemeDbModel::class,
        PersonalityDbModel::class,
        AddictionWithPersonalityDbModel::class], version = 1, exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun messageDao(): MessageDao
    abstract fun addictionDao(): AddictionDao
    abstract fun userProgressDao(): UserProgressDao
    abstract fun addictionInWidgetDao(): AddictionInWidgetDao
    abstract fun personalizationDao() : PersonalizationDao
}