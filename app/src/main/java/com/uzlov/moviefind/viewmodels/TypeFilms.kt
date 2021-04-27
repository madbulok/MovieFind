package com.uzlov.moviefind.viewmodels

import java.io.Serializable

sealed class TypeFilms : Serializable {
    object PopularFilm : TypeFilms()
    object RecommendFilm : TypeFilms()
    object UpcomingFilm : TypeFilms()
    object ErrorFilm : TypeFilms()
}
