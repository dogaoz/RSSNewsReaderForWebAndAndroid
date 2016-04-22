package com.dogaozkaraca.bitpops.FirstUse;

/**
 * Fluxx Reader
 * Created by Doga Oz on 3/26/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.dogaozkaraca.bitpops.AdapterItems.Category;
import com.dogaozkaraca.bitpops.Adapters.CategoryAdapter;
import com.dogaozkaraca.bitpops.FluxFirstUse;
import com.dogaozkaraca.bitpops.R;

import java.util.ArrayList;

public class No1_ChooseCategory extends Fragment {

    public static ArrayList<Category> ChoosenCats;
    public static No1_ChooseCategory newInstance() {
        No1_ChooseCategory sampleSlide = new No1_ChooseCategory();

       // Bundle args = new Bundle();
       // args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
       // sampleSlide.setArguments(args);

        return sampleSlide;
    }

    private int layoutResId;

    public No1_ChooseCategory() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
       //     layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.firstuse_1_categoryselector, container, false);

        GridView catChooser = (GridView) v.findViewById(R.id.gridView1);
        ChoosenCats = new ArrayList<>();

        ArrayList<Category> Cats = new ArrayList<>();

        Cats.add(new Category("News","ic_public_black_48dp"));
        Cats.add(new Category("Music","ic_music_video_black_48dp"));
        Cats.add(new Category("Finance","ic_monetization_on_black_48dp"));

        Cats.add(new Category("Sports","ic_golf_course_black_48dp"));
        Cats.add(new Category("Lifestyle","ic_local_bar_black_48dp"));
        Cats.add(new Category("Photography","ic_monochrome_photos_black_48dp"));

        Cats.add(new Category("Entertainment","ic_games_black_48dp"));
        Cats.add(new Category("Food","ic_restaurant_black_48dp"));
        Cats.add(new Category("Travel","ic_airplanemode_active_black_48dp"));


        catChooser.setAdapter(new CategoryAdapter(getContext(),Cats));

        return v;
    }

    public static void updateTitle()
    {

        if (ChoosenCats.size() == 0)
            FluxFirstUse.firstUseActivity.getSupportActionBar().setTitle("Fluxx | Choose Topics");
        else if (ChoosenCats.size() == 1)
            FluxFirstUse.firstUseActivity.getSupportActionBar().setTitle("Fluxx | 1 topic");
        else
            FluxFirstUse.firstUseActivity.getSupportActionBar().setTitle("Fluxx | " + ChoosenCats.size() + " topics");

    }



}