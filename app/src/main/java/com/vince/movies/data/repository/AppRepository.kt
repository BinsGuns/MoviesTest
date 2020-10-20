package com.vince.movies.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.vince.movies.data.AppDatabase
import com.vince.movies.data.entity.Movies
import com.vince.movies.data.entity.Result
import com.vince.movies.retrofit.RetrofitInstance
import com.vince.movies.service.MovieService
import retrofit2.Response

//val RepositoryModule = module {
//    factory { AppRepository() }
//}

open class AppRepository(context : Context) {
    private var service: MovieService = RetrofitInstance.moviesApi

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "movies-db"
    ).build()

    val sharedPreferences : SharedPreferences = context.getSharedPreferences("MOVIE_SHARED_PREF",0)

    fun saveTotalResult(total :  Int){
        sharedPreferences.edit().putInt("total_result",total).apply()
    }

    suspend fun getListMovie(s: String, t: String, type: String, page: Int): List<Movies> {
        val request = service.getListMovie(s,t,type,page)

        val total_page = request.body()?.totalResults!!
        saveTotalResult(total_page)

        request.body()?.search?.forEach { it ->
            var result = it.imdbID?.filter {
                it.isDigit()
            }
            it.id =  result?.toLong()
            db.moviesDao().insert(it)
        }

        return db.moviesDao().getAllMovies(t);

    }

    suspend fun getMovieById(s: String): Movies {
        val request = service.getMovieById(s)


        var result =  request.body()?.imdbID?.filter {
            it.isDigit()
        }
        request.body()?.id = result?.toLong()
        db.moviesDao().insert(request.body())

        return db.moviesDao().getMoviesById(s);

    }


}
