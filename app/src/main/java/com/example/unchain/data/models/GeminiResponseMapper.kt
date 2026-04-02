package com.example.unchain.data.models

import com.example.unchain.data.remote.CandidatesDto
import com.example.unchain.data.remote.ContentResponseDto
import com.example.unchain.data.remote.GeminiResponseDto
import com.example.unchain.data.remote.PartResponseDto
import com.example.unchain.domain.models.gemini.Candidates
import com.example.unchain.domain.models.gemini.ContentResponse
import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.models.gemini.PartResponse
import javax.inject.Inject

class GeminiResponseMapper @Inject constructor() {

    fun dtoToEntity(dto: GeminiResponseDto): GeminiResponse {
        return GeminiResponse(
            candidates = dto.candidates?.map { dtoToEntityCandidate(it) }
        )
    }

    private fun dtoToEntityCandidate(dto: CandidatesDto): Candidates {
        return Candidates(
            content = dtoToEntityContent(dto.content)
        )
    }

    private fun dtoToEntityContent(dto: ContentResponseDto): ContentResponse {
        return ContentResponse(
            parts = dto.parts.map { dtoToEntityPart(it) },
            dto.role
        )
    }

    private fun dtoToEntityPart(dto: PartResponseDto): PartResponse {
        return PartResponse(
            text = dto.text
        )
    }
}