package com.example.unchain.di.module

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.unchain.data.db.AddictionDao
import com.example.unchain.data.db.AddictionInWidgetDao
import com.example.unchain.data.db.AppDataBase
import com.example.unchain.data.db.UserProgressDao
import com.example.unchain.di.annotation.ApplicationSingleton
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule {

    @ApplicationSingleton
    @Provides
    fun provideDataBase(application: Application) : AppDataBase{

        application.deleteDatabase(DB_NAME)

        return Room.databaseBuilder(
            application,
            AppDataBase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAddictionDao(db : AppDataBase) : AddictionDao{
        return db.addictionDao()
    }

    @Provides
    fun provideUserProgressDao(db: AppDataBase) : UserProgressDao{
        return db.userProgressDao()
    }

    @Provides
    fun provideAddictionInWidgetDao(db : AppDataBase) : AddictionInWidgetDao{
        return db.addictionInWidgetDao()
    }

    companion object{
        private const val DB_NAME = "addiction_app_db"
    }
}