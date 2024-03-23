package uz.john.paginated_movies_list.all_people_screen

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.json.Json.Default.decodeFromString
import uz.john.domain.model.person.Person
import uz.john.domain.use_cases.person.pagination.GetPaginatedPeopleBySearchQueryUseCase
import uz.john.domain.use_cases.person.pagination.GetPaginatedPopularPeopleUseCase
import uz.john.paginated_movies_list.all_people_screen.AllPeopleScreenContract.SideEffect
import uz.john.paginated_movies_list.all_people_screen.AllPeopleScreenContract.UiAction
import uz.john.paginated_movies_list.all_people_screen.AllPeopleScreenContract.UiState
import uz.john.ui.delegations.MVI
import uz.john.ui.delegations.mvi
import javax.inject.Inject

@HiltViewModel
class AllPeopleViewModel @Inject constructor(
    getPaginatedPopularPeopleUseCase: GetPaginatedPopularPeopleUseCase,
    getPaginatedPeopleBySearchQueryUseCase: GetPaginatedPeopleBySearchQueryUseCase,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext context: Context
) : ViewModel(), MVI<UiState, UiAction, SideEffect> by mvi(UiState()) {
    private val json = savedStateHandle.get<String>(ALL_PEOPLE_ARG)

    private val allPeopleScreenParam = json?.let { decodeFromString<AllPeopleScreenParam>(it) }

    init {
        updateUiState {
            copy(title = allPeopleScreenParam?.getTitle(context) ?: "")
        }
    }

    val paginatedItems: Flow<PagingData<Person>> = when (allPeopleScreenParam) {
        is AllPeopleScreenParam.AllPeopleBySearchQuery -> {
            getPaginatedPeopleBySearchQueryUseCase(allPeopleScreenParam.query).cachedIn(viewModelScope)
        }

        AllPeopleScreenParam.AllPopularPeople -> {
            getPaginatedPopularPeopleUseCase().cachedIn(viewModelScope)
        }

        null -> {
            flowOf()
        }
    }
}