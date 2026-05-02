package com.example.unchain.presentation.shopScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.usecases.GetAllPersonalitiesUseCase
import com.example.unchain.domain.usecases.GetUserProgressUseCase
import com.example.unchain.domain.usecases.SelectPersonalityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopViewModel @Inject constructor(
    private val getAllPersonalitiesUseCase: GetAllPersonalitiesUseCase,
    private val selectPersonalityUseCase: SelectPersonalityUseCase,
    private val userProgressUseCase: GetUserProgressUseCase
) : ViewModel(){

    private val _personalities = MutableStateFlow<List<Personality>>(emptyList())
    val personalities : StateFlow<List<Personality>>
        get() = _personalities

    suspend fun getAllPersonalities(){
        _personalities.value = getAllPersonalitiesUseCase()
    }

    suspend fun selectPersonality(awp : AddictionWithPersonality){
        selectPersonalityUseCase(awp)
    }

    suspend fun getUserProgress(addictionId : Int) : UserProgress?{
        val userProgress = userProgressUseCase.execute(addictionId).firstOrNull()
        return userProgress
    }
}