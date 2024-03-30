package uz.john.domain.use_cases.movies

import uz.john.data.repository.MoviesRepository
import uz.john.domain.model.movie.movie_details.MovieDetails
import uz.john.domain.model.movie.movie_details.toDomain
import uz.john.util.ResultModel
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): ResultModel<MovieDetails> {
        val response = moviesRepository.getMovieDetails(movieId)

        when (response) {
            is ResultModel.Error -> {
                return ResultModel.Error(response.error)
            }

            is ResultModel.Exception -> {
                return ResultModel.Exception(response.throwable)
            }

            is ResultModel.Success -> {
                val movieData = response.data.toDomain()

                val accountStateResponse = moviesRepository.getAccountStatesOfMovie(
                    movieId = movieId
                )

                return when (accountStateResponse) {
                    is ResultModel.Error -> {
                        ResultModel.Error(accountStateResponse.error)
                    }

                    is ResultModel.Exception -> {
                        ResultModel.Exception(accountStateResponse.throwable)
                    }

                    is ResultModel.Success -> {
                        ResultModel.Success(
                            movieData.copy(
                                isFavorite = accountStateResponse.data.favorite,
                                isWatchLater = accountStateResponse.data.watchlist
                            )
                        )
                    }
                }
            }
        }
    }
}