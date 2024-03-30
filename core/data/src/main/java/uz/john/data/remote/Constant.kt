package uz.john.data.remote

// Movies
internal const val TRENDING_ENDPOINT = "trending/movie/{time_window}"
internal const val TOP_RATED_MOVIES_ENDPOINT = "movie/top_rated"
internal const val MOVIE_DETAILS_ENDPOINT = "movie/{movie_id}"
internal const val SIMILAR_MOVIES_ENDPOINT = "movie/{movie_id}/similar"
internal const val RECOMMENDED_MOVIES_ENDPOINT = "movie/{movie_id}/recommendations"
internal const val DISCOVER_MOVIES = "discover/movie"
internal const val ALL_GENRES_ENDPOINT = "genre/movie/list"
internal const val ACCOUNT_STATE_ENDPOINT = "movie/{movie_id}/account_states"
internal const val FAVORITE_ENDPOINT = "account/{account_id}/favorite"
internal const val WATCH_LIST_ENDPOINT = "account/{account_id}/watchlist"
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
internal const val TV_SHOW_MEDIA_TYPE = "tv"

// Search
internal const val SEARCH_MOVIE_ENDPOINT = "search/movie"
internal const val SEARCH_PEOPLE_ENDPOINT = "search/person"
internal const val SEARCH_TV_SHOWS_ENDPOINT = "search/tv"
internal const val YEAR = "year"
internal const val QUERY = "query"
internal const val FIRST_AIR_DATE_YEAR = "first_air_date_year"

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
internal const val SERIES_ID = "series_id"
internal const val PERSON_ID = "person_id"
internal const val TRENDING_TIME_WINDOW = "time_window"
internal const val TRENDING_TIME_WINDOW_DAY = "day"
internal const val TRENDING_TIME_WINDOW_WEEK = "week"