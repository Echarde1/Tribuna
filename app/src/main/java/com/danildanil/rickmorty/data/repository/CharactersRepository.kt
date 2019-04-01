package com.danildanil.rickmorty.data.repository

import com.danildanil.rickmorty.data.model.Favourite
import com.danildanil.rickmorty.data.model.Response
import com.danildanil.rickmorty.di.module.RepositoryModule.Local
import com.danildanil.rickmorty.di.module.RepositoryModule.Remote
import io.reactivex.Single
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    @Remote private val api: CharactersDataSource,
    @Local private val local: CharactersDataSource
) : CharactersDataSource {

    private val charactersCache: MutableList<Response.Character> = mutableListOf()
    private val charactersFavourite: MutableList<Favourite> = mutableListOf()

    override fun loadCharacters(page: Int): Single<List<Response.Character>> =
        local.loadCharacters(page)
            .doOnSuccess {
                if (!charactersCache.containsAll(it)) {
                    charactersCache.addAll(it)
                }
            }
            .filter { !it.isEmpty() }
            .switchIfEmpty(api.loadCharacters(page)
                .doOnSuccess { insertList(it) }
            )

    override fun insertList(list: List<Response.Character>) { local.insertList(list) }

    override fun loadChar(id: Long): Single<List<Response.Character>> {
        charactersCache.find { it.id == id }?.let {
            return Single.just(listOf(it))
        }

        return local.loadChar(id)
            .filter { !it.isEmpty() }
            .switchIfEmpty(api.loadChar(id))
    }

    override fun checkFavourite(id: Long): Single<List<Favourite>> {
        charactersFavourite.find { it.charId == id }?.let {
            return Single.just(listOf(it))
        }

        return local.checkFavourite(id)
            .doOnSuccess {
                if (it.isNotEmpty() && !charactersFavourite.containsAll(it)) {
                    charactersFavourite.add(it[0])
                }
            }
    }

    override fun deleteFavourite(id: Long) {
        charactersFavourite.forEach {
            if (it.charId == id) charactersFavourite.remove(it)
        }
        local.deleteFavourite(id)
    }

    override fun insertFavourite(fav: Favourite) {
        charactersFavourite.add(fav)
        local.insertFavourite(fav)
    }

    override fun loadFavouritesList(): Single<List<Response.Character>> = local.loadFavouritesList()

}