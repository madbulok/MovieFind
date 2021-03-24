package com.uzlov.moviefind.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uzlov.moviefind.model.TestFilm
import java.lang.Thread.sleep

class FilmsViewModel(private val liveDataToObserve: MutableLiveData<List<TestFilm>> = MutableLiveData()) : ViewModel() {


    fun getPopularFilms() : LiveData<List<TestFilm>>{
        getLocalData()
        return liveDataToObserve
    }

    private fun getLocalData(){
        Thread {
            sleep(2000)
            liveDataToObserve.postValue(listOf(
                    TestFilm("Бойцовский клуб","Боевик"),
                    TestFilm("Пила 2","Ужасы"),
                    TestFilm("Большой куш","Боевик"),
                    TestFilm("1+1","Комедия, драма"),
                    TestFilm("Терминатор","Боевик")
            ))
        }.start()

    }
}