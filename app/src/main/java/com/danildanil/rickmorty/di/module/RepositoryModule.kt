package com.danildanil.rickmorty.di.module

import com.danildanil.rickmorty.data.api.Endpoints
import com.danildanil.rickmorty.data.database.CharactersDao
import com.danildanil.rickmorty.data.repository.CharactersDataSource
import com.danildanil.rickmorty.data.repository.CharactersLocalDataSource
import com.danildanil.rickmorty.data.repository.CharactersRemoteDataSource
import com.danildanil.rickmorty.data.repository.CharactersRepository
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Local
    @Singleton
    fun provideLocalDataSource(dao: CharactersDao): CharactersDataSource =
        CharactersLocalDataSource(dao)

    @Provides
    @Remote
    @Singleton
    fun provideRemoteDataSource(api: Endpoints): CharactersDataSource =
        CharactersRemoteDataSource(api)

    @Provides
    @Repo
    @Singleton
    fun provideDataSource(@Remote remote: CharactersDataSource, @Local local: CharactersDataSource): CharactersDataSource =
        CharactersRepository(remote, local)

    @Qualifier
    @MustBeDocumented
    annotation class Local

    @Qualifier
    @MustBeDocumented
    annotation class Remote

    @Qualifier
    @MustBeDocumented
    annotation class Repo

}