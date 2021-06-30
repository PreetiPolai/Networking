package com.android.networking.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.android.networking.data.database.MovieDatabase
import com.android.networking.data.model.Movie
import com.android.networking.data.network.ErrorCode
import com.android.networking.data.network.LoadingStatus
import java.lang.Exception
import java.net.UnknownHostException

class MovieListRepo(context: Application) {

    private val movieListDao = MovieDatabase.getDatabase(context).getMovieListDao()
    private val repoApiService by lazy { com.android.networking.data.network.apiService.getRetrofitInstance() }

    private suspend fun insertMovies(movieList : List<Movie>){
        movieListDao.insertMovies(movieList)
    }

     fun getMovies() : LiveData<List<Movie>>{
        return movieListDao.getMovies()
    }

    suspend fun fetchFromNetwork() =

            try {

                // We make the network call here
                val result = repoApiService.getMovies()

                if (result.isSuccessful){
                    val tmbdMovieList = result.body()
                    tmbdMovieList?.let {
                        insertMovies(it.results)
                    }

                    LoadingStatus.success()
                }

                else{

                    LoadingStatus.error(ErrorCode.NO_DATA)
                }
        }

            catch ( ex : UnknownHostException){
                LoadingStatus.error(ErrorCode.NETWORK_ERROR)
            }
            catch (ex: Exception){
                LoadingStatus.error(ErrorCode.UNKNOWN_ERROR, ex.message)
            }

    suspend fun refresh(){
        movieListDao.delete()
    }

}