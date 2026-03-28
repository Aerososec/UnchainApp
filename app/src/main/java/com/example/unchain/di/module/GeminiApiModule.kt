package com.example.unchain.di.module


import com.example.unchain.data.remote.GeminiApiService
import com.example.unchain.di.annotation.ApplicationSingleton
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

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

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(converter)
            .build()

        return retrofit.create<GeminiApiService>(GeminiApiService::class.java)
    }
}

