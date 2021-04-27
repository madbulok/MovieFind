package com.uzlov.moviefind.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uzlov.moviefind.database.FilmEntityDB
import com.uzlov.moviefind.model.ActorDescription
import com.uzlov.moviefind.model.Credits
import com.uzlov.moviefind.model.PopularFilms
import com.uzlov.moviefind.repository.RepositoryLocalData

import com.uzlov.moviefind.repository.RepositoryRemoteImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsViewModel : ViewModel() {

    private fun builderCallback(livedata: MutableLiveData<AppStateFilms>) : Callback<PopularFilms>{
        return object : Callback<PopularFilms> {
            override fun onResponse(call: Call<PopularFilms>, response: Response<PopularFilms>) {
                if (response.isSuccessful){
                    livedata.value = AppStateFilms.Success(filmsData = response.body()!!)
                } else {
                    livedata.value = AppStateFilms.Error(error = Throwable("Ошибка сервера ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<PopularFilms>, t: Throwable) {
                livedata.value = AppStateFilms.Error(error = Throwable("Ошибка сервера ${t.message}"))
            }
        }
    }

    fun getUpcomingFilms() : LiveData<AppStateFilms>{
        val liveData: MutableLiveData<AppStateFilms> = MutableLiveData()
        RepositoryRemoteImpl.loadUpcoming(builderCallback(liveData))
        return liveData
    }

    fun getTopRatedFilms() : LiveData<AppStateFilms> {
        val liveData: MutableLiveData<AppStateFilms> = MutableLiveData()
        RepositoryRemoteImpl.loadTopRatedFilms(builderCallback(liveData))
        return liveData
    }

    fun getPopularFilms() : LiveData<AppStateFilms>{
        val liveData: MutableLiveData<AppStateFilms> = MutableLiveData()
        RepositoryRemoteImpl.loadPopular(builderCallback(liveData))
        return liveData
    }

    fun getFilmsByName(name : String, isAdult: Boolean = false) : LiveData<AppStateFilms>{
        val liveData: MutableLiveData<AppStateFilms> = MutableLiveData()
        RepositoryRemoteImpl.searchFilmsByName(name, isAdult, builderCallback(liveData))
        return liveData
    }

    fun getMyFavoritesFilms() : LiveData<List<FilmEntityDB>> {
        return RepositoryLocalData.loadFavoriteFilms()
    }

    fun getFilmById(id: Int) : LiveData<AppStateFilm> {
        return RepositoryRemoteImpl.loadFilmById(id)
    }

    fun getCreditFilmByID(id: Int) : LiveData<Credits> {
        return RepositoryRemoteImpl.getCreditsMoviesById(id)
    }

    fun addFilmToFavorite(film : FilmEntityDB){
        RepositoryLocalData.addFavoriteFilm(film)
    }

    fun filmIsFavorite(id: Int) : LiveData<Int> {
        return RepositoryLocalData.checkFilmIsFavorite(id)
    }

    fun removeFilmFromSavedFavorite(id: Long){
        RepositoryLocalData.removeFilmFromFavorite(id)
    }

    fun getActorDescriptionsById(id: Int): MutableLiveData<ActorDescription> {
        return RepositoryRemoteImpl.getActorDescriptionById(id)
    }
}