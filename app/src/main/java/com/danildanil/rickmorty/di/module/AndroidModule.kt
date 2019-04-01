package com.danildanil.rickmorty.di.module

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import android.content.res.Resources
import android.view.LayoutInflater
import com.danildanil.rickmorty.di.PerApplication
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AndroidModule {

    @Provides
    @Singleton
    fun context(app: Application): Context =
        app.applicationContext

    @Provides
    @Singleton
    fun provideResources(application: Application): Resources = application.resources

    @Provides
    @Singleton
    fun assetManager(ctx: Context): AssetManager =
        ctx.assets

    @Provides
    @Singleton
    fun layoutInflater(ctx: Context): LayoutInflater =
        LayoutInflater.from(ctx)

    @Provides
    @Singleton
    fun provideActivityManager(context: Context): ActivityManager =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    @Provides
    @PerApplication
    @Named("cacheDir")
    fun getCacheDir(ctx: Context): String = ctx.cacheDir.toString()

    @Provides
    @PerApplication
    fun sharedPreferences(ctx: Context): SharedPreferences =
        ctx.getSharedPreferences(SHARED_PREF_DB, Context.MODE_PRIVATE)

    companion object {
        private const val SHARED_PREF_DB = "SHARED_PREF_DB"
    }
}