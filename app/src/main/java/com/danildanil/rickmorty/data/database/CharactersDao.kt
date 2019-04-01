package com.danildanil.rickmorty.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.danildanil.rickmorty.data.Config
import com.danildanil.rickmorty.data.model.Favourite
import com.danildanil.rickmorty.data.model.Response
import io.reactivex.Single

@Dao
interface CharactersDao {

    @Query("SELECT * FROM ${Config.FAVOURITE_TABLE_NAME} WHERE character_id == :id")
    fun getFavouriteById(id: Long): Single<List<Favourite>>

    @Query("SELECT * FROM ${Config.CHARACTERS_TABLE_NAME} WHERE id >= ((:page - 1) * 20 + 1) AND id <= :page * 20 ")
    fun loadCharacters(page: Int): Single<List<Response.Character>>

    @Query("SELECT * FROM ${Config.CHARACTERS_TABLE_NAME} WHERE id = :id")
    fun loadChar(id: Long): Single<List<Response.Character>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertChars(list: List<Response.Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavourite(favourite: Favourite)

    @Query("DELETE FROM ${Config.FAVOURITE_TABLE_NAME} WHERE character_id == :id")
    fun deleteFavourite(id: Long)

    @Query("SELECT chars.id, chars.imageUrl, chars.name, chars.status FROM ${Config.CHARACTERS_TABLE_NAME} AS chars JOIN ${Config.FAVOURITE_TABLE_NAME} AS fav ON chars.id == fav.character_id ")
    fun loadFavourites(): Single<List<Response.Character>>

}