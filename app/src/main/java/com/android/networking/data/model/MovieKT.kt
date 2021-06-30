package com.android.networking.data.model

import com.google.gson.annotations.SerializedName

data class MovieKT (


        @SerializedName("backdrop_path") var backdropPath : String,
        @SerializedName("id") var id : Int,
        @SerializedName("overview") var overview : String,
        @SerializedName("poster_path") var posterPath : String,
        @SerializedName("release_date") var releaseDate : String,
        @SerializedName("title") var title : String,

)
