package com.ferreira.hallefy.filmsview.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ferreira.hallefy.filmsview.R;

import java.util.List;

/**
 * Created by HallefyPC on 12/06/2017.
 */


public class MyRecyclerAdapter extends RecyclerView.Adapter<FeedListRowHolder> {
    private List<FeedItem> feedItemList;
    private Context mContext;
    private final String IMG_URL = "https://image.tmdb.org/t/p/w500/";


    public MyRecyclerAdapter(Context context, List<FeedItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;

    }


    @Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        /*View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);
        FeedListRowHolder viewHolder = new FeedListRowHolder(mContext,view);
        return viewHolder;*/
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

        return new FeedListRowHolder(v, mContext);

    }


    @Override
    public void onBindViewHolder(final FeedListRowHolder feedListRowHolder, final int i) {


        final FeedItem feedItem = feedItemList.get(i);
        //Setting text view

        feedListRowHolder.title.setText(feedItem.getTitle());
        feedListRowHolder.rate.setText(feedItem.getRate());
        feedListRowHolder.descricao.setText(feedItem.getDescricao());
        PicassoClient.downloadImage(mContext,(Html.fromHtml(feedItem.getUrlImagem())).toString(),feedListRowHolder.imageFilm);
        feedListRowHolder.relativeLayout.setId(feedItem.getId_filme());
    }


    @Override
    public int getItemCount() {

        return feedItemList.size();

    }
}