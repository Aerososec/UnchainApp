package com.example.unchain.domain.repositories

import com.example.unchain.domain.models.Addiction
import com.example.unchain.domain.models.AddictionWithProgress
import com.example.unchain.domain.models.UserProgress
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun startAddiction(userProgress: UserProgress)
    suspend fun markDayFail(userProgress: UserProgress)
    suspend fun markDaySuccess(userProgress: UserProgress)
    fun getUserProgress(addictionId: Int) : Flow<UserProgress?>
    fun getAllAddictions() : Flow<List<AddictionWithProgress>>
    suspend fun insertAddiction(addiction: Addiction)
    suspend fun getAddictionById(addictionId: Int) : Addiction
    suspend fun saveUserProgress(userProgress: UserProgress)
}