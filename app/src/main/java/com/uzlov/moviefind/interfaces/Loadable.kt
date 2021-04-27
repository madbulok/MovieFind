package com.uzlov.moviefind.interfaces

import androidx.lifecycle.MutableLiveData
import com.uzlov.moviefind.model.PopularFilms
import com.uzlov.moviefind.viewmodels.AppStateFilms
import com.uzlov.moviefind.viewmodels.AppStateFilm
import retrofit2.Callback

@FunctionalInterface
public interface Loadable {
    fun loadPopular(callback: Callback<PopularFilms>)
    fun loadFilmById(id: Int) : MutableLiveData<AppStateFilm>
    fun loadTopRatedFilms(call: Callback<PopularFilms>)
}