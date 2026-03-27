package com.example.unchain.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class GeminiRequestDto(val contents: List<ContentRequestDto>)

@Serializable
data class ContentRequestDto(val parts: List<PartRequestDto>)

@Serializable
data class PartRequestDto(val text: String)