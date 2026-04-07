package com.example.unchain.domain.repositories

import com.example.unchain.domain.models.gemini.GeminiRequest
import com.example.unchain.domain.models.gemini.GeminiResponse

interface GeminiRepository {
    suspend fun getGeminiResponse(geminiRequest: GeminiRequest) : GeminiResponse
    suspend fun getGeminiResponseStream(geminiRequest: GeminiRequest) : List<GeminiResponse>
}