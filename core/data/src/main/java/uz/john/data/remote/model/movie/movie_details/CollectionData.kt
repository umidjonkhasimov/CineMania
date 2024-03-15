package uz.john.data.remote.model.movie.movie_details

import com.google.gson.annotations.SerializedName

data class CollectionData(
    @SerializedName(value = "backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val name: String,
    @SerializedName(value = "poster_path")
    val posterPath: String?
)