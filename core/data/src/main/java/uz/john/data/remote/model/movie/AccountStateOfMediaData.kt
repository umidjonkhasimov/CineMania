package uz.john.data.remote.model.movie

data class AccountStateOfMediaData(
    val id: Int,
    val favorite: Boolean,
    val rated: Boolean,
    val watchlist: Boolean
)