package com.uzlov.moviefind.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.uzlov.moviefind.interfaces.Constants
import com.uzlov.moviefind.interfaces.Loadable
import com.uzlov.moviefind.model.Film
import com.uzlov.moviefind.model.PopularFilms
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

object RepositoryPopularImpl : Loadable {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun loadPopular() : MutableLiveData<PopularFilms>{
        val uriPopularFilms = URL("https://api.themoviedb.org/3/movie/popular?api_key=${Constants.API_KEY}&language=ru-RU")
        val responseLiveData: MutableLiveData<PopularFilms> = MutableLiveData()

        try {
            Thread {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = (uriPopularFilms.openConnection() as HttpsURLConnection).apply {
                        requestMethod = "GET"
                        readTimeout = 10_000
                    }
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))

                    val popularFilms =
                        Gson().fromJson(bufferedReader.getLines(), PopularFilms::class.java)
                    responseLiveData.postValue(popularFilms)

                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException){
            e.printStackTrace()
        }
        return responseLiveData
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun loadFilmById(id: Int): MutableLiveData<Film> {
        val uriDetailFilm = URL("https://api.themoviedb.org/3/movie/$id?api_key=${Constants.API_KEY}&language=ru-RU")
        val responseLiveData: MutableLiveData<Film> = MutableLiveData()

        try {
            Thread {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = (uriDetailFilm.openConnection() as HttpsURLConnection).apply {
                        requestMethod = "GET"
                        readTimeout = 10_000
                    }
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))

                    val film =
                        Gson().fromJson(bufferedReader.getLines(), Film::class.java)
                    responseLiveData.postValue(film)

                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    urlConnection.disconnect()
                }
            }.start()
        } catch (e: MalformedURLException){
            e.printStackTrace()
        }
        return responseLiveData
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun BufferedReader.getLines() : String {
        return lines().collect(Collectors.joining("\n"))
    }
}