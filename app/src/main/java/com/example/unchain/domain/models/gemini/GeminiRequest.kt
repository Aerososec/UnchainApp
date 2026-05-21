package com.example.unchain.domain.models.gemini

data class GeminiRequest(
    val contents: List<ContentRequest>,
    val systemInstruction: SystemInstruction? = null
)

data class SystemInstruction(val parts: List<PartRequest>)

data class ContentRequest(val role: String = MessageRole.USER.value, val parts: List<PartRequest>)

data class PartRequest(val text: String)