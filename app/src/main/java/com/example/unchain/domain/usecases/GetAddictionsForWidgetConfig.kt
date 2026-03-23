package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.Addiction
import com.example.unchain.domain.repositories.AddictionInWidgetRepository
import javax.inject.Inject

class GetAddictionsForWidgetConfig @Inject constructor(private val addictionInWidgetRepository: AddictionInWidgetRepository) {
    suspend fun execute() : List<Addiction>{
        return addictionInWidgetRepository.getAddictions()
    }
}