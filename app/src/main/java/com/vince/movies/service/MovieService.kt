package com.vince.movies.service


import com.vince.movies.data.entity.Movies
import com.vince.movies.data.entity.Result
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface MovieService {

    //http://www.omdbapi.com/?s=spider&&t=spider&type=movie&apikey=5ca1a8e5
    @GET(".")
     suspend fun getListMovie(@Query("s") s: String,
                           @Query("t") t : String,
                           @Query("type") type : String,
                           @Query("page") page : Int): Response<Result>

    @GET(".")
    suspend fun getMovieById(@Query("i") s: String): Response<Movies>

//    @GET(".")
//    suspend fun searchMovie(@Query("s") s: String,
//                            @Query("t") t : String,
//                            @Query("type") type : String,
//                            @Query("page") page : Int): Response<Result>
}