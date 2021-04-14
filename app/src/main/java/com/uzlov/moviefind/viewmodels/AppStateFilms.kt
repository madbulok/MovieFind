package com.uzlov.moviefind.viewmodels

import com.uzlov.moviefind.model.PopularFilms


sealed class AppStateFilms {
    data class Success(val filmsData: PopularFilms) : AppStateFilms()
    data class Error(val error: Throwable) : AppStateFilms()
    object Loading : AppStateFilms()
}
