package uz.john.data.pagination.people

import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.john.data.remote.model.person.PersonData
import uz.john.data.repository.PersonRepository
import uz.john.util.ResultModel

class PeopleBySearchQueryPagingSource(
    private val personRepository: PersonRepository,
    private val query: String
) : PagingSource<Int, PersonData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PersonData> {
        val page = params.key ?: 1

        val result = personRepository.searchPeople(query = query, page = page)
        return when (result) {
            is ResultModel.Error -> {
                LoadResult.Error(result.error)
            }

            is ResultModel.Exception -> {
                LoadResult.Error(result.throwable)
            }

            is ResultModel.Success -> {
                LoadResult.Page(
                    data = result.data.results,
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = if (result.data.results.isEmpty()) null else page.plus(1)
                )
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PersonData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}