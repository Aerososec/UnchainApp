package com.example.unchain.presentation.allAddictionsScreen.viewModel

import com.example.unchain.domain.models.AddictionWithProgress

data class AllAddictionUiState(
    val isLoading: Boolean = true,
    val addictionList : List<AddictionWithProgress> = emptyList<AddictionWithProgress>(),
    val error: String? = null
)
