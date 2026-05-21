package com.example.unchain.domain.usecases

import com.example.unchain.domain.models.gemini.ContentRequest
import com.example.unchain.domain.models.gemini.GeminiRequest
import com.example.unchain.domain.models.gemini.Message
import com.example.unchain.domain.models.gemini.PartRequest
import com.example.unchain.domain.models.gemini.SystemInstruction
import com.example.unchain.domain.repositories.PersonalizationRepository
import javax.inject.Inject

class CreateGeminiRequestUseCase @Inject constructor(private val personalizationRepository: PersonalizationRepository) {
    suspend operator fun invoke(chatHistory: List<Message>, addictionId: Int): GeminiRequest {
        val promptModifier = personalizationRepository.getPromptModifierByAddictionId(addictionId)

        val contents = chatHistory
            .takeLast(CHAT_HISTORY_MAX_SIZE)
            .map { message ->
                ContentRequest(
                    parts = listOf(
                        PartRequest(message.text)
                    )
                )
            }
        return GeminiRequest(
            contents,
            promptModifier?.let {
                SystemInstruction(parts = listOf(PartRequest(promptModifier)))
            }
        )
    }

    companion object {
        private const val CHAT_HISTORY_MAX_SIZE = 10
    }
}