package com.vince.movies.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vince.movies.data.entity.Movies


@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movies where title like '%'||:title||'%' ORDER BY year desc")
    fun getAllMovies(title : String): List<Movies>

    @Query("SELECT * FROM Movies WHERE imdbID = :imdbID")
    fun getMoviesById(imdbID: String): Movies

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: Movies?): Long?

}