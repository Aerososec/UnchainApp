package com.example.unchain.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class GeminiResponseDto(val candidates : List<CandidatesDto>? = null)

@Serializable
data class CandidatesDto(val content : ContentResponseDto)

@Serializable
data class ContentResponseDto(val parts : List<PartResponseDto>, val role : String)

@Serializable
data class PartResponseDto(val text : String?)
