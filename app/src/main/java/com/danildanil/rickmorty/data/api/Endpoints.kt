package com.danildanil.rickmorty.data.api

import com.danildanil.rickmorty.data.model.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Endpoints {

    @GET("character/")
    fun getCharactersList(@Query("page") page: Int): Single<Response>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: Long): Single<Response.Character>
}
