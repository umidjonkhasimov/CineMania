package uz.john.data.remote.model.person

import com.google.gson.annotations.SerializedName

data class PeopleResponseData(
    val page: Int,
    val results: List<PersonData>,
    @SerializedName(value = "total_pages")
    val totalPages: Int,
    @SerializedName(value = "total_results")
    val totalResults: Int
)