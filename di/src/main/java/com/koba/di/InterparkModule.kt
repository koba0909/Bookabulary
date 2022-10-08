package com.koba.di

import com.koba.data.network.RetrofitProvider
import com.koba.data.repository.InterparkRepositoryImpl
import com.koba.data.service.InterparkService
import com.koba.domain.repository.InterparkRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface InterparkModule {
    @Binds
    @ViewModelScoped
    fun bindInterparkRepository(
        interparkRepositoryImpl: InterparkRepositoryImpl
    ): InterparkRepository

    companion object {
        const val BASE_URL_INTERPARK = "http://book.interpark.com"

        @Provides
        @ViewModelScoped
        fun provideInterparkService(
            retrofitProvider: RetrofitProvider
        ): InterparkService = retrofitProvider
            .getRetrofitBuilder(
                baseUrl = BASE_URL_INTERPARK
            ).build()
            .create(InterparkService::class.java)
    }
}
