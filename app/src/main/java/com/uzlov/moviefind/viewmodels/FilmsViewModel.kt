package com.uzlov.moviefind.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.uzlov.moviefind.repository.RepositoryPopularImpl

class FilmsViewModel : ViewModel() {
    private var liveDataToObserve: MutableLiveData<AppStateFilms> = MutableLiveData()

    fun getPopularFilms() : LiveData<AppStateFilms>{
        getLocalDataFilms()
        return liveDataToObserve
    }

    fun getFilmById(id: Int) : LiveData<AppStateFilm> {
        return RepositoryPopularImpl.loadFilmById(id)
    }

    private fun getLocalDataFilms(){
        liveDataToObserve = RepositoryPopularImpl.loadPopular()
    }

    fun getTopRatedFilms() : LiveData<AppStateFilms> {
        return RepositoryPopularImpl.loadTopRatedFilms()
    }
}