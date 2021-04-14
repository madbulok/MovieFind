package com.uzlov.moviefind.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FilmEntityDB::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun filmsDao() : FilmDAO
}