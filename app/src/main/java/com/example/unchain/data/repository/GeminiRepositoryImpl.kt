package com.example.unchain.data.repository

import com.example.unchain.BuildConfig
import com.example.unchain.data.remote.GeminiApiService
import com.example.unchain.domain.models.gemini.GeminiRequest
import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.repositories.GeminiRepository
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor(private val geminiApiService: GeminiApiService) : GeminiRepository {
    override suspend fun getGeminiResponse(geminiRequest: GeminiRequest): GeminiResponse {
        geminiApiService.getGeminiResponse(BuildConfig.GEMINI_MODEL, BuildConfig.GEMINI_API_KEY, geminiRequest)
    }
}