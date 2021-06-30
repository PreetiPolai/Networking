package com.android.networking.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.android.networking.data.model.Movie

@Dao
interface MovieDetailDao {

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovieById(id: Long) : LiveData<Movie>
}