package com.uzlov.moviefind.repository

import androidx.lifecycle.LiveData
import com.uzlov.moviefind.application.App
import com.uzlov.moviefind.database.FilmEntityDB

object RepositoryLocalData {

    fun checkFilmIsFavorite(id : Int) : LiveData<Int>  = App.getFilmsDatabase.filmsDao().checkFilmIsFavorite(id)

    fun getFilmById(id: Long) : LiveData<FilmEntityDB> = App.getFilmsDatabase.filmsDao().getSavedFilmById(id)

    fun loadFavoriteFilms(): LiveData<List<FilmEntityDB>> = App.getFilmsDatabase.filmsDao().getSavedFavoriteFilm()

    fun addFavoriteFilm(film : FilmEntityDB){
        App.getFilmsDatabase.filmsDao().insertIntoFavoriteAll(film)
    }

    fun removeFilmFromFavorite(id: Long) {
        App.getFilmsDatabase.filmsDao().removeFilmFromFavorite(id)
    }
}