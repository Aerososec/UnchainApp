package com.example.unchain.presentation.shopScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.usecases.GetAllPersonalitiesUseCase
import com.example.unchain.domain.usecases.SelectPersonalityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopViewModel @Inject constructor(
    private val getAllPersonalitiesUseCase: GetAllPersonalitiesUseCase,
    private val selectPersonalityUseCase: SelectPersonalityUseCase
) : ViewModel(){

    private val _personalities = MutableStateFlow<List<Personality>>(emptyList())
    val personalities : StateFlow<List<Personality>>
        get() = _personalities

    fun getAllPersonalities(){
        viewModelScope.launch {
            _personalities.value = getAllPersonalitiesUseCase()
        }
    }

    fun selectPersonality(awp : AddictionWithPersonality){
        viewModelScope.launch {
            selectPersonalityUseCase(awp)
        }
    }
}