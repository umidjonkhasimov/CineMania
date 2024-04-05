# CineMania
CineMania is an Android application built with Jetpack Compose. Movie fans can have recent information about the trending, popular, top rated (and many more) Movies, TvShows and Celebrities. Not only that, users can become familiar with the details of their favorite Movies, TvShows and the casts. If they are signed in with their TMDB acoount, they can also save Movies and TvShows as favorites and/or as Watch Later. Finally, users of CineMania application can search for Movies, TvShows and Celebrities, and have all the detailed information about them.
# App Architechture
CineMania application is built on top of the concept that is well know for any Android developer - MVI (Model-View-Intent):
* Model - covers all the data related code;
* View - covers all presentation related code;
* Intent - is an event that presentation layer sends to a ViewModel which reacts to that event and changes UiState accordingly or emits SideEffect to the presentation layer. UiState is observed by presentation layer, which means any change to UiState changes the state of the application.
# App Structure
CineMania application is divided into modules based on their responsibilities. In the image, you can see all modules and their dependencies. Module "A" pointing to module "B" means module "A" depends on module "B"
![App Structure](https://github.com/umidjonkhasimov/CineMania/blob/master/images/Untitled-2024-02-24-1431.png?raw=true)
# Setup
Get your api key from [TMDB API](https://developer.themoviedb.org/docs/getting-started) and replace it in the ```local.properties``` file:
```

 API_KEY = "API_KEY_VALUE"

```
# Tech Stack
* Jetpack Compose - is a modern UI toolkit to build native android applications.
* ViewModel - is a class that holds application data and survives configuration changes.
* Kotlin Coroutines - is a lightweight threads that is used to handle concurrent programming. Coroutines allow developers to write asyhnchronous code in a synchronous way.
* Retrofit - is one of the widely used HTTP clients for Android. It is used to consume RESTful web services and convert to objects.
* GSON - is used to parse Json format to Kotlin objects.
* Kotlin Flow - is a stream of data that can be collected inside coroutines.
* Dagger Hilt - is used to inject dependencies into classes, for example, in the project, different types of Use Cases are injected into ViewModel's constructor.
* Coil - is used to fetch and display images asynchronously.
