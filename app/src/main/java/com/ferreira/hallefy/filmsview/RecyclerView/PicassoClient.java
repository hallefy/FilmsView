package com.ferreira.hallefy.filmsview.RecyclerView;

import android.content.Context;
import android.widget.ImageView;

import com.ferreira.hallefy.filmsview.R;
import com.squareup.picasso.Picasso;

/**
 * Created by HallefyPC on 12/06/2017.
 */

public class PicassoClient {
    public static void downloadImage(Context c, String imageUrl, ImageView img)
    {
        if(imageUrl.length()>0 && imageUrl!=null)
        {
            Picasso.with(c).load(imageUrl).placeholder(R.mipmap.ic_nofilme).into(img);
        }else {
            Picasso.with(c).load(R.mipmap.ic_nofilme).into(img);
        }
    }
}