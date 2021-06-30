package com.android.networking.data.model

import com.google.gson.annotations.SerializedName

data class Example (

//        @SerializedName("page") var page : Int,
        @SerializedName("results") var results : List<MovieKT>,
//        @SerializedName("total_pages") var totalPages : Int,
//        @SerializedName("total_results") var totalResults : Int

)
