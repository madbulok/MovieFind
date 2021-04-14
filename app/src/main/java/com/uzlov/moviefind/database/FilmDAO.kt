package com.uzlov.moviefind.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FilmDAO {
    @Query("SELECT * FROM FilmEntityDB")
    fun getAllSavedFilms() : LiveData<List<FilmEntityDB>>

    @Query("SELECT * FROM FilmEntityDB WHERE id=:id")
    fun getSavedFilmById(id : Long) : LiveData<FilmEntityDB>

    @Query("SELECT * FROM FilmEntityDB WHERE favorite=1")
    fun getSavedFavoriteFilm() : LiveData<List<FilmEntityDB>>

    @Query("SELECT COUNT(id) FROM FilmEntityDB WHERE id=:id")
    fun checkFilmIsFavorite(id: Int) : LiveData<Int>

    @Query("DELETE FROM FilmEntityDB WHERE id=:id")
    fun removeFilmFromFavorite(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIntoFavoriteAll(vararg users: FilmEntityDB)

    @Delete
    fun delete(user: FilmEntityDB)
}