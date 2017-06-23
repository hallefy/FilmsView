package com.ferreira.hallefy.filmsview.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ferreira.hallefy.filmsview.R;
import com.ferreira.hallefy.filmsview.Response.Request.RequestDados;

public class activity_main extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProgressBar progressBar;
    private static int  page_num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        progressBar.setVisibility(View.VISIBLE);

        final RequestDados requestDados = new RequestDados();

        if(!verificaConexao()){
            progressBar.setVisibility(View.GONE);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Sem conexão com a internet")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            activity_main.this.finish();
                        }
                    })
                    .create()
                    .show();
            builder.create();
        }else {
            //requisita filmes caso o usuario possua internet
            requestDados.getDados(mRecyclerView,activity_main.this,page_num,progressBar);
        }


            //caso o usuario chege no fim da view é carregado mais filmes
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisible = layoutManager.findLastVisibleItemPosition();

                    boolean endHasBeenReached = lastVisible + 1 >= totalItemCount;
                    if (totalItemCount > 0 && endHasBeenReached) {
                        page_num++;
                        progressBar.setVisibility(View.VISIBLE);
                        requestDados.getDados(mRecyclerView,activity_main.this,page_num,progressBar);

                    }
                }
            });


    }





    //verifica se o usuario possui conexao
    public boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }



}
