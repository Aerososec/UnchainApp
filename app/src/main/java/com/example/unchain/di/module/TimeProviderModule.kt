package com.example.unchain.di.module

import com.example.unchain.domain.provider.SystemTimeProvider
import com.example.unchain.domain.provider.TimeProvider
import dagger.Binds
import dagger.Module

@Module
interface TimeProviderModule {

    @Binds
    fun getTimeProvider(systemTimeProvider: SystemTimeProvider) : TimeProvider
}