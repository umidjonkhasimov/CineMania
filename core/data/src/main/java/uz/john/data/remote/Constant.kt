package uz.john.data.remote

// Authentication
internal const val AUTHORIZATION = "Authorization"
internal const val BEARER = "Bearer"
internal const val REQUEST_TOKEN_ENDPOINT = "authentication/token/new"
internal const val VALIDATE_REQUEST_TOKEN_ENDPOINT = "authentication/token/validate_with_login"
internal const val CREATE_SESSION_ENDPOINT = "authentication/session/new"

// Movies
internal const val TRENDING_ENDPOINT = "trending/movie/{time_window}"
internal const val TOP_RATED_MOVIES_ENDPOINT = "movie/top_rated"
internal const val MOVIE_DETAILS_ENDPOINT = "movie/{movie_id}"
internal const val SIMILAR_MOVIES_ENDPOINT = "movie/{movie_id}/similar"
internal const val RECOMMENDED_MOVIES_ENDPOINT = "movie/{movie_id}/recommendations"
internal const val DISCOVER_MOVIES = "discover/movie"
internal const val APPEND_IMAGES = "images"
internal const val APPEND_CREDITS = "credits"
internal const val APPEND_VIDEOS = "videos"
internal const val ALL_GENRES_ENDPOINT = "genre/movie/list"
internal const val TRENDING_TIME_WINDOW_DAY = "day"
internal const val TRENDING_TIME_WINDOW_WEEK = "week"

// Person
internal const val PERSON_DETAILS_ENDPOINT = "person/{person_id}"

// Query Params
internal const val LANGUAGE = "language"
internal const val PAGE = "page"
internal const val REGION = "region"
internal const val ADD_TO_RESPONSE = "append_to_response"

// Discover Movie Params
internal const val INCLUDE_ADULT = "include_adult"
internal const val INCLUDE_VIDEO = "include_adult"
internal const val PRIMARY_RELEASE_YEAR = "primary_release_year"
internal const val PRIMARY_RELEASE_DATE_GTE = "primary_release_date.gte"
internal const val PRIMARY_RELEASE_DATE_LTE = "primary_release_date.lte"
internal const val SORT_BY = "sort_by"
internal const val VOTE_AVERAGE_GTE = "vote_average.gte"
internal const val VOTE_AVERAGE_LTE = "vote_average.lte"
internal const val VOTE_COUNT_GTE = "vote_count.gte"
internal const val VOTE_COUNT_LTE = "vote_count.lte"
internal const val WITH_CAST = "with_cast"
internal const val WITH_CREW = "with_cast"
internal const val WITH_COMPANIES = "with_companies"
internal const val WITH_GENRES = "with_genres"
internal const val WITH_PEOPLE = "with_people"

// Path
internal const val MOVIE_ID = "movie_id"
internal const val PERSON_ID = "person_id"
internal const val TRENDING_TIME_WINDOW = "time_window"