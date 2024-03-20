package uz.john.data.remote.model.common

data class ImagesResponseData(
    val backdrops: List<ImageData>,
    val logos: List<ImageData>,
    val posters: List<ImageData>
)