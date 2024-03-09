package uz.john.data.remote.model.details

data class ImagesResponseData(
    val backdrops: List<ImageData>,
    val logos: List<ImageData>,
    val posters: List<ImageData>
)