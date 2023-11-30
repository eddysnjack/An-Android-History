package com.leylapps.anandroidhistory.ui.mainpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.leylapps.anandroidhistory.R

class MovieListAC : AppCompatActivity() {
    lateinit var recMovieList: RecyclerView;
    lateinit var searchViewMovie: SearchView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recMovieList = findViewById(R.id.recListMovies);
        searchViewMovie = findViewById(R.id.svMovie)
        // fetch movie list
    }
}