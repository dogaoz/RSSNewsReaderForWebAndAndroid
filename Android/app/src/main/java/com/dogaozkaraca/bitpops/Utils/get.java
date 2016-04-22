package com.dogaozkaraca.bitpops.Utils;

import com.dogaozkaraca.bitpops.AdapterItems.Feed;

import java.util.ArrayList;

/**
 * Created by Doga Oz on 3/26/2016.
 */
public class get {

    public static ArrayList<Feed> FeedsFromCategory(String Category,int howmuch)
    {
        ArrayList<Feed> Feeds = new ArrayList<Feed>();

        for (int i=0;i<howmuch;i++)
        {
            Feeds.add(new Feed("Title","URL"));
        }


        return Feeds;
    }
}
