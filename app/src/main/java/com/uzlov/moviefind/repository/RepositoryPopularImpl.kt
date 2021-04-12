package com.uzlov.moviefind.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.uzlov.moviefind.interfaces.Constants
import com.uzlov.moviefind.interfaces.Loadable
import com.uzlov.moviefind.model.Film
import com.uzlov.moviefind.model.PopularFilms
import com.uzlov.moviefind.viewmodels.AppStateFilms
import com.uzlov.moviefind.viewmodels.AppStateFilm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryPopularImpl : Loadable {

    private val filmsApi = Retrofit
        .Builder()
        .baseUrl(Constants.API_URL)
        .addConverterFactory(GsonConverterFactory.create(
            GsonBuilder().setLenient().create()
        )
        )
        .build().create(FilmApi::class.java)

    override fun loadPopular() : MutableLiveData<AppStateFilms> {
        val responseLiveData: MutableLiveData<AppStateFilms> = MutableLiveData()

        filmsApi.getPopularFilms().enqueue(object : Callback<PopularFilms> {
            override fun onResponse(call: Call<PopularFilms>, response: Response<PopularFilms>) {
                if (response.isSuccessful){
                    responseLiveData.postValue(AppStateFilms.Success(filmsData = response.body()!!))
                }else {
                    responseLiveData.value = AppStateFilms.Error(error = Throwable("Ошибка сервера ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<PopularFilms>, t: Throwable) {
                responseLiveData.value = AppStateFilms.Error(error = Throwable("Ошибка сервера ${t.message}"))
            }
        })
        return responseLiveData
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

    override fun loadTopRatedFilms(): MutableLiveData<AppStateFilms> {
        val responseLiveData: MutableLiveData<AppStateFilms> = MutableLiveData()

        filmsApi.getTopFilms().enqueue(object : Callback<PopularFilms> {
            override fun onResponse(call: Call<PopularFilms>, response: Response<PopularFilms>) {
                if (response.isSuccessful){
                    responseLiveData.postValue(AppStateFilms.Success(filmsData = response.body()!!))
                } else {
                    responseLiveData.value = AppStateFilms.Error(error = Throwable("Ошибка сервера ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<PopularFilms>, t: Throwable) {
                responseLiveData.value = AppStateFilms.Error(error = Throwable("Ошибка сервера ${t.message}"))
            }
        })
        return responseLiveData
    }
}