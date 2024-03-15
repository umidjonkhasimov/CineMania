package uz.john.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import uz.john.data.remote.WITH_GENRES
import uz.john.data.remote.api.MoviesApi
import uz.john.data.remote.model.movie.MovieData
import uz.john.util.ResultModel
import uz.john.util.invokeRequest

class MoviesByGenrePagingSource(
    private val moviesApi: MoviesApi,
    private val language: String,
    private val region: String,
    private val genreId: Int,
) : PagingSource<Int, MovieData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieData> {
        val page = params.key ?: 1
        val queryParams = mutableMapOf<String, String>()
        queryParams[WITH_GENRES] = genreId.toString()

        val response = invokeRequest {
            moviesApi.discoverMovies(
                page = page,
                language = language,
                region = region,
                queryParams = queryParams
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
                    nextKey = if (response.data.results.isEmpty()) null else page.plus(1),
                )
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}