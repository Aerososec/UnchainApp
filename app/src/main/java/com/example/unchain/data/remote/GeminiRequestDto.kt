package com.example.unchain.data.remote

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class GeminiRequestDto(
    val contents: List<ContentRequestDto>,
    @SerialName("system_instruction")
    val systemInstruction: SystemInstructionDto? = null
)

@Serializable
data class SystemInstructionDto(val parts: List<PartRequestDto>)

@Serializable
data class ContentRequestDto(val role : String, val parts: List<PartRequestDto>)

@Serializable
data class PartRequestDto(val text: String)