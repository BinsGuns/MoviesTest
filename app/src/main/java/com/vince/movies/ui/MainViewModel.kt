package com.vince.movies.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.vince.movies.data.entity.Movies
import com.vince.movies.data.entity.Result

import com.vince.movies.data.repository.AppRepository
import com.vince.movies.networking.Resource
import com.vince.movies.networking.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class MainViewModel(application :  Application): AndroidViewModel(application) {

    private val appRepository = AppRepository(application)


    fun getMovies(s : String, t: String , type : String , page : Int) = liveData(Dispatchers.IO) {

        try{
            val  result =  Resource.success(data = appRepository.getListMovie(s,t,type,page))
            emit(result.data)
        }catch ( e : Exception){
            // load local data cache from room
            emit(appRepository.db.moviesDao().getAllMovies(t))
        }
    }

    fun getMovieById(i:String) = liveData(Dispatchers.IO) {

        try{
            val  result =  Resource.success(data = appRepository.getMovieById(i))
            emit(result.data)
        }catch ( e : Exception){
            // load local data cache from room
            emit(appRepository.db.moviesDao().getMoviesById(i))
        }
    }

}