package com.example.unchain.presentation.userProgressScreen.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.models.gemini.Message
import com.example.unchain.domain.models.gemini.MessageRole
import com.example.unchain.domain.usecases.GetChatUseCase
import com.example.unchain.domain.usecases.SendMessageUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class MessageViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getChatUseCase: GetChatUseCase
) : ViewModel() {

    private val addictionIdFlow = MutableStateFlow(-1)
    @OptIn(ExperimentalCoroutinesApi::class)
    val chatFlow = addictionIdFlow
        .filter { it > -1}
        .flatMapLatest { getChatUseCase(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun sendMessage(text : String, addictionId: Int) {
        val message = Message(
            id = 0,
            text = text,
            role = MessageRole.USER.value,
            addictionId = addictionId
        )

        viewModelScope.launch {
            sendMessageUseCase(message)
        }
    }

    fun getChat(addictionId: Int) {
        addictionIdFlow.value = addictionId
    }
}