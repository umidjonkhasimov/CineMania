package uz.john.paginated_movies_list.all_people_screen

import android.content.Context
import kotlinx.serialization.Serializable
import uz.john.paginated_movies_list.R

@Serializable
sealed interface AllPeopleScreenParam {
    fun getTitle(context: Context): String

    @Serializable
    data object AllPopularPeople : AllPeopleScreenParam {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.popular_people)
        }
    }

    @Serializable
    data class AllPeopleBySearchQuery(val query: String) : AllPeopleScreenParam {
        override fun getTitle(context: Context): String {
            return context.getString(R.string.search_for, query)
        }
    }
}