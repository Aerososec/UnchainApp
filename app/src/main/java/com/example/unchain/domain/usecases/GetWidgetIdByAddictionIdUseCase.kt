package com.example.unchain.domain.usecases

import com.example.unchain.domain.repositories.AddictionInWidgetRepository
import javax.inject.Inject

class GetWidgetIdByAddictionIdUseCase @Inject constructor(private val addictionInWidgetRepository: AddictionInWidgetRepository) {
    suspend fun execute(addictionId : Int) : Int? {
        return addictionInWidgetRepository.getWidgetIdByAddictionId(addictionId)
    }
}