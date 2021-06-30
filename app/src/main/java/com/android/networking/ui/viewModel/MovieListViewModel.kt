package com.android.networking.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.android.networking.data.model.Movie
import com.android.networking.data.network.LoadingStatus
import com.android.networking.data.repository.MovieListRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MovieListViewModel(context : Application) : AndroidViewModel(context) {


    private val movieListRepo: MovieListRepo = MovieListRepo(context)
    private val loadingStatus = MutableLiveData<LoadingStatus>()

    val liveloadingStatus : LiveData<LoadingStatus>
        get() = loadingStatus

    //liveData by default works in other Thread
    val movies : LiveData<List<Movie>> = movieListRepo.getMovies()

    fun fetchFromNetwork() {

        // the status of the network call is loading
        loadingStatus.value = LoadingStatus.loading()

        // function call is done with coroutines
        viewModelScope.launch{
            loadingStatus.value = withContext(Dispatchers.IO) {
                movieListRepo.fetchFromNetwork()
            }!!
        }

    }

    fun refresh() {
        viewModelScope.launch {
            movieListRepo.refresh()
        }

        //fetchFromNetwork()
    }
}