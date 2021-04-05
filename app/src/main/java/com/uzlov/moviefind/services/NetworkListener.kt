package com.uzlov.moviefind.services

interface NetworkListener {
    fun networkStateChanged(isAvailable:Boolean)
}