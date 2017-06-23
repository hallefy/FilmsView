package com.ferreira.hallefy.filmsview.Response.Request;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ferreira.hallefy.filmsview.RecyclerView.FeedItem;
import com.ferreira.hallefy.filmsview.RecyclerView.MyRecyclerAdapter;
import com.ferreira.hallefy.filmsview.Response.api.ApiClient;
import com.ferreira.hallefy.filmsview.Response.api.ApiInterface;
import com.ferreira.hallefy.filmsview.Response.model.movies.Movie;
import com.ferreira.hallefy.filmsview.Response.model.movies.MoviesResponse;
import com.ferreira.hallefy.filmsview.views.activity_main;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * Created by HallefyPC on 21/06/2017.
 */




public class RequestDados {


    private final static String TAG = activity_main.class.getSimpleName();;
    private final static String API_KEY = "362a511b0e7d3ed1216c24f338d8b490";
    private  final String IMG_URL = "https://image.tmdb.org/t/p/w500/";
    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();

    private MyRecyclerAdapter adapter;
    private static int  page_num = 1;

    public void getDados(final RecyclerView mRecyclerView, final Context c, int page_num, final ProgressBar progressBar){


        if (API_KEY.isEmpty()) {
            Toast.makeText(c.getApplicationContext(), "Inserir KEY", Toast.LENGTH_LONG).show();
            return;
        }

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MoviesResponse> call = apiService.getMovies(API_KEY,"pt-BR",String.valueOf(page_num));
        call.enqueue(new Callback<MoviesResponse>() {


            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();

                mRecyclerView.setLayoutManager(new GridLayoutManager(c,2));
                adapter = new MyRecyclerAdapter(c, feedItemList);
                mRecyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);



                for(Movie filmes : movies){
                    FeedItem item = new FeedItem();
                    item.setTitle(filmes.getTitle());
                    item.setUrlImagem(IMG_URL+filmes.getPosterPath());
                    item.setRate(""+filmes.getVoteAverage());
                    item.setDescricao(formatdate(filmes.getReleaseDate()));
                    item.setId_filme(filmes.getId());
                    feedItemList.add(item);
                }

            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                progressBar.setVisibility(GONE);
                Log.e(TAG, t.toString());
            }
        });

    }

    public String formatdate(String fdate)
    {
        String datetime=null;
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat d= new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date convertedDate = inputFormat.parse(fdate);
            datetime = d.format(convertedDate);

        }catch (ParseException e)
        {

        }
        return  datetime;


    }

}
