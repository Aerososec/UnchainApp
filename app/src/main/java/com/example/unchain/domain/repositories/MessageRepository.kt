package com.example.unchain.domain.repositories

import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.models.gemini.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    fun getChatForAddiction(addictionId : Int) : Flow<List<Message>>
    suspend fun insertMessage(message: Message)
}