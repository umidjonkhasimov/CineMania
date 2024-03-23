package uz.john.domain.use_cases.person.pagination

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.john.data.pagination.people.PeopleBySearchQueryPagingSource
import uz.john.data.repository.SearchRepository
import uz.john.domain.model.person.Person
import uz.john.domain.model.person.toDomain
import javax.inject.Inject

class GetPaginatedPeopleBySearchQueryUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    operator fun invoke(query: String): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                PeopleBySearchQueryPagingSource(searchRepository = searchRepository, query = query)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDomain()
            }
        }
    }
}