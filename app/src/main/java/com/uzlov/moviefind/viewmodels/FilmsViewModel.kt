package com.uzlov.moviefind.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uzlov.moviefind.model.Film
import com.uzlov.moviefind.model.PopularFilms

import com.uzlov.moviefind.repository.RepositoryPopularImpl

class FilmsViewModel : ViewModel() {
    private var liveDataToObserve: MutableLiveData<PopularFilms> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.N)
    fun getPopularFilms() : LiveData<PopularFilms>{
        getLocalDataFilms()
        return liveDataToObserve
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getFilmById(id: Int) : LiveData<Film> {
        return RepositoryPopularImpl.loadFilmById(id)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLocalDataFilms(){
        liveDataToObserve = RepositoryPopularImpl.loadPopular()
    }
}