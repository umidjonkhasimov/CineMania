package uz.john.data.remote.model.movie

data class AccountStateOfMovieData(
    val id: Int,
    val favorite: Boolean,
    val rated: Boolean,
    val watchlist: Boolean
)