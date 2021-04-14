package com.uzlov.moviefind.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FilmEntityDB(
    @PrimaryKey val id : Long,
    val title : String,
    val picture : String,
    val rating : Float,
    val description : String,
    val favorite: Boolean = false
)