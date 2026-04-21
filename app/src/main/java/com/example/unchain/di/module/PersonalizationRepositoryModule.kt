package com.example.unchain.di.module

import com.example.unchain.data.repository.PersonalizationRepositoryImpl
import com.example.unchain.domain.repositories.PersonalizationRepository
import dagger.Binds
import dagger.Module

@Module
interface PersonalizationRepositoryModule {
    @Binds
    fun bindPersonalizationRepository(impl : PersonalizationRepositoryImpl) : PersonalizationRepository
}