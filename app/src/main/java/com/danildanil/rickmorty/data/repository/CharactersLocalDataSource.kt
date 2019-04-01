package com.danildanil.rickmorty.data.repository

import com.danildanil.rickmorty.data.database.CharactersDao
import com.danildanil.rickmorty.data.model.Favourite
import com.danildanil.rickmorty.data.model.Response
import io.reactivex.Single
import javax.inject.Inject

class CharactersLocalDataSource @Inject constructor(
    private val charsDao: CharactersDao
) : CharactersDataSource {

    override fun loadCharacters(page: Int): Single<List<Response.Character>> = charsDao.loadCharacters(page)

    override fun loadChar(id: Long): Single<List<Response.Character>> = charsDao.loadChar(id)

    override fun deleteFavourite(id: Long) = charsDao.deleteFavourite(id)

    override fun insertList(list: List<Response.Character>) = charsDao.insertChars(list)

    override fun checkFavourite(id: Long): Single<List<Favourite>> = charsDao.getFavouriteById(id)

    override fun insertFavourite(fav: Favourite) = charsDao.insertFavourite(fav)

    override fun loadFavouritesList(): Single<List<Response.Character>> = charsDao.loadFavourites()

}