package com.example.unchain.presentation.userProgressScreen.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.models.gemini.ContentRequest
import com.example.unchain.domain.models.gemini.GeminiRequest
import com.example.unchain.domain.models.gemini.GeminiResponse
import com.example.unchain.domain.models.gemini.PartRequest
import com.example.unchain.domain.usecases.GetGeminiResponseUseCase
import com.example.unchain.domain.usecases.GetUserProgressUseCase
import com.example.unchain.domain.usecases.GetWidgetIdByAddictionIdUseCase
import com.example.unchain.domain.usecases.MarkDayFailUseCase
import com.example.unchain.domain.usecases.MarkDaySuccessUseCase
import com.example.unchain.domain.usecases.SetReminderUseCase
import com.example.unchain.domain.usecases.StartAddictionUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProgressViewModel @Inject constructor(
    private val getUserProgressUseCase: GetUserProgressUseCase,
    private val markDayFailUseCase: MarkDayFailUseCase,
    private val markDaySuccessUseCase: MarkDaySuccessUseCase,
    private val setReminderUseCase: SetReminderUseCase,
    private val getWidgetIdByAddictionIdUseCase: GetWidgetIdByAddictionIdUseCase,
    private val getGeminiResponseUseCase: GetGeminiResponseUseCase
) : ViewModel() {


    private val _motivationFlow = MutableStateFlow<GeminiResponse?>(null)
    val motivationFlow = _motivationFlow

    val addictionId = MutableStateFlow<Int>(-1)



    @OptIn(ExperimentalCoroutinesApi::class)
    val userProgressFlow = addictionId
        .filter { it > -1 }
        .flatMapLatest { getUserProgressUseCase.execute(it) }
        .map {
            val shouldShowTimePicker = it?.hour == null && it?.minute == null
            ProgressUiState(
                isLoading = false,
                userProgress = it,
                shouldShowTimePicker = shouldShowTimePicker
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProgressUiState())

    fun loadProgress(id: Int) {
        addictionId.value = id
    }

    fun markDaySuccess(userProgress: UserProgress, addictionId: Int) {
        viewModelScope.launch {
            val update = markDaySuccessUseCase.execute(userProgress, addictionId)
        }
    }

    fun markDayFail(userProgress: UserProgress, addictionId: Int) {
        viewModelScope.launch {
            val update = markDayFailUseCase.execute(userProgress, addictionId)
        }
    }

    fun setReminder(addictionId: Int, hour: Int, minute: Int) {
        viewModelScope.launch {
            setReminderUseCase.execute(addictionId, hour, minute)
        }
    }

    suspend fun getWidgetIdByAddictionId(addictionId: Int): Int? {
        return getWidgetIdByAddictionIdUseCase.execute(addictionId)
    }

    fun getGeminiMotivationResponse(
        userProgress: UserProgress,
        addictionName: String
    ){
        viewModelScope.launch {
            val text = createTextForMotivationGeminiRequest(userProgress, addictionName)
            val request = createMotivationGeminiRequest(text)
            try {
                _motivationFlow.value = getGeminiResponseUseCase.execute(request)
            }
            catch (e : Exception){
                Log.d("GEMINI_API_TEST", e.message.toString())
                throw e
            }
        }
    }

    private fun createMotivationGeminiRequest(text: String): GeminiRequest {
        val partRequest = PartRequest(text)
        val contentRequest = ContentRequest(listOf(partRequest))
        val geminiRequest = GeminiRequest(listOf(contentRequest))
        return geminiRequest
    }

    private fun createTextForMotivationGeminiRequest(
        userProgress: UserProgress,
        addictionName: String
    ): String {

        val addiction = addictionName
        val currentStreak = userProgress.currentStreak
        val bestStreak = userProgress.bestStreak


        return """
        Твоя роль: Ты — легендарный наставник, синтез мудрого воина, капитана звездного корабля и священника. 
        Ты говоришь с глубокой харизмой, используешь мощные метафоры и говоришь по существу. 
        Ты не жалеешь, но и не даешь спуску.

        Задача: Создай мощную, эмоциональную и мотивационную речь для человека, который ведет битву. 
        Используй данные, которые я укажу в параметрах. Но не очень длинную речь.

        Вот мои параметры битвы с зависимостью:
        - Зависимость: $addiction
        - Количество дней: $currentStreak
        - Мой лучший результат: $bestStreak
        
       
        Структура ответа:
        Твоя речь должна состоять из трех частей, разделенных эмодзи:

        🛡️ ПРИЗНАНИЕ БОЛИ И СТАТУСА:
        Начни с признания того, какого черта мне сейчас тяжело. 
        Опиши мое текущее состояние (опираясь на «Сложность сегодняшнего дня»).
        Скажи, почему $currentStreak — это не просто цифра, а рубеж, который меняет химию мозга и закаляет дух. Также учитывай мой лучший результат $bestStreak

        🗡️ АРСЕНАЛ МОЩИ (Цитаты и Отсылки):
        Приведи вдохновляющие цитаты или отсылки, которые перекликаются с моей ситуацией.
        Используй сцены из фильмов, игр и псалмов. 
        Обязательно адаптируй смысл цитаты к борьбе с зависимостью.

        ⚔️ ПРИКАЗ К ДЕЙСТВИЮ:
        Заверши коротким, рубящим приказом. 
        Дай конкретный совет на ближайший час.
    """.trimIndent()
    }

}