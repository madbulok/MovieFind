package com.uzlov.moviefind.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.uzlov.moviefind.interfaces.Constants
import com.uzlov.moviefind.interfaces.Loadable
import com.uzlov.moviefind.model.ActorDescription
import com.uzlov.moviefind.model.Credits
import com.uzlov.moviefind.model.Film
import com.uzlov.moviefind.model.PopularFilms
import com.uzlov.moviefind.viewmodels.AppStateFilm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryRemoteImpl : Loadable {

    private val filmsApi = Retrofit
        .Builder()
        .baseUrl(Constants.API_URL)
        .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(FilmApi::class.java)

    override fun loadPopular(callback: Callback<PopularFilms>) {
        filmsApi.getPopularFilms().enqueue(callback)
    }

    fun loadUpcoming(callback: Callback<PopularFilms>) {
        filmsApi.getUpcomingFilms().enqueue(callback)
    }

    override fun loadTopRatedFilms(call: Callback<PopularFilms>){
        filmsApi.getTopFilms().enqueue(call)
    }

    override fun loadFilmById(id: Int): MutableLiveData<AppStateFilm> {
        val responseLiveData: MutableLiveData<AppStateFilm> = MutableLiveData()

        filmsApi.getFilmById(id).enqueue(object : Callback<Film> {
            override fun onResponse(call: Call<Film>, response: Response<Film>) {
                if (response.isSuccessful){
                    responseLiveData.postValue(AppStateFilm.Success(filmsData = response.body()!!))
                } else {
                    responseLiveData.value = AppStateFilm.Error(error = Throwable("Ошибка сервера ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Film>, t: Throwable) {
                responseLiveData.value = AppStateFilm.Error(error = Throwable("Ошибка сервера ${t.message}"))
            }
        })
        return responseLiveData
    }

    fun searchFilmsByName(
        name: String,
        isAdult: Boolean = false,
        callback: Callback<PopularFilms>
    ){
        filmsApi.searchFilmByName(name = name, isAdult = isAdult).enqueue(callback)
    }

    fun getCreditsMoviesById(id: Int): MutableLiveData<Credits> {
        val responseLiveData: MutableLiveData<Credits> = MutableLiveData()

        filmsApi.getCreditsMoviesById(id).enqueue(object : Callback<Credits>{
            override fun onResponse(call: Call<Credits>, response: Response<Credits>) {
                if (response.isSuccessful){
                    responseLiveData.postValue(response.body()!!)
                }
            }

            override fun onFailure(call: Call<Credits>, t: Throwable) {
            }
        })
        return responseLiveData
    }

    fun getActorDescriptionById(id: Int): MutableLiveData<ActorDescription> {
        val responseLiveData: MutableLiveData<ActorDescription> = MutableLiveData()

        filmsApi.getActorDescriptionById(id).enqueue(object : Callback<ActorDescription>{
            override fun onResponse(call: Call<ActorDescription>, response: Response<ActorDescription>) {
                if (response.isSuccessful){
                    responseLiveData.postValue(response.body()!!)
                }
            }

            override fun onFailure(call: Call<ActorDescription>, t: Throwable) {
            }
        })
        return responseLiveData
    }
}