package com.example.unchain.presentation.allAddictionsScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unchain.domain.usecases.GetAllAddictionsUseCase
import com.example.unchain.domain.usecases.GetUserProgressUseCase
import com.example.unchain.domain.usecases.StartAddictionUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class AllAddictionsViewModel @Inject constructor(
    private val startAddictionUseCase: StartAddictionUseCase,
    private val getAllAddictionsUseCase: GetAllAddictionsUseCase,
    private val getUserProgressUseCase: GetUserProgressUseCase
) : ViewModel() {

    private val _state = getAllAddictionsUseCase.execute()
        .map { AllAddictionUiState(isLoading = false, addictionList = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AllAddictionUiState())

    val state = _state

    fun getUserProgress(addictionId : Int){
        viewModelScope.launch {
            getUserProgressUseCase.execute(addictionId)
        }
    }
}
