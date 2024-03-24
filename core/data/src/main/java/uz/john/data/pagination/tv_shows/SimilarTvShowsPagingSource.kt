package uz.john.data.pagination.tv_shows

import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.john.data.remote.model.tv_show.TvShowData
import uz.john.data.repository.TvShowsRepository
import uz.john.util.ResultModel

class SimilarTvShowsPagingSource(
    private val tvShowsRepository: TvShowsRepository,
    private val seriesId: Int,
) : PagingSource<Int, TvShowData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowData> {
        val page = params.key ?: 1

        val response = tvShowsRepository.getSimilarTvShows(
            seriesId = seriesId,
            page = page
        )

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