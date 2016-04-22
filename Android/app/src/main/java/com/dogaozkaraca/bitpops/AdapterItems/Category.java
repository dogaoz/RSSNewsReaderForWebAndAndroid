package com.dogaozkaraca.bitpops.AdapterItems;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by Doga Oz on 3/26/2016.
 */

public class Category {

    private String title;
    private String img;
    public Category(String title, String img) {
        // TODO Auto-generated constructor stub
        this.title = title;
        this.img = img;

    }


    public String getCatName()
    {

        return this.title;
    }
    public Drawable getCatImage(Context ctx)
    {
        String uri = "@drawable/"+img;  // where myresource (without the extension) is the file

        int imageResource = ctx.getResources().getIdentifier(uri, null, ctx.getPackageName());

        Drawable res = ctx.getResources().getDrawable(imageResource);
        return res;
    }





}
