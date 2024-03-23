package uz.john.domain.use_cases.person.pagination

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.john.data.pagination.people.PopularPeoplePagingSource
import uz.john.data.repository.PersonRepository
import uz.john.domain.model.person.Person
import uz.john.domain.model.person.toDomain
import javax.inject.Inject

class GetPaginatedPopularPeopleUseCase @Inject constructor(
    private val personRepository: PersonRepository
) {
    operator fun invoke(): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                PopularPeoplePagingSource(personRepository)
            }
        ).flow.map { pagingData ->
            pagingData.map {
                it.toDomain()
            }
        }
    }
}