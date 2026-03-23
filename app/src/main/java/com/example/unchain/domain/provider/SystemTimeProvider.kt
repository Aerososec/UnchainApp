package com.example.unchain.domain.provider

import javax.inject.Inject

class SystemTimeProvider @Inject constructor() : TimeProvider{
    override fun now(): Long {
        return System.currentTimeMillis()
    }
}