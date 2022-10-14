package com.gihandbook.my.di

import com.gihandbook.my.domain.datacontracts.ICharacterInteractor
import com.gihandbook.my.domain.datacontracts.IWeaponInteractor
import com.gihandbook.my.domain.interactors.CharacterInteractor
import com.gihandbook.my.domain.interactors.WeaponInteractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InteractorModule {

    @Binds
    @Singleton
    abstract fun provideCharacterInteractor(characterInteractor: CharacterInteractor): ICharacterInteractor

    @Binds
    @Singleton
    abstract fun provideWeaponInteractor(weaponInteractor: WeaponInteractor): IWeaponInteractor
}