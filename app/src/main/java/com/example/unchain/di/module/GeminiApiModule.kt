package com.example.unchain.di.module


import com.example.unchain.data.remote.GeminiApiService
import com.example.unchain.di.annotation.ApplicationSingleton
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

@Module
class GeminiApiModule {

    @ApplicationSingleton
    @Provides
    fun provideGeminiApi() : GeminiApiService{
        val baseUrl = "https://generativelanguage.googleapis.com/"
        val converter = Json{
            ignoreUnknownKeys = true
            coerceInputValues = true
        }.asConverterFactory("application/json".toMediaType())

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converter)
            .build()

        return retrofit.create<GeminiApiService>(GeminiApiService::class.java)
    }
}

