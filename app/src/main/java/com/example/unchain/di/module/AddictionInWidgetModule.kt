package com.example.unchain.di.module

import com.example.unchain.data.repository.AddictionInWidgetRepositoryImpl
import com.example.unchain.domain.repositories.AddictionInWidgetRepository
import dagger.Binds
import dagger.Module

@Module
interface AddictionInWidgetModule {
    @Binds
    fun bindAddictionInWidgetRepository(impl : AddictionInWidgetRepositoryImpl) : AddictionInWidgetRepository
}