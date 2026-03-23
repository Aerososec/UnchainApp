package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.AddictionInWidget
import com.example.unchain.domain.repositories.AddictionInWidgetRepository
import javax.inject.Inject

class SetAddictionForWidgetUseCase @Inject constructor(private val addictionInWidgetRepository: AddictionInWidgetRepository) {
    suspend fun execute(addictionInWidget: AddictionInWidget){
        addictionInWidgetRepository.setAddictionInWidget(addictionInWidget)
    }
}