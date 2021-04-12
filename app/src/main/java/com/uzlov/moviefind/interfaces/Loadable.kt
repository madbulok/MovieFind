package com.uzlov.moviefind.interfaces

import androidx.lifecycle.MutableLiveData
import com.uzlov.moviefind.viewmodels.AppStateFilms
import com.uzlov.moviefind.viewmodels.AppStateFilm

@FunctionalInterface
public interface Loadable {
    fun loadPopular() : MutableLiveData<AppStateFilms>
    fun loadFilmById(id: Int) : MutableLiveData<AppStateFilm>
    fun loadTopRatedFilms(): MutableLiveData<AppStateFilms>
}