package com.example.unchain.domain.usecases

import com.example.unchain.domain.repositories.AddictionInWidgetRepository
import javax.inject.Inject

class GetAddictionIdByWidgetIdUseCase @Inject constructor(private val addictionInWidgetRepository: AddictionInWidgetRepository) {
    suspend fun execute(widgetId : Int) : Int?{
        return addictionInWidgetRepository.getAddictionIdByWidgetId(widgetId)
    }
}