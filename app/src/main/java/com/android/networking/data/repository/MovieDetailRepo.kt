package com.android.networking.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.android.networking.data.dao.MovieDetailDao
import com.android.networking.data.database.MovieDatabase
import com.android.networking.data.model.Movie

class MovieDetailRepo(context: Application) {

    private val movieDetailDao : MovieDetailDao = MovieDatabase.getDatabase(context).getMovieDetailDao()

    fun getMovieDetail(id : Long) : LiveData<Movie> {
        return movieDetailDao.getMovieById(id)
    }
}