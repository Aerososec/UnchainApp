package com.example.unchain.domain.models.gemini

data class GeminiResponse(val candidates : List<Candidates>? = null)

data class Candidates(val content : ContentResponse)

data class ContentResponse(val parts : List<PartResponse>, val role : String)

data class PartResponse(val text : String?)
