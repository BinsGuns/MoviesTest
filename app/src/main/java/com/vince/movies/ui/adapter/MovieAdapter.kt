package com.vince.movies.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView

import com.vince.movies.R


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.vince.movies.data.entity.Movies

class MovieAdapter (private val dataSet : List<Movies>?,private val listener: (Movies) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    lateinit var  imageLoader : ImageLoader

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
         var movieImage : ImageView
         var movieTitle :  TextView
         var movieYear :  TextView
         var movieType :  TextView

        init {
            movieImage =  view.findViewById(R.id.movie_image)
            movieTitle = view.findViewById(R.id.movie_title)
            movieYear = view.findViewById(R.id.movie_year)
            movieType = view.findViewById(R.id.movie_type)

        }

    }



    override fun getItemCount(): Int {
        return dataSet!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =  LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_layout,parent,false)  as  View

        imageLoader =  ImageLoader.Builder(parent.context)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()

       return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text =  dataSet?.get(position)?.title
        holder.movieType.text =  "Type: "+dataSet?.get(position)?.type
        holder.movieYear.text =  "Year: "+dataSet?.get(position)?.year.toString()


        val request = ImageRequest.Builder(holder.itemView.context)
            .data(dataSet?.get(position)?.poster)
            .target(holder.movieImage)
            .build()
        imageLoader.enqueue(request)

        holder.itemView.setOnClickListener { listener(dataSet!![position]) }
    }
}