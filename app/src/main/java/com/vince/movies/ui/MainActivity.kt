package com.vince.movies.ui

import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.Window
import android.widget.*
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


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var swipeRefreshLayout :  SwipeRefreshLayout
    var default_search : String? = "spider"
    lateinit var sharedPreferences : SharedPreferences
    var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getSharedPreferences("MOVIE_SHARED_PREF", 0)
        recyclerView = findViewById(R.id.movie_recyclerview)
        swipeRefreshLayout =  findViewById(R.id.swipe_container)
        viewManager = LinearLayoutManager(this)
        recyclerView.layoutManager = viewManager



        setupViewModel()

        swipeRefreshLayout.setOnRefreshListener {
            fetchData(default_search!!, default_search!!)
            swipeRefreshLayout.isRefreshing =  false
        }

        findViewById<Button>(R.id.reload).setOnClickListener {
            findViewById<LottieAnimationView>(R.id.progress_circular).visibility = View.VISIBLE
            fetchData(default_search!!, default_search!!)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    loadMore()
                }
            }
        })

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))

        }
        return true
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
        fetchData(default_search!!, default_search!!)
    }

    private fun fetchData(s: String, t: String){
        findViewById<LottieAnimationView>(R.id.progress_circular).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.no_result).visibility = View.GONE


        viewModel.getMovies(s, t, "movie", page).observe(this, Observer {
            it?.let { data ->

                findViewById<LottieAnimationView>(R.id.progress_circular).visibility = View.GONE

                if (data.size == 0) {
                    findViewById<LinearLayout>(R.id.no_result).visibility = View.VISIBLE
                } else {

                    viewAdapter = MovieAdapter(data as List<Movies>) { movies ->
                        findViewById<LottieAnimationView>(R.id.progress_circular).visibility = View.VISIBLE
                        getDetails(movies)
                    }
                    recyclerView.adapter = viewAdapter
                    viewAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun getDetails(movies: Movies){
        viewModel.getMovieById(movies.imdbID!!).observe(this, Observer {
            it?.let { data ->
                findViewById<LottieAnimationView>(R.id.progress_circular).visibility = View.GONE
                showDialog(data)
            }
        })
    }

    private fun setupViewModel() {
        page++
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        fetchData(default_search!!, default_search!!)
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
        val btn = dialog.findViewById(R.id.close) as Button
        poster.load(movies.poster)
        title.text = movies.title
        runtime_realase.text = movies.Runtime +" | "+movies.Released
        rated_genre.text =  "Genre: "+movies.Genre
        actors.text = "Actors: "+movies.Actors
        rate_vote.text = "Rating: "+movies.imdbRating+" | "+movies.imdbVotes
        description.text = movies.Plot

        btn.setOnClickListener {
            dialog.dismiss()
        }
//        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }

}