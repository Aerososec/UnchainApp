package com.example.unchain.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unchain.di.ViewModelFactory
import com.example.unchain.di.annotation.ViewModelKey
import com.example.unchain.presentation.allAddictionsScreen.viewModel.AllAddictionsViewModel
import com.example.unchain.presentation.userProgressScreen.viewModel.MessageViewModel
import com.example.unchain.presentation.userProgressScreen.viewModel.ProgressViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AllAddictionsViewModel::class)
    fun bindAddictionViewModel(allAddictionsViewModel: AllAddictionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProgressViewModel::class)
    fun bindProgressViewModel(progressViewModel: ProgressViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel::class)
    fun bindMessageViewModel(messageViewModel: MessageViewModel) : ViewModel
}