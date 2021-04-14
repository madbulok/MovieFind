package com.uzlov.moviefind.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.uzlov.moviefind.database.FilmEntityDB
import com.uzlov.moviefind.model.Credits
import com.uzlov.moviefind.repository.RepositoryLocalData

import com.uzlov.moviefind.repository.RepositoryPopularImpl

class FilmsViewModel : ViewModel() {

    fun getPopularFilms() : LiveData<AppStateFilms>{
        return getLocalDataFilms()
    }

    fun getFilmsByName(name : String, isAdult: Boolean = false) : LiveData<AppStateFilms>{
        return RepositoryPopularImpl.searchFilmsByName(name, isAdult)
    }

    fun getMyFavoritesFilms() : LiveData<List<FilmEntityDB>> {
        return RepositoryLocalData.loadFavoriteFilms()
    }

    fun getFilmById(id: Int) : LiveData<AppStateFilm> {
        return RepositoryPopularImpl.loadFilmById(id)
    }

    fun getCreditFilmByID(id: Int) : LiveData<Credits> {
        return RepositoryPopularImpl.getCreditsMoviesById(id)
    }

    private fun getLocalDataFilms() : LiveData<AppStateFilms>{
        return RepositoryPopularImpl.loadPopular()
    }

    fun getTopRatedFilms() : LiveData<AppStateFilms> {
        return RepositoryPopularImpl.loadTopRatedFilms()
    }

    fun addFilmToFavorite(film : FilmEntityDB){
        RepositoryLocalData.addFavoriteFilm(film)
    }

    fun filmIsFavorite(id: Int) : LiveData<Int> {
        return RepositoryLocalData.checkFilmIsFavorite(id)
    }

    fun removeFilmFromSavedFavorite(id: Long){
        RepositoryLocalData.removeFilmFromFavorite(id)
    }
}