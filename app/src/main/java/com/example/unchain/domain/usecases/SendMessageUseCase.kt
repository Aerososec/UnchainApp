package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.models.gemini.Message
import com.example.unchain.domain.repositories.MessageRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(message: Message) : List<GeminiResponse>{
        return messageRepository.sendMessage(message)
    }
}