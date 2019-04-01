package com.danildanil.rickmorty.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.danildanil.rickmorty.data.Config
import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("info") val info: Info,
    @SerializedName("results") val results: List<Character>
) {

    data class Info(
        @SerializedName("pages") val pages: String,
        @SerializedName("next") val nextPage: String
    )

    @Entity(tableName = Config.CHARACTERS_TABLE_NAME)
    data class Character(

        @SerializedName("id")
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        val id: Long,

        @SerializedName("name") val name: String,
        @SerializedName("status") val status: String,
        @SerializedName("image") val imageUrl: String
    )
}