package com.vince.movies.ui

import android.app.Dialog
import android.app.SearchManager
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import com.airbnb.lottie.LottieAnimationView
import com.vince.movies.R
import com.vince.movies.data.entity.Movies
import com.vince.movies.ui.adapter.MovieAdapter

class SearchableResultsActivity : AppCompatActivity(){
    private lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var sharedPreferences : SharedPreferences
    lateinit var swipeRefreshLayout :  SwipeRefreshLayout
    var page = 0
    var query : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("MOVIE_SHARED_PREF", 0)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewManager = LinearLayoutManager(this)
        swipeRefreshLayout =  findViewById(R.id.swipe_container)
        recyclerView = findViewById(R.id.movie_recyclerview)
        recyclerView.layoutManager = viewManager
        handleIntent(intent)

        swipeRefreshLayout.setOnRefreshListener {
            fetchData(query!!, query!!)
            swipeRefreshLayout.isRefreshing =  false
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                    loadMore()
                }
            }
        })


    }

    private fun  loadMore(){
        var saved_total_request =  sharedPreferences.getInt("total_result", 1)
        var total_pages = (saved_total_request.div(10))


        if(total_pages > 0 ) {
            val  modulus  =  (saved_total_request % 10)
            if(modulus == 0 && page != total_pages) { //
                page++
                sharedPreferences.edit().putInt("page", page).apply()
            } else {
                total_pages += 1
                if(total_pages != page) {
                    page++
                    sharedPreferences.edit().putInt("page", page).apply()
                }
            }
        } else {
            sharedPreferences.edit().putInt("page", 1)
            page = sharedPreferences.getInt("page", 1)
        }
        fetchData(query!!, query!!)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        handleIntent(intent)

    }

    private fun handleIntent(intent: Intent) {

        if (Intent.ACTION_SEARCH == intent.action) {
            query = intent.getStringExtra(SearchManager.QUERY)
            page++
            fetchData(query!!,query!!)
            //use the query to search your data somehow
        }
    }

    private fun getDetails(movies :  Movies){
        viewModel.getMovieById(movies.imdbID!!).observe(this, Observer {
            it?.let { data ->

                findViewById<LottieAnimationView>(R.id.progress_circular).visibility = View.GONE
                showDialog(data)
            }
        })
    }

    private fun showDialog(movies: Movies) {
        val dialog = Dialog(this)
        val window: Window = dialog.window!!
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        // dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_details)
        val poster = dialog.findViewById(R.id.poster) as ImageView
        val title = dialog.findViewById(R.id.title) as TextView
        val runtime_realase = dialog.findViewById(R.id.runtime_release) as TextView
        val rated_genre = dialog.findViewById(R.id.rated_genre) as TextView
        val actors = dialog.findViewById(R.id.actors) as TextView
        val rate_vote = dialog.findViewById(R.id.rating_votes) as TextView
        val description = dialog.findViewById(R.id.description) as TextView
        // val noBtn = dialog.findViewById(R.id.noBtn) as TextView
        poster.load(movies.poster)
        title.text = movies.title
        runtime_realase.text = movies.Runtime +" | "+movies.Released
        rated_genre.text =  "Genre: "+movies.Genre
        actors.text = "Actors: "+movies.Actors
        rate_vote.text = "Rating: "+movies.imdbRating+" | "+movies.imdbVotes
        description.text = movies.Plot

        val btn = dialog.findViewById(R.id.close) as Button
        btn.setOnClickListener {
            dialog.dismiss()
        }
//        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

    private fun fetchData(s: String,t:String){
        findViewById<LottieAnimationView>(R.id.progress_circular).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.no_result).visibility = View.GONE

        viewModel.getMovies(s, t, "movie", page).observe(this, Observer {
            it?.let { data ->

                findViewById<LottieAnimationView>(R.id.progress_circular).visibility = View.GONE

                if (data.size == 0) {
                    findViewById<LinearLayout>(R.id.no_result).visibility = View.VISIBLE
                } else {
                    viewAdapter = MovieAdapter(data as List<Movies>){
                        movies ->
                        findViewById<LottieAnimationView>(R.id.progress_circular).visibility = View.VISIBLE
                        getDetails(movies)
                    }
                    recyclerView.adapter = viewAdapter

                    viewAdapter.notifyDataSetChanged()
                }
            }
        })
    }
}