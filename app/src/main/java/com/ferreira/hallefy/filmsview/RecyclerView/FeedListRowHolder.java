package com.ferreira.hallefy.filmsview.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ferreira.hallefy.filmsview.R;
import com.ferreira.hallefy.filmsview.views.activity_details;

/**
 * Created by HallefyPC on 12/06/2017.
 */

public class FeedListRowHolder extends RecyclerView.ViewHolder {

    protected TextView title,rate,descricao;
    protected ImageView imageFilm;
    Context context;
    protected RelativeLayout relativeLayout;


    public FeedListRowHolder(View view, final Context context) {
        super(view);

        this.context = context;
        this.rate = (TextView) view.findViewById(R.id.tvRate);
        this.imageFilm = (ImageView) view.findViewById(R.id.ImageFilm);
        this.title = (TextView) view.findViewById(R.id.tvTitulo);
        this.descricao = (TextView) view.findViewById(R.id.tvDescricao);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.relativeCard);


        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = relativeLayout.getId();

                Bundle bundle = new Bundle();
                Intent intent = new Intent(context,activity_details.class);

                bundle.putInt("id_movie", id);
                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

    }
}