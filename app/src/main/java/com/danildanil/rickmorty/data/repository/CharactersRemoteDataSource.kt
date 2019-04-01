package com.danildanil.rickmorty.data.repository

import com.danildanil.rickmorty.data.api.Endpoints
import com.danildanil.rickmorty.data.model.Favourite
import com.danildanil.rickmorty.data.model.Response
import com.danildanil.rickmorty.presentation.presenter.CharactersPresenter
import io.reactivex.Single
import javax.inject.Inject

class CharactersRemoteDataSource @Inject constructor(
    private val api: Endpoints
) : CharactersDataSource {

    override fun loadChar(id: Long): Single<List<Response.Character>> =
        api.getCharacter(id).flatMap {
            Single.just(listOf(it))
        }

    override fun loadCharacters(page: Int): Single<List<Response.Character>> =
        api.getCharactersList(page)
            .doOnSuccess { CharactersPresenter.maxPages = it.info.pages.toInt() }
            .flatMap { Single.just(it.results) }

    override fun deleteFavourite(id: Long) = throw Error()

    override fun insertList(list: List<Response.Character>) = throw Error()

    override fun checkFavourite(id: Long) = throw Error()

    override fun insertFavourite(fav: Favourite) = throw Error()

    override fun loadFavouritesList(): Single<List<Response.Character>> = throw Error()

}