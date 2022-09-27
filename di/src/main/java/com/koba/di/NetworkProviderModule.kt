package com.koba.di

import com.koba.data.network.RetrofitProvider
import com.koba.data.network.impl.RetrofitProviderImpl
import com.koba.data.service.InterparkService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkProviderModule {
    @Binds
    @Singleton
    fun bindRetrofitProvider(
        retrofitProviderImpl: RetrofitProviderImpl
    ): RetrofitProvider
}
