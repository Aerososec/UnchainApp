package com.example.unchain.presentation.userProgressScreen.viewModel

import com.example.unchain.domain.models.UserProgress

data class ProgressUiState(
    val isLoading : Boolean = true,
    val userProgress : UserProgress? = null,
    val error : String? = null,
    val shouldShowTimePicker : Boolean = false
)