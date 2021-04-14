package com.uzlov.moviefind.viewmodels

import com.uzlov.moviefind.model.Film


sealed class AppStateFilm {
    data class Success(val filmsData: Film) : AppStateFilm()
    data class Error(val error: Throwable) : AppStateFilm()
    object Loading : AppStateFilm()
}
