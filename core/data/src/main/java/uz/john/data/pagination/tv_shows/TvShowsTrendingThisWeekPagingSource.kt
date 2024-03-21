package uz.john.data.pagination.tv_shows

import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.john.data.remote.api.TvShowsApi
import uz.john.data.remote.model.tv_show.TvShowData
import uz.john.util.ResultModel
import uz.john.util.invokeRequest

class TvShowsTrendingThisWeekPagingSource(
    private val tvShowsApi: TvShowsApi,
    private val language: String
) : PagingSource<Int, TvShowData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowData> {
        val page = params.key ?: 1

        val result = invokeRequest {
            tvShowsApi.getTrendingThisWeekTvShows(
                page = page,
                language = language
            )
        }

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

    override fun getRefreshKey(state: PagingState<Int, TvShowData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}