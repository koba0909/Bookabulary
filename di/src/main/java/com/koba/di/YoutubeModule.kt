package com.koba.di

import com.koba.data.network.RetrofitProvider
import com.koba.data.repository.MockYoutubeRepositoryImpl
import com.koba.data.repository.YoutubeRepositoryImpl
import com.koba.data.service.InterparkService
import com.koba.data.service.YoutubeService
import com.koba.domain.repository.YoutubeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
interface YoutubeModule {

    // @Binds
    // @ViewModelScoped
    // fun bindYoutubeRepository(
    //     youtubeRepositoryImpl: YoutubeRepositoryImpl
    // ): YoutubeRepository

    @Binds
    @ViewModelScoped
    fun bindYoutubeRepository(
        mockYoutubeRepositoryImpl: MockYoutubeRepositoryImpl
    ): YoutubeRepository

    companion object {
        private const val BASE_URL_YOUTUBE = "https://www.googleapis.com"

        @Provides
        @ViewModelScoped
        fun provideYoutubeService(
            retrofitProvider: RetrofitProvider
        ): YoutubeService = retrofitProvider
            .getRetrofitBuilder(
                baseUrl = BASE_URL_YOUTUBE
            ).build()
            .create(YoutubeService::class.java)
    }
}