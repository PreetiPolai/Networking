package com.android.networking.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.networking.R
import com.android.networking.data.model.Movie
import com.android.networking.data.network.apiService
import com.android.networking.databinding.MovieListItemBinding
import com.bumptech.glide.Glide


class MovieListAdapter( val context: Context, private val listener :(Long) -> Unit) : ListAdapter<Movie, MovieListAdapter.ViewHolder>(DiffCallBack()) {

    inner class ViewHolder(val binding: MovieListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {

                //pass the selected index to the function type
                listener.invoke(getItem(adapterPosition).id)
            }
        }

        fun bind(m : Movie){

              this.binding.movieNameListItem.text = m.title

                Glide
                    .with(context)
                    .load(apiService.POSTER_BASE_URL + m.posterPath)
                        .error(R.drawable.ic_launcher_background)
                    .into(this.binding.moviePosterListItem);
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : MovieListItemBinding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //passing the movie of the specified position
        holder.bind(getItem(position))

    }
}


//this is an alternate way for notifyDataSetChange().
//This compares the old and the new object
class DiffCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {

        //the data class object can be simply compared by ==
        return oldItem == newItem
    }

}
