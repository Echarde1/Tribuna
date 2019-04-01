package com.danildanil.rickmorty.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.danildanil.rickmorty.data.model.Favourite
import com.danildanil.rickmorty.data.model.Response

@Database(entities = [Response.Character::class, Favourite::class], version = 1)
abstract class TribunaDb : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

}