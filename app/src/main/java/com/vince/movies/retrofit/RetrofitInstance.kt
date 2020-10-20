package com.vince.movies.retrofit

import android.content.Context
import com.google.gson.GsonBuilder
import com.vince.movies.networking.ConnectivityInterceptorImpl
import com.vince.movies.service.MovieService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance{

    companion object{
        private val base_url = "http://www.omdbapi.com/";
        private  val api_key = "5ca1a8e5";

        //creating a Network Interceptor to add api_key in all the request as authInterceptor


        private val interceptor = Interceptor { chain ->
            val url = chain.request().url.newBuilder().addQueryParameter("apikey", api_key).build()
            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }

        // we are creating a networking client using OkHttp and add our authInterceptor.
        private val apiClient = OkHttpClient()
            .newBuilder()
            .addInterceptor(interceptor)
            .build()

        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder().client(apiClient)
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val moviesApi: MovieService = getRetrofit().create(MovieService::class.java)

    }




}