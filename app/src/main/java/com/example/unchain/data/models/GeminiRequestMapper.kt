package com.example.unchain.data.models

import com.example.unchain.data.remote.ContentRequestDto
import com.example.unchain.data.remote.GeminiRequestDto
import com.example.unchain.data.remote.PartRequestDto
import com.example.unchain.domain.models.gemini.ContentRequest
import com.example.unchain.domain.models.gemini.GeminiRequest
import com.example.unchain.domain.models.gemini.PartRequest
import javax.inject.Inject

class GeminiRequestMapper @Inject constructor() {
    private fun dtoToEntityPart(partRequestDto: PartRequestDto) : PartRequest{
        return PartRequest(partRequestDto.text)
    }

    private fun entityToDtoPart(partRequest: PartRequest) : PartRequestDto{
        return PartRequestDto(partRequest.text)
    }

    private fun dtoToEntityContent(contentRequestDto: ContentRequestDto) : ContentRequest{
        return ContentRequest(contentRequestDto.role, contentRequestDto.parts.map { dtoToEntityPart(it) })
    }

    private fun entityToDtoContent(contentRequest: ContentRequest) : ContentRequestDto{
        return ContentRequestDto(contentRequest.role, contentRequest.parts.map { entityToDtoPart(it) })
    }

    fun entityToDtoRequest(geminiRequest: GeminiRequest) : GeminiRequestDto{
        return GeminiRequestDto(geminiRequest.contents.map { entityToDtoContent(it) })
    }

    fun dtoToEntityRequest(geminiRequestDto: GeminiRequestDto) : GeminiRequest{
        return GeminiRequest(geminiRequestDto.contents.map { dtoToEntityContent(it) })
    }

}