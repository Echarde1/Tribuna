package com.danildanil.rickmorty.data.repository

import com.danildanil.rickmorty.data.model.Favourite
import com.danildanil.rickmorty.data.model.Response
import io.reactivex.Single


interface CharactersDataSource {

    fun loadCharacters(page: Int): Single<List<Response.Character>>

    fun deleteFavourite(id: Long)

    fun loadChar(id: Long): Single<List<Response.Character>>

    fun insertList(list: List<Response.Character>)

    fun checkFavourite(id: Long): Single<List<Favourite>>

    fun insertFavourite(fav: Favourite)

    fun loadFavouritesList(): Single<List<Response.Character>>

}