package com.example.unchain.data.repository

import com.example.unchain.BuildConfig
import com.example.unchain.data.models.GeminiRequestMapper
import com.example.unchain.data.models.GeminiResponseMapper
import com.example.unchain.data.remote.GeminiApiService
import com.example.unchain.domain.models.gemini.GeminiRequest
import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.repositories.GeminiRepository
import javax.inject.Inject

class GeminiRepositoryImpl @Inject constructor(
    private val geminiApiService: GeminiApiService,
    private val geminiRequestMapper: GeminiRequestMapper,
    private val geminiResponseMapper: GeminiResponseMapper
) : GeminiRepository {
    override suspend fun getGeminiResponse(geminiRequest: GeminiRequest): GeminiResponse {
        val response = geminiApiService.getGeminiResponseContent(
            BuildConfig.GEMINI_MODEL,
            BuildConfig.GEMINI_API_KEY,
            geminiRequestMapper.entityToDtoRequest(geminiRequest)
        )
        return geminiResponseMapper.dtoToEntity(response)
    }

    override suspend fun getGeminiResponseStream(geminiRequest: GeminiRequest): List<GeminiResponse> {
        val response = geminiApiService.getGeminiResponseStream(
            BuildConfig.GEMINI_MODEL,
            BuildConfig.GEMINI_API_KEY,
            geminiRequestMapper.entityToDtoRequest(geminiRequest)
        )
        return geminiResponseMapper.dtoToEntityList(response)
    }


}