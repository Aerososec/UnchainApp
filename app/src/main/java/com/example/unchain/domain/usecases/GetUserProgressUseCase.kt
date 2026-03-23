package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserProgressUseCase @Inject constructor(private val userRepository: UserRepository, private val startAddictionUseCase: StartAddictionUseCase) {
    fun execute(addictionId : Int) : Flow<UserProgress?> = flow{
        val userProgress = userRepository.getUserProgress(addictionId).firstOrNull()
        val addiction = userRepository.getAddictionById(addictionId)
        if (!addiction.isActive){
            startAddictionUseCase.execute(addictionId)
        }
        emitAll(userRepository.getUserProgress(addictionId))
    }
}