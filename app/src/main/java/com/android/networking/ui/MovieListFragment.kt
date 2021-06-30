package com.android.networking.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.android.networking.data.network.ErrorCode
import com.android.networking.data.network.Status
import com.android.networking.databinding.FragmentMovieListBinding
import com.android.networking.ui.adapter.MovieListAdapter
import com.android.networking.ui.viewModel.MovieListViewModel


class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MovieListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //we write the functionality of the function type in lambda
        val gridLayoutManager = GridLayoutManager(context,2)

        binding.movieListRecyclerView.apply {

            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            adapter = MovieListAdapter(context){

                findNavController().navigate(
                MovieListFragmentDirections.actionMovieListFragment2ToMovieDetailFragment2(it)
                )

            }

        }

        viewModel.movies.observe(viewLifecycleOwner, Observer {


                (binding.movieListRecyclerView.adapter as MovieListAdapter).submitList(it)


            if (it.isEmpty())
                viewModel.fetchFromNetwork()


        })


        //observing the loading status
        viewModel.liveloadingStatus.observe(viewLifecycleOwner, Observer {

            when{

                it.status == Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.error.visibility = View.INVISIBLE
                }

                it.status == Status.SUCCESS -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.error.visibility = View.INVISIBLE
                }

                it.status == Status.ERROR -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.error.visibility = View.VISIBLE
                    showErrorMessage(it.errorCode,it.msg)
                }
            }

            binding.swipeRefresh.isRefreshing = false

        })


        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }



    }

    private fun showErrorMessage(errorCode: ErrorCode?, msg: String?) {

        when(errorCode){

            ErrorCode.UNKNOWN_ERROR -> binding.error.text = " UNKNOWN ERROR,$msg"
            ErrorCode.NETWORK_ERROR -> binding.error.text = " NO NETWORK, PLEASE CHECK NETWORK"
            ErrorCode.NO_DATA -> binding.error.text = " NO DATA RECEIVED FROM THE SERVER"

        }
    }

}