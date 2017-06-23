package com.ferreira.hallefy.filmsview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ferreira.hallefy.filmsview.RecyclerView.FeedItem;
import com.ferreira.hallefy.filmsview.RecyclerView.MyRecyclerAdapter;
import com.ferreira.hallefy.filmsview.Response.api.ApiClient;
import com.ferreira.hallefy.filmsview.Response.api.ApiInterface;
import com.ferreira.hallefy.filmsview.Response.model.movies.Movie;
import com.ferreira.hallefy.filmsview.Response.model.movies.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_main extends AppCompatActivity {





    private final static String TAG = activity_main.class.getSimpleName();;
    private final static String API_KEY = "362a511b0e7d3ed1216c24f338d8b490";
    private  final String IMG_URL = "https://image.tmdb.org/t/p/w500/";
    private List<FeedItem> feedItemList = new ArrayList<FeedItem>();
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private ProgressBar progressBar;
    private static int page_num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));


        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Inserir KEY", Toast.LENGTH_LONG).show();
            return;
        }

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MoviesResponse> call = apiService.getMovies(API_KEY,"pt-BR",String.valueOf(page_num));
        call.enqueue(new Callback<MoviesResponse>() {


            @Override
            public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();

                adapter = new MyRecyclerAdapter(activity_main.this, feedItemList);
                mRecyclerView.setAdapter(adapter);



                for(Movie filmes : movies){
                    FeedItem item = new FeedItem();
                    item.setTitle(filmes.getTitle());
                    item.setUrlImagem(IMG_URL+filmes.getPosterPath());
                    item.setRate(""+filmes.getVoteAverage());
                    item.setDescricao(filmes.getReleaseDate());
                    item.setId_filme(filmes.getId());
                    feedItemList.add(item);
                }

            }

            @Override
            public void onFailure(Call<MoviesResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 1 >= totalItemCount;
                if (totalItemCount > 0 && endHasBeenReached) {
                    page_num++;
                    Call<MoviesResponse> call = apiService.getMovies(API_KEY,"pt-BR",String.valueOf(page_num));
                    call.enqueue(new Callback<MoviesResponse>() {


                        @Override
                        public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                            List<Movie> movies = response.body().getResults();


                            for(Movie filmes : movies){
                                FeedItem item = new FeedItem();
                                item.setTitle(filmes.getTitle());
                                item.setUrlImagem(IMG_URL+filmes.getPosterPath());
                                item.setRate(""+filmes.getVoteAverage());
                                item.setDescricao(filmes.getReleaseDate());
                                item.setId_filme(filmes.getId());
                                feedItemList.add(item);
                            }

                        }

                        @Override
                        public void onFailure(Call<MoviesResponse>call, Throwable t) {
                            // Log error here since request failed
                            Log.e(TAG, t.toString());
                        }
                    });
                }
            }
        });

    }




}
