package com.gihandbook.my.di

import android.content.Context
import android.content.res.Resources
import com.bumptech.glide.Glide
import com.gihandbook.my.data.net.repositories.CharactersNetRepository
import com.gihandbook.my.data.net.repositories.WeaponsNetRepository
import com.gihandbook.my.domain.datacontracts.ICharacterNetRepository
import com.gihandbook.my.domain.datacontracts.IWeaponNetRepository
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
    fun provideContext(@ApplicationContext context: Context): Context =
        context

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources =
        context.resources

    @Provides
    @Singleton
    fun provideGlide(@ApplicationContext context: Context) = Glide.with(context)

    @Provides
    @Singleton
    fun provideCharactersRepository(httpClient: HttpClient): ICharacterNetRepository =
        CharactersNetRepository(httpClient)

    @Provides
    @Singleton
    fun provideWeaponRepository(httpClient: HttpClient): IWeaponNetRepository =
        WeaponsNetRepository(httpClient)
}