package com.ferreira.hallefy.filmsview.Response.api;

import com.ferreira.hallefy.filmsview.Response.model.movies.Movie;
import com.ferreira.hallefy.filmsview.Response.model.movies.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HallefyPC on 12/06/2017.
 */


public interface ApiInterface {
    @GET("discover/movie?api_key=")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey,@Query("language") String language);

    @GET("discover/movie?api_key=")
    Call<MoviesResponse> getMovies(@Query("api_key") String apiKey,
                                   @Query("language") String language,
                                   @Query("page") String page);

}