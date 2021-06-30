package com.android.networking.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.android.networking.data.model.Movie
import com.android.networking.data.repository.MovieDetailRepo
import com.android.networking.data.repository.MovieListRepo

class MovieDetailViewModel(id:Long, application: Application) :AndroidViewModel(application) {
    private val movieDetailRepo: MovieDetailRepo = MovieDetailRepo(application)

    //liveData by default works in other Thread
    val liveMovie : LiveData<Movie> = movieDetailRepo.getMovieDetail(id)
}