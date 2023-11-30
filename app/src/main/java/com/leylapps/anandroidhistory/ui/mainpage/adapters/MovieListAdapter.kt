package com.leylapps.anandroidhistory.ui.mainpage.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.leylapps.anandroidhistory.R
import com.leylapps.anandroidhistory.ui.mainpage.models.MovieListItem

class MovieListAdapter(private val movies: ArrayList<MovieListItem>) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_movie_list, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var acivMoviePoster: AppCompatImageView
        lateinit var tvMovieName: TextView
        lateinit var tvMovieRating: TextView
        lateinit var tvMovieYear: TextView

        init {
            acivMoviePoster = itemView.findViewById(R.id.acivMoviePoster)
            tvMovieName = itemView.findViewById(R.id.tvMovieName)
            tvMovieRating = itemView.findViewById(R.id.tvMovieRating)
            tvMovieYear = itemView.findViewById(R.id.tvMovieYear)
        }
    }


    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (movies.isEmpty()) {
            return
        }

        val currentItem = movies[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(holder.acivMoviePoster)
        holder.tvMovieName.text = currentItem.movieName
        holder.tvMovieYear.text = currentItem.movieYear.toString()
        holder.tvMovieRating.text = currentItem.movieRating
    }
}