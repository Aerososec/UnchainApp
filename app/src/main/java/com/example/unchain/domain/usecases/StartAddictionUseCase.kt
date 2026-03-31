package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.provider.TimeProvider
import com.example.unchain.domain.repositories.UserRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class StartAddictionUseCase @Inject constructor(private val userRepository: UserRepository, private val timeProvider: TimeProvider) {
    suspend fun execute(addictionId : Int){
        val oldUserProgress = userRepository.getUserProgress(addictionId).firstOrNull()
        var bestStreak = 0
        oldUserProgress?.let {
            bestStreak = it.bestStreak
        }
        val userProgress = UserProgress(addictionId = addictionId, startDate = timeProvider.now(), bestStreak = bestStreak)
        val oldAddiction = userRepository.getAddictionById(addictionId)
        val addictionUpdate = oldAddiction.copy(isActive = true)
        userRepository.insertAddiction(addictionUpdate)
        userRepository.startAddiction(userProgress)
    }
}