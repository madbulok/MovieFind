package com.uzlov.moviefind.model

data class PopularFilms(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)