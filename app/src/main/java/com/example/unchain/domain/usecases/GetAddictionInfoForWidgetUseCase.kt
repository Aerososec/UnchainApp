package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.AddictionWithProgress
import com.example.unchain.domain.repositories.AddictionInWidgetRepository
import javax.inject.Inject

class GetAddictionInfoForWidgetUseCase @Inject constructor(private val repository: AddictionInWidgetRepository) {
    suspend fun execute(addictionId : Int) : AddictionWithProgress{
        return repository.getAddictionInfo(addictionId)
    }
}