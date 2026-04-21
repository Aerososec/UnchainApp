package com.example.unchain.data.repository

import android.appwidget.AppWidgetManager
import android.content.Intent
import com.example.unchain.data.db.AddictionDao
import com.example.unchain.data.db.UserProgressDao
import com.example.unchain.data.models.mapper.Mapper
import com.example.unchain.domain.models.Addiction
import com.example.unchain.domain.models.AddictionWithProgress
import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.repositories.UserRepository
import com.example.unchain.presentation.widget.MyWidgetProvider
import com.example.unchain.presentation.widget.WidgetConfigActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val addictionDao: AddictionDao,
    private val userProgressDao: UserProgressDao,
    private val mapper: Mapper
) : UserRepository {
    override suspend fun startAddiction(userProgress : UserProgress) {
        saveProgress(userProgress)
    }

    override suspend fun markDayFail(userProgress: UserProgress) {
        saveProgress(userProgress)
    }

    override suspend fun markDaySuccess(userProgress: UserProgress) {
        saveProgress(userProgress)
    }

    override fun getUserProgress(addictionId: Int): Flow<UserProgress?> {
        return userProgressDao.getUserProgress(addictionId).map {
            it?.let {
                mapper.dbModelToEntityUserProgress(it)
            }
        }
    }

    override fun getAllAddictions(): Flow<List<AddictionWithProgress>> {
        return addictionDao.getAllAddictions().map {
            mapper.dbModelToEntityAddictionList(it)
        }
    }

    override suspend fun insertAddiction(addiction: Addiction) {
        addictionDao.insertUserAddiction(mapper.entityToDbModelAddiction(addiction))
    }

    override suspend fun getAddictionById(addictionId: Int): Addiction {
        return mapper.dbModelToEntityAddiction(addictionDao.getAddictionById(addictionId))
    }

    override suspend fun saveUserProgress(userProgress: UserProgress) {
        saveProgress(userProgress)
    }


    private suspend fun saveProgress(newProgress : UserProgress){
        userProgressDao.upsertProgress(mapper.entityToDbModelUserProgress(newProgress))
    }

}