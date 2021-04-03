package com.uzlov.moviefind.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uzlov.moviefind.model.Film
import com.uzlov.moviefind.model.PopularFilms

import com.uzlov.moviefind.repository.RepositoryPopularImpl

class FilmsViewModel : ViewModel() {
    private var liveDataToObserve: MutableLiveData<PopularFilms> = MutableLiveData()

    fun getPopularFilms() : LiveData<PopularFilms>{
        getLocalDataFilms()
        return liveDataToObserve
    }

    fun getFilmById(id: Int) : LiveData<Film> {
        return RepositoryPopularImpl.loadFilmById(id)
    }

    private fun getLocalDataFilms(){
        liveDataToObserve = RepositoryPopularImpl.loadPopular()
    }

    fun getTopRatedFilms() : LiveData<PopularFilms> {
        return RepositoryPopularImpl.loadTopRatedFilms()
    }
}