package com.danildanil.rickmorty.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.danildanil.rickmorty.data.Config

@Entity(
    tableName = Config.FAVOURITE_TABLE_NAME, foreignKeys =
    [ForeignKey(
        entity = Response.Character::class,
        parentColumns = ["id"],
        childColumns = ["character_id"]
    )]
)
data class Favourite (

    @ColumnInfo(name = "character_id")
    val charId: Long

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

}