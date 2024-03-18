package uz.john.data.remote.model.tv_show

import com.google.gson.annotations.SerializedName

data class TvShowsResponseData(
    val page: Int,
    val results: List<TvShowData>,
    @SerializedName(value = "total_pages")
    val totalPages: Int,
    @SerializedName(value = "total_results")
    val totalResults: Int
)