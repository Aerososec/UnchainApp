package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.provider.TimeProvider
import com.example.unchain.domain.repositories.UserRepository
import com.example.unchain.domain.utils.isSameDay
import javax.inject.Inject

class MarkDaySuccessUseCase @Inject constructor(private val userRepository: UserRepository, private val timeProvider: TimeProvider) {
    suspend fun execute(userProgress: UserProgress, addictionId : Int) : UserProgress {
        val lastCheckDate = timeProvider.now()
        if (!isSameDay(userProgress.lastCheckDate, lastCheckDate)){
            return newSuccessDay(userProgress, lastCheckDate, addictionId)
        }
        return userProgress
    }

    private suspend fun newSuccessDay(userProgress: UserProgress, lastCheckDate : Long, addictionId: Int) : UserProgress{
        val dayResult = true
        val currentStreak = userProgress.currentStreak + 1
        val bestStreak = maxOf(currentStreak, userProgress.bestStreak)
        val currency = userProgress.currency + currencyProgression(userProgress.currentStreak)
        val newProgress = userProgress.copy(
            currentStreak = currentStreak,
            bestStreak = bestStreak,
            currency = currency,
            lastCheckDate = lastCheckDate,
            dayResult = dayResult
        )

        userRepository.markDaySuccess(newProgress)
        return newProgress
    }


    private fun currencyProgression(value: Int): Int {
        return 100 + value * 10
    }
}