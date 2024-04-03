package uz.john.data.remote

// Account
internal const val ADD_TO_FAVORITE_ENDPOINT = "account/{account_id}/favorite"
internal const val ADD_TO_WATCH_LIST_ENDPOINT = "account/{account_id}/watchlist"

// Movies
internal const val TRENDING_ENDPOINT = "trending/movie/{time_window}"
internal const val TOP_RATED_MOVIES_ENDPOINT = "movie/top_rated"
internal const val MOVIE_DETAILS_ENDPOINT = "movie/{movie_id}"
internal const val SIMILAR_MOVIES_ENDPOINT = "movie/{movie_id}/similar"
internal const val RECOMMENDED_MOVIES_ENDPOINT = "movie/{movie_id}/recommendations"
internal const val DISCOVER_MOVIES = "discover/movie"
internal const val ALL_MOVIE_GENRES_ENDPOINT = "genre/movie/list"
internal const val MOVIE_ACCOUNT_STATE_ENDPOINT = "movie/{movie_id}/account_states"
internal const val FAVORITE_MOVIES_ENDPOINT = "account/{account_id}/favorite/movies"
internal const val WATCH_LATER_MOVIES_ENDPOINT = "account/{account_id}/watchlist/movies"
internal const val MOVIE_MEDIA_TYPE = "movie"

// Person
internal const val PERSON_DETAILS_ENDPOINT = "person/{person_id}"
internal const val POPULAR_PEOPLE_ENDPOINT = "person/popular"
internal const val APPEND_MOVIE_CREDITS = "movie_credits"
internal const val APPEND_TV_SHOW_CREDITS = "tv_credits"

// Tv Shows
internal const val TRENDING_TV_SHOWS_ENDPOINT = "trending/tv/{time_window}"
internal const val TV_SHOW_DETAILS_ENDPOINT = "tv/{series_id}"
internal const val SIMILAR_TV_SHOWS_ENDPOINT = "tv/{series_id}/similar"
internal const val RECOMMENDED_TV_SHOWS_ENDPOINT = "tv/{series_id}/recommendations"
internal const val FAVORITE_TV_SHOWS_ENDPOINT = "account/{account_id}/favorite/tv"
internal const val WATCH_LATER_TV_SHOWS_ENDPOINT = "account/{account_id}/watchlist/tv"
internal const val TV_SHOW_MEDIA_TYPE = "tv"
internal const val TV_SHOW_ACCOUNT_STATE_ENDPOINT = "tv/{series_id}/account_states"
internal const val DISCOVER_TV_SHOWS = "discover/tv"
internal const val ALL_TV_SHOW_GENRES_ENDPOINT = "genre/tv/list"
internal const val TOP_RATED_TV_SHOWS_ENDPOINT = "tv/top_rated"

// Search
internal const val SEARCH_MOVIE_ENDPOINT = "search/movie"
internal const val SEARCH_PEOPLE_ENDPOINT = "search/person"
internal const val SEARCH_TV_SHOWS_ENDPOINT = "search/tv"
internal const val YEAR = "year"
internal const val QUERY = "query"

// Query Params
internal const val LANGUAGE = "language"
internal const val PAGE = "page"
internal const val REGION = "region"
internal const val APPEND_TO_RESPONSE = "append_to_response"
internal const val APPEND_IMAGES = "images"
internal const val APPEND_CREDITS = "credits"
internal const val APPEND_VIDEOS = "videos"

// Discover Movie Params
internal const val INCLUDE_ADULT = "include_adult"
internal const val INCLUDE_VIDEO = "include_adult"
internal const val PRIMARY_RELEASE_YEAR = "primary_release_year"
internal const val PRIMARY_RELEASE_DATE_GTE = "primary_release_date.gte"
internal const val PRIMARY_RELEASE_DATE_LTE = "primary_release_date.lte"
internal const val SORT_BY = "sort_by"
internal const val WITH_CAST = "with_cast"
internal const val WITH_CREW = "with_cast"
internal const val WITH_PEOPLE = "with_people"

// Discover TvShows Params
internal const val AIR_DATE_GTE = "air_date.gte"
internal const val AIR_DATE_LTE = "air_date.lte"
internal const val FIRST_AIR_DATE_YEAR = "first_air_date_year"
internal const val FIRST_AIR_DATE_GTE = "first_air_date.gte"
internal const val FIRST_AIR_DATE_LTE = "first_air_date.lte"
internal const val SCREENED_THEATRICALLY = "screened_theatrically"
internal const val TIMEZONE = "timezone"

// Discover Media
internal const val VOTE_AVERAGE_GTE = "vote_average.gte"
internal const val VOTE_AVERAGE_LTE = "vote_average.lte"
internal const val VOTE_COUNT_GTE = "vote_count.gte"
internal const val VOTE_COUNT_LTE = "vote_count.lte"
internal const val WITH_GENRES = "with_genres"
internal const val WITH_COMPANIES = "with_companies"

// Path
internal const val MOVIE_ID = "movie_id"
internal const val TV_SHOW_ID = "series_id"
internal const val SERIES_ID = "series_id"
internal const val ACCOUNT_ID_PATH = "account_id"
internal const val SESSION_ID_PATH = "session_id"
internal const val PERSON_ID = "person_id"
internal const val TRENDING_TIME_WINDOW = "time_window"
internal const val TRENDING_TIME_WINDOW_DAY = "day"
internal const val TRENDING_TIME_WINDOW_WEEK = "week"