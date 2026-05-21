package com.example.unchain.domain.usecases

import android.util.Log
import com.example.unchain.domain.models.gemini.ContentRequest
import com.example.unchain.domain.models.gemini.GeminiRequest
import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.models.gemini.Message
import com.example.unchain.domain.models.gemini.MessageRole
import com.example.unchain.domain.models.gemini.PartRequest
import com.example.unchain.domain.models.gemini.SystemInstruction
import com.example.unchain.domain.repositories.GeminiRepository
import com.example.unchain.domain.repositories.MessageRepository
import com.example.unchain.domain.repositories.PersonalizationRepository
import com.example.unchain.domain.utils.Outcome
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val messageRepository: MessageRepository,
    private val geminiRepository: GeminiRepository,
    private val createGeminiRequestUseCase: CreateGeminiRequestUseCase
) {
    suspend operator fun invoke(message: Message): Outcome<Unit>{
        messageRepository.insertMessage(message)
        return try {
            val chatHistory = messageRepository.getChatForAddiction(message.addictionId).firstOrNull() ?: emptyList()
            val request = createGeminiRequestUseCase(chatHistory, message.addictionId)
            val geminiAnswer = geminiRepository.getGeminiResponseStream(request)
            val text =  geminiAnswer.joinToString("") { response ->
                response.candidates
                    ?.firstOrNull()
                    ?.content
                    ?.parts
                    ?.firstOrNull()
                    ?.text
                    ?: ""
            }
            if (text.isNotBlank()) {
                val answerMessage = makeMessage(text, message.addictionId)
                messageRepository.insertMessage(answerMessage)
            }
            geminiAnswer
            return Outcome.Success(Unit)
        } catch (e: Exception) {
            when (e) {

                is retrofit2.HttpException -> {
                    val errorBody = e.response()?.errorBody()?.string()
                    Log.e("GEMINI_HTTP", "Code: ${e.code()}")
                    Log.e("GEMINI_HTTP", "ErrorBody: $errorBody")
                }

                is java.io.IOException -> {
                    Log.e("GEMINI_NETWORK", "Network error: ${e.message}")
                }

                else -> {
                    Log.e("GEMINI_UNKNOWN", "Unknown error", e)
                }
            }
            return Outcome.Fail.Unknown(e.message)
        }
    }

    private fun makeMessage(text: String, addictionId: Int): Message {
        val message =
            Message(id = 0, text = text, role = MessageRole.GEMINI.value, addictionId = addictionId)
        return message
    }
}