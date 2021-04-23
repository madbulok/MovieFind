package com.uzlov.moviefind.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uzlov.moviefind.database.FilmEntityDB
import com.uzlov.moviefind.model.ActorDescription
import com.uzlov.moviefind.model.Credits
import com.uzlov.moviefind.repository.RepositoryLocalData

import com.uzlov.moviefind.repository.RepositoryRemoteImpl

class FilmsViewModel : ViewModel() {

    fun getPopularFilms() : LiveData<AppStateFilms>{
        return getLocalDataFilms()
    }

    fun getFilmsByName(name : String, isAdult: Boolean = false) : LiveData<AppStateFilms>{
        return RepositoryRemoteImpl.searchFilmsByName(name, isAdult)
    }

    fun getMyFavoritesFilms() : LiveData<List<FilmEntityDB>> {
        return RepositoryLocalData.loadFavoriteFilms()
    }

    fun getFilmById(id: Int) : LiveData<AppStateFilm> {
        return RepositoryRemoteImpl.loadFilmById(id)
    }

    fun getCreditFilmByID(id: Int) : LiveData<Credits> {
        return RepositoryRemoteImpl.getCreditsMoviesById(id)
    }

    private fun getLocalDataFilms() : LiveData<AppStateFilms>{
        return RepositoryRemoteImpl.loadPopular()
    }

    fun getTopRatedFilms() : LiveData<AppStateFilms> {
        return RepositoryRemoteImpl.loadTopRatedFilms()
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

    fun getActorDescriptionsById(id: Int): MutableLiveData<ActorDescription> {
        return RepositoryRemoteImpl.getActorDescriptionById(id)
    }
}