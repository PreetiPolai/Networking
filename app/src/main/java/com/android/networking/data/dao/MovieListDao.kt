package com.android.networking.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.android.networking.data.model.Movie

@Dao
interface MovieListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovies(movieList : List<Movie>)

    @Query("SELECT * FROM movie ORDER BY release_date DESC")
     fun getMovies() : LiveData<List<Movie>>

     @Query("DELETE FROM movie")
     suspend fun delete()
}