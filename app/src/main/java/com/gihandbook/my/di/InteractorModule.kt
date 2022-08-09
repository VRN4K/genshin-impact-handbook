package com.gihandbook.my.di

import android.content.Context
import com.bumptech.glide.Glide
import com.gihandbook.my.data.net.repositories.CharactersNetRepository
import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.interactors.CharacterInteractor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractorModule {

    @Binds
    @Singleton
    abstract fun provideCharacterInteractor(characterInteractor: CharacterInteractor): ICharacterInteractor
}