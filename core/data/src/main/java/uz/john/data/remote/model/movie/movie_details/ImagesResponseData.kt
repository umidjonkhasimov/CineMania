package uz.john.data.remote.model.movie.movie_details

data class ImagesResponseData(
    val backdrops: List<ImageData>,
    val logos: List<ImageData>,
    val posters: List<ImageData>
)