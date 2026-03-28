package com.example.unchain.di.module

import com.example.unchain.data.repository.GeminiRepositoryImpl
import com.example.unchain.domain.repositories.GeminiRepository
import dagger.Binds
import dagger.Module

@Module
interface GeminiRepositoryModel {
    @Binds
    fun bindGeminiRepository(geminiRepositoryImpl: GeminiRepositoryImpl) : GeminiRepository
}