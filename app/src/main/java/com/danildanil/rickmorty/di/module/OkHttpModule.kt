package com.danildanil.rickmorty.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class OkHttpModule {

    @Provides
    @Singleton
    fun providerBaseBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .connectTimeout(360, TimeUnit.SECONDS)
        .readTimeout(360, TimeUnit.SECONDS)
        .writeTimeout(360, TimeUnit.SECONDS)

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache =
        Cache(application.cacheDir, 10 * 1024 * 1024)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideDispatcher(): Dispatcher = Dispatcher().apply { maxRequests = 3 }

    @Provides
    @Singleton
    fun provideOkHttp(
        builder: OkHttpClient.Builder,
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        dispatcher: Dispatcher
    ): OkHttpClient = builder
        .cache(cache)
        .addInterceptor(loggingInterceptor)
        .dispatcher(dispatcher)
        .build()

}

