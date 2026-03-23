package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.provider.TimeProvider
import com.example.unchain.domain.repositories.UserRepository
import com.example.unchain.domain.utils.isSameDay
import javax.inject.Inject

class MarkDayFailUseCase @Inject constructor(private val userRepository: UserRepository, private val timeProvider: TimeProvider) {

    suspend fun execute(userProgress: UserProgress, addictionId : Int) : UserProgress {
        val lastCheckDate = timeProvider.now()
        if (!isSameDay(userProgress.lastCheckDate, lastCheckDate)){
            makeAddictionNotActive(addictionId = addictionId)
            return failDay(userProgress, lastCheckDate, addictionId)
        }
        return userProgress
    }

    private suspend fun failDay(userProgress: UserProgress, lastCheckDate : Long, addictionId : Int) : UserProgress{
        val startDate = userProgress.startDate
        val currentStreak = 0
        val currency = 0
        val dayResult = false
        val newProgress = userProgress.copy(
            startDate = startDate,
            currentStreak = currentStreak,
            currency = currency,
            lastCheckDate = lastCheckDate,
            dayResult = dayResult
        )

        userRepository.markDayFail(newProgress)
        return newProgress
    }

    private suspend fun makeAddictionNotActive(addictionId: Int){
        val addiction = userRepository.getAddictionById(addictionId)
        val newAddiction = addiction.copy(isActive = false)
        userRepository.insertAddiction(newAddiction)
    }
}