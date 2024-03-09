package uz.john.data.remote

// Authentication
internal const val AUTHORIZATION = "Authorization"
internal const val BEARER = "Bearer"
internal const val REQUEST_TOKEN_ENDPOINT = "authentication/token/new"
internal const val VALIDATE_REQUEST_TOKEN_ENDPOINT = "authentication/token/validate_with_login"
internal const val CREATE_SESSION_ENDPOINT = "authentication/session/new"

// Movies
internal const val NOW_PLAYING_ENDPOINT = "movie/now_playing"
internal const val POPULAR_MOVIES_ENDPOINT = "movie/popular"
internal const val TOP_RATED_MOVIES_ENDPOINT = "movie/top_rated"
internal const val MOVIE_DETAILS_ENDPOINT = "movie/{movie_id}"
internal const val SIMILAR_MOVIES_ENDPOINT = "movie/{movie_id}/similar"
internal const val APPEND_IMAGES = "images"
internal const val APPEND_CREDITS = "credits"
internal const val APPEND_VIDEOS = "videos"

// Query Params
internal const val LANGUAGE = "language"
internal const val PAGE = "page"
internal const val REGION = "region"
internal const val ADD_TO_RESPONSE = "append_to_response"

// Path
internal const val MOVIE_ID = "movie_id"