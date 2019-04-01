package com.danildanil.rickmorty.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.danildanil.rickmorty.data.Config
import com.danildanil.rickmorty.data.database.CharactersDao
import com.danildanil.rickmorty.data.database.TribunaDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideTribunaDao(context: Context): TribunaDb =
        Room.databaseBuilder(context, TribunaDb::class.java, Config.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideFavouriteDao(tribunaDb: TribunaDb): CharactersDao =
        tribunaDb.charactersDao()

}