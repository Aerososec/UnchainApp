package com.example.unchain.presentation.userProgressScreen.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unchain.domain.models.UserProgress
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
    private val getWidgetIdByAddictionIdUseCase: GetWidgetIdByAddictionIdUseCase
) : ViewModel() {

    val addictionId = MutableStateFlow<Int>(-1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val userProgressFlow = addictionId
        .filter { it > -1 }
        .flatMapLatest { getUserProgressUseCase.execute(it) }
        .map {
            val shouldShowTimePicker = it?.hour == null && it?.minute == null
            ProgressUiState(isLoading = false, userProgress = it, shouldShowTimePicker = shouldShowTimePicker)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ProgressUiState())

    fun loadProgress(id: Int){
        addictionId.value = id
    }

    fun markDaySuccess(userProgress: UserProgress, addictionId: Int){
        viewModelScope.launch {
            val update = markDaySuccessUseCase.execute(userProgress, addictionId)
        }
    }

    fun markDayFail(userProgress: UserProgress, addictionId: Int){
        viewModelScope.launch {
            val update = markDayFailUseCase.execute(userProgress, addictionId)
        }
    }

    fun setReminder(addictionId: Int, hour : Int, minute : Int){
        viewModelScope.launch {
            setReminderUseCase.execute(addictionId, hour, minute)
        }
    }

    suspend fun getWidgetIdByAddictionId(addictionId : Int) : Int?{
        return getWidgetIdByAddictionIdUseCase.execute(addictionId)
    }

}