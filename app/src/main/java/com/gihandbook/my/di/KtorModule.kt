package com.gihandbook.my.di

import android.content.Context
import androidx.navigation.NavController
import com.gihandbook.my.ui.screens.navigation.NavigationActions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class KtorModule {
    companion object {
        const val BASE_URL = "https://api.genshin.dev"
    }

    @Provides
    fun provideNavController(@ApplicationContext context: Context): NavController =
        NavController(context)

    @Provides
    fun provideActions(navController: NavController): NavigationActions =
        NavigationActions(navController)

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient = HttpClient(Android) {
        developmentMode = true
        install(Logging) {
            level = LogLevel.ALL
        }
//        defaultRequest {
//            url(BASE_URL)
//            contentType(ContentType.Application.Json)
//        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }
    }
}