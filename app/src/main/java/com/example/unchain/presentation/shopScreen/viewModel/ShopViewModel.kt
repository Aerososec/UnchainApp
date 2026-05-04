package com.example.unchain.presentation.shopScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.usecases.GetAllPersonalitiesUseCase
import com.example.unchain.domain.usecases.GetUserProgressUseCase
import com.example.unchain.domain.usecases.SelectPersonalityUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopViewModel @Inject constructor(
    private val getAllPersonalitiesUseCase: GetAllPersonalitiesUseCase,
    private val selectPersonalityUseCase: SelectPersonalityUseCase,
    private val userProgressUseCase: GetUserProgressUseCase
) : ViewModel(){

    val personalities: StateFlow<List<Personality>> = getAllPersonalitiesUseCase()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )


    suspend fun selectPersonality(awp : AddictionWithPersonality){
        selectPersonalityUseCase(awp)
    }

    fun getUserProgress(addictionId : Int) : Flow<UserProgress?>{
        return userProgressUseCase.execute(addictionId)
    }

}