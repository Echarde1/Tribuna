package com.danildanil.rickmorty.di.module

import com.danildanil.rickmorty.data.api.ApiServiceHolder
import com.danildanil.rickmorty.data.api.Endpoints
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun providesEndpoints(
        retrofit: Retrofit,
        apiServiceHolder: ApiServiceHolder
    ): Endpoints {
        val endpoints = retrofit.create(Endpoints::class.java)
        apiServiceHolder.apiService = endpoints
        return endpoints
    }

    @Provides
    @Singleton
    fun provideApiServiceHolder(): ApiServiceHolder = ApiServiceHolder()

}