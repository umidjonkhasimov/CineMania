package uz.john.data.pagination.tv_shows

import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.john.data.remote.api.SearchApi
import uz.john.data.remote.model.tv_show.TvShowData
import uz.john.util.ResultModel
import uz.john.util.invokeRequest

class TvShowsBySearchQueryPagingSource(
    private val searchApi: SearchApi,
    private val query: String,
    private val includeAdult: Boolean,
    private val language: String,
) : PagingSource<Int, TvShowData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowData> {
        val page = params.key ?: 1

        val response = invokeRequest {
            searchApi.searchTvShows(
                query = query,
                language = language,
                includeAdult = includeAdult,
                page = page,
                additionalParams = mapOf()
            )
        }

        return when (response) {
            is ResultModel.Error -> {
                LoadResult.Error(response.error)
            }

            is ResultModel.Exception -> {
                LoadResult.Error(response.throwable)
            }

            is ResultModel.Success -> {
                LoadResult.Page(
                    data = response.data.results,
                    prevKey = if (page == 1) null else page.minus(1),
                    nextKey = if (response.data.results.isEmpty()) null else page.plus(1)
                )
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TvShowData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}