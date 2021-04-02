package com.uzlov.moviefind.interfaces

import androidx.lifecycle.MutableLiveData
import com.uzlov.moviefind.model.Film
import com.uzlov.moviefind.model.PopularFilms

@FunctionalInterface
public interface Loadable {
    fun loadPopular() : MutableLiveData<PopularFilms>
    fun loadFilmById(id: Int) : MutableLiveData<Film>
}