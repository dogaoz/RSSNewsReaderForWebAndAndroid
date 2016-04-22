package com.dogaozkaraca.bitpops.FirstUse;

/**
 * Created by Doga Oz on 3/26/2016.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dogaozkaraca.bitpops.AdapterItems.Feed;
import com.dogaozkaraca.bitpops.Adapters.FeedList_Adapter;
import com.dogaozkaraca.bitpops.R;
import com.dogaozkaraca.bitpops.Utils.Utils;

import java.util.ArrayList;

public class No2_ChooseRecommendedFeeds extends Fragment {

    public static ListView feedChooser;
    public static No2_ChooseRecommendedFeeds newInstance() {
        No2_ChooseRecommendedFeeds sampleSlide = new No2_ChooseRecommendedFeeds();

       // Bundle args = new Bundle();
       // args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
       // sampleSlide.setArguments(args);

        return sampleSlide;
    }

    private int layoutResId;

    public No2_ChooseRecommendedFeeds() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
       //     layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.firstuse_2_feedchooser, container, false);

        feedChooser = (ListView) v.findViewById(R.id.listView1);





        return v;
    }

    public static void updateList(final Context ctx)
    {
        new AsyncTask<Void, Void, ArrayList<Feed>>() {

            @Override
            protected void onPreExecute()
            {
                // TODO : Show Loading Dialog...
            }
            @Override
            protected ArrayList<Feed> doInBackground(Void... params) {

                // Show 5 items per category
                return Utils.getFeedsByCategory(No1_ChooseCategory.ChoosenCats,5);
            }

            @Override
            protected void onPostExecute(ArrayList<Feed> feeds)
            {
                // TODO : Hide Loading Dialog...
                feedChooser.setAdapter(new FeedList_Adapter(ctx,feeds));
            }
        }.execute();
    }

}