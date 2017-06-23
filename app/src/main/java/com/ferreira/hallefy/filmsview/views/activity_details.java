package com.ferreira.hallefy.filmsview.views;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ferreira.hallefy.filmsview.R;
import com.ferreira.hallefy.filmsview.RecyclerView.PicassoClient;
import com.ferreira.hallefy.filmsview.Response.api.ApiClient;
import com.ferreira.hallefy.filmsview.Response.api.ApiInterface;
import com.ferreira.hallefy.filmsview.Response.model.movies.Movie;

import java.text.ParseException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_details extends AppCompatActivity {



    private final static String TAG = activity_main.class.getSimpleName();;
    private final String API_KEY = "362a511b0e7d3ed1216c24f338d8b490";
    private int id_filme = 0;
    private  final String IMG_URL = "https://image.tmdb.org/t/p/w500/";
    private ProgressBar progressBar;
    private TextView title,vote,voteCount,releaseDate,overview,popularidade;
    private ImageView imgMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);




        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        title = (TextView) findViewById(R.id.tvTitle);
        vote = (TextView) findViewById(R.id.tvVoteAverage);
        voteCount = (TextView) findViewById(R.id.tvVoteCount);
        releaseDate = (TextView) findViewById(R.id.tvReleaseData);
        overview = (TextView) findViewById(R.id.tvOverview);
        imgMovie = (ImageView) findViewById(R.id.image_film);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        popularidade = (TextView) findViewById(R.id.tvPopularidade);
        progressBar.setVisibility(View.VISIBLE);

        //recupera id do filme selecionado
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //recuperando id do filme selecionado
        id_filme = bundle.getInt("id_movie");

        System.out.println("id filme: " + id_filme);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Inserir KEY", Toast.LENGTH_LONG).show();
            return;
        }
        //requisição de dados do filme selecionado
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call = apiService.getMovieDetails(id_filme,API_KEY,"pt-BR");
        call.enqueue(new Callback<Movie>() {


            @Override
            public void onResponse(Call<Movie>call, Response<Movie> response) {
                //List<Movie> movies = response.body().;
                title.setText(response.body().getTitle());
                vote.setText(response.body().getVoteAverage().toString());
                voteCount.setText("/ " +response.body().getVoteCount().toString());
                String date = formatdate(response.body().getReleaseDate());
                releaseDate.setText(date);
                PicassoClient.downloadImage(activity_details.this,IMG_URL+response.body().getPosterPath(),imgMovie);
                overview.setText(response.body().getOverview());
                popularidade.setText("Popularidade: "+response.body().getPopularity().toString());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Movie>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }


    //pegando click do botao back
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //caso o botão de voltar for pressionado
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent home_intent = new Intent(activity_details.this, activity_main.class);
        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
        startActivity(home_intent);
        finish();
    }


    //formatar data para o formato correto
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
