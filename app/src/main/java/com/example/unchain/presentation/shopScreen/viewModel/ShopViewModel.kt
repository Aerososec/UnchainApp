package com.example.unchain.presentation.shopScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unchain.domain.models.UserProgress
import com.example.unchain.domain.models.personalization.AddictionWithPersonality
import com.example.unchain.domain.models.personalization.Personality
import com.example.unchain.domain.models.personalization.PersonalityState
import com.example.unchain.domain.usecases.GetAllPersonalitiesUseCase
import com.example.unchain.domain.usecases.GetPersonalityIdByAddictionIdUseCase
import com.example.unchain.domain.usecases.GetPurchasedIdsUseCase
import com.example.unchain.domain.usecases.GetUserProgressUseCase
import com.example.unchain.domain.usecases.SelectPersonalityUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class ShopViewModel @Inject constructor(
    private val getAllPersonalitiesUseCase: GetAllPersonalitiesUseCase,
    private val selectPersonalityUseCase: SelectPersonalityUseCase,
    private val userProgressUseCase: GetUserProgressUseCase,
    private val getPurchasedIdsUseCase: GetPurchasedIdsUseCase,
    private val getPersonalityIdByAddictionIdUseCase: GetPersonalityIdByAddictionIdUseCase
) : ViewModel(){

    private var _addictionId = MutableStateFlow(-1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val personalities : StateFlow<List<Personality>> = _addictionId
        .filter { it > -1 }
        .flatMapLatest { addictionId ->
            combine(
                getAllPersonalitiesUseCase(),
                getPurchasedIdsUseCase(addictionId),
                getPersonalityIdByAddictionIdUseCase(addictionId)
            ) { personalities, purchasedIds, selectedId ->
                personalities.map{ personality ->
                    val state = when (personality.id) {
                        selectedId -> PersonalityState.SELECTED.state
                        in purchasedIds -> PersonalityState.UNLOCKED.state
                        else -> PersonalityState.LOCKED.state
                    }

                    personality.copy(state = state)
                }

            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    suspend fun selectPersonality(awp : AddictionWithPersonality){
        selectPersonalityUseCase(awp)
    }

    fun getUserProgress(addictionId : Int) : Flow<UserProgress?>{
        _addictionId.value = addictionId
        return userProgressUseCase.execute(addictionId)
    }

}