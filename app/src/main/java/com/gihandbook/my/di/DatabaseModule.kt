package com.gihandbook.my.di

import android.content.Context
import androidx.room.Room
import com.gihandbook.my.data.storage.GIDataBase
import com.gihandbook.my.data.storage.dao.CharactersDao
import com.gihandbook.my.data.storage.dao.WeaponsDao
import com.gihandbook.my.data.storage.entities.ElementConverter
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCharactersDao(database: GIDataBase): CharactersDao = database.CharactersDao()

    @Provides
    @Singleton
    fun provideWeaponsDao(database: GIDataBase): WeaponsDao = database.WeaponsDao()

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context, gson: Gson): GIDataBase =
        Room.databaseBuilder(
            context,
            GIDataBase::class.java,
            "gi-database"
        ).addTypeConverter(ElementConverter(gson))
            .fallbackToDestructiveMigration()
            .build()

}