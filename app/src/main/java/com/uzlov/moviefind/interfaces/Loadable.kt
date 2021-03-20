package com.uzlov.moviefind.interfaces

@FunctionalInterface
public interface Loadable<T> {
    fun loadPopular(results : List<T>)
}