package uz.john.data.remote.model.home

import com.google.gson.annotations.SerializedName

data class MoviesResponseData(
    val page: Int,
    val results: List<MovieData>,
    @SerializedName(value = "total_pages")
    val totalPages: Int,
    @SerializedName(value = "total_results")
    val totalResults: Int
)