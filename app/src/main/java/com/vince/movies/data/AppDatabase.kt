package com.vince.movies.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vince.movies.data.entity.Movies

@Database(entities = [Movies::class], version = 1)
abstract  class AppDatabase : RoomDatabase(){
    abstract fun moviesDao() : MoviesDao

}