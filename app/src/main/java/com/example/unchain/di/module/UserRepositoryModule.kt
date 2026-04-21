package com.example.unchain.di.module

import com.example.unchain.data.repository.UserRepositoryImpl
import com.example.unchain.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module

@Module
interface UserRepositoryModule {
    @Binds
    fun getUserRepository(userRepositoryImpl: UserRepositoryImpl) : UserRepository
}