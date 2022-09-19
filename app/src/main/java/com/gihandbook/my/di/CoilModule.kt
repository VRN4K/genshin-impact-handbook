package com.gihandbook.my.di

import android.content.Context
import coil.ImageLoader
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.gihandbook.my.data.net.repositories.CharactersNetRepository
import com.gihandbook.my.domain.datacontracts.ICharacterNetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoilModule {

    @Provides
    @Singleton
    fun provideImageRequestBuilder(@ApplicationContext context: Context) =
        ImageRequest.Builder(context)

    @Provides
    @Singleton
    fun provideImageLoader(@ApplicationContext context: Context) = ImageLoader(context)

}