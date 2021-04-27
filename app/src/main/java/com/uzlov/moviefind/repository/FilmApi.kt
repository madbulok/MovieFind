package com.uzlov.moviefind.repository

import com.uzlov.moviefind.interfaces.Constants
import com.uzlov.moviefind.model.ActorDescription
import com.uzlov.moviefind.model.Credits
import com.uzlov.moviefind.model.Film
import com.uzlov.moviefind.model.PopularFilms
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmApi {

    @GET("3/movie/popular")
    fun getPopularFilms(
        @Query("api_key") apikey:String = Constants.API_KEY,
        @Query("language") language:String = "ru-RU",
    ) : Call<PopularFilms>

    @GET("3/movie/top_rated")
    fun getTopFilms(
        @Query("api_key") apikey:String = Constants.API_KEY,
        @Query("language") language:String = "ru-RU",
    ) : Call<PopularFilms>

    @GET("3/movie/upcoming")
    fun getUpcomingFilms(
        @Query("api_key") apikey:String = Constants.API_KEY,
        @Query("language") language:String = "ru-RU",
    ) : Call<PopularFilms>


    @GET("3/movie/{id}")
    fun getFilmById(
        @Path("id") id: Int,
        @Query("api_key") apikey:String = Constants.API_KEY,
        @Query("language") language:String = "ru-RU",
    ) : Call<Film>

    @GET("3/movie/{movie_id}/credits")
    fun getCreditsMoviesById(
        @Path("movie_id") id: Int,
        @Query("api_key") apikey:String = Constants.API_KEY,
        @Query("language") language:String = "ru-RU",
    ) : Call<Credits>

    @GET("3/search/movie")
    fun searchFilmByName(
        @Query("api_key") apikey:String = Constants.API_KEY,
        @Query("language") language:String = "ru-RU",
        @Query("query") name: String,
        @Query("page") num: Int = 1,
        @Query("include_adult") isAdult: Boolean = true
    ) : Call<PopularFilms>

    @GET("3/person/{person_id}")
    fun getActorDescriptionById(
        @Path("person_id") id: Int,
        @Query("api_key") apikey: String = Constants.API_KEY,
        @Query("language") language: String = "ru-RU"
    ): Call<ActorDescription>
}