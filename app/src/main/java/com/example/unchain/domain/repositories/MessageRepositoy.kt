package com.example.unchain.domain.repositories

import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.models.gemini.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun sendMessage(message: Message) : List<GeminiResponse>

    fun getChatForAddiction(addictionId : Int) : Flow<List<Message>>
}