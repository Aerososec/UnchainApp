package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.AddictionWithProgress
import com.example.unchain.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllAddictionsUseCase @Inject constructor(private val userRepository: UserRepository) {
    fun execute(): Flow<List<AddictionWithProgress>> {
        return userRepository.getAllAddictions()
    }
}