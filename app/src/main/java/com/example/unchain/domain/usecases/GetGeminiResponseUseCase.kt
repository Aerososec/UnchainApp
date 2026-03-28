package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.gemini.GeminiRequest
import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.repositories.GeminiRepository
import javax.inject.Inject

class GetGeminiResponseUseCase @Inject constructor(private val geminiRepository: GeminiRepository) {
    suspend fun execute(geminiRequest: GeminiRequest) : GeminiResponse{
        return geminiRepository.getGeminiResponse(geminiRequest)
    }
}