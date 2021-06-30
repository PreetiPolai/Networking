package com.android.networking.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.android.networking.R
import com.android.networking.data.model.Movie
import com.android.networking.data.network.apiService
import com.android.networking.databinding.FragmentMovieDetailBinding
import com.android.networking.ui.viewModel.MovieDetailViewModel
import com.android.networking.ui.viewModel.MovieDetailViewModelFactory
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*


class MovieDetailFragment : Fragment() {

    private  lateinit var binding: FragmentMovieDetailBinding
    private lateinit var viewModel : MovieDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id : Long? = arguments?.let { MovieDetailFragmentArgs.fromBundle(it).id }

        val movieDetailViewModelFactory = id?.let { MovieDetailViewModelFactory(it,requireActivity().application) }
        viewModel = ViewModelProviders.of(this,movieDetailViewModelFactory)
            .get(MovieDetailViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.liveMovie.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            setData(it)
        })
    }


    private fun setData(movie : Movie){
        binding.movieTitle.text = movie.title
        binding.movieReleaseDate.text = movie.releaseDate.readableFormat()
        binding.MovieOverview.text = movie.overview

        context?.let {
            Glide
                .with(it)
                .load(apiService.POSTER_BASE_URL + movie.posterPath)
                .error(R.drawable.ic_launcher_background)
                .into(binding.moviePoster)
        }

        context?.let {
            Glide
                    .with(it)
                    .load(apiService.BACKDROP_BASE_URL + movie.backdropPath)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.backDropPoster)
        }

    }

}


//using extension function on class Date so as to change the format of date
private fun Date.readableFormat(): CharSequence? {

    val df = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
    return df.format(this)
}
