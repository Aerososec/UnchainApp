package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.gemini.Message
import com.example.unchain.domain.repositories.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetChatUseCase @Inject constructor(private val messageRepository: MessageRepository) {
    operator fun invoke(addictionId : Int) : Flow<List<Message>>{
        return messageRepository.getChatForAddiction(addictionId)
    }
}