package com.gihandbook.my.di

import android.content.Context
import android.content.res.Resources
import com.gihandbook.my.data.net.repositories.CharactersNetRepository
import com.gihandbook.my.domain.datacontracts.ICharacterNetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources =
        context.resources

    @Provides
    @Singleton
    fun provideCharactersRepository(httpClient: HttpClient): ICharacterNetRepository =
        CharactersNetRepository(httpClient)

}