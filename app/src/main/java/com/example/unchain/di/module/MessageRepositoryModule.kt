package com.example.unchain.di.module

import com.example.unchain.data.repository.MessageRepositoryImpl
import com.example.unchain.domain.repositories.MessageRepository
import dagger.Binds
import dagger.Module

@Module
interface MessageRepositoryModule {
    @Binds
    fun bindMessageRepository(messageRepository: MessageRepositoryImpl) : MessageRepository
}