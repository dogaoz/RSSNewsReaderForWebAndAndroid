package com.dogaozkaraca.bitpops.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dogaozkaraca.bitpops.AdapterItems.Category;
import com.dogaozkaraca.bitpops.FirstUse.No1_ChooseCategory;
import com.dogaozkaraca.bitpops.R;

import java.util.ArrayList;

/**
 * Created by Doga Oz on 3/26/2016.
 */
public class CategoryAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Category> Categories;

    public CategoryAdapter(Context ct, ArrayList<Category> feedItemList) {
        mContext = ct;
        Categories = feedItemList;


    }

    @Override
    public int getCount() {
        return Categories.size();
    }

    @Override
    public Object getItem(int position) {
        return Categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        RelativeLayout catRL;
        TextView catTextView;
        ImageView catImageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Category cat = Categories.get(position);

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            //GridView grid = (GridView)parent;
            //int size = grid.getRequestedColumnWidth();

            ViewHolder holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.category_item, null);
            holder.catRL = (RelativeLayout) convertView.findViewById(R.id.RL);
            holder.catTextView = (TextView) convertView.findViewById(R.id.textView1);
            holder.catImageView = (ImageView) convertView.findViewById(R.id.imageView1);
            //convertView.setLayoutParams(new GridView.LayoutParams(size, size));
            convertView.setTag(holder);


        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.catTextView.setText(cat.getCatName());
        holder.catImageView.setImageDrawable(cat.getCatImage(mContext));


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChoosenCats(cat,holder.catRL);
            }
        });

        holder.catRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChoosenCats(cat,holder.catRL);

            }
        });
        holder.catImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChoosenCats(cat,holder.catRL);

            }
        });
        holder.catTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateChoosenCats(cat,holder.catRL);

            }
        });


        return convertView;
    }

    public void updateChoosenCats(Category cat,RelativeLayout catRL)
    {
        int color = Color.TRANSPARENT;
        Drawable background = catRL.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        if (color == Color.parseColor("#AFAFAF"))
        {
            for (int i = 0;i<No1_ChooseCategory.ChoosenCats.size();i++) {
                Category choosencat = No1_ChooseCategory.ChoosenCats.get(i);
                if (choosencat.getCatName().equals(cat.getCatName())) {
                    catRL.setBackgroundColor(Color.parseColor("#DFDFDF"));
                    No1_ChooseCategory.ChoosenCats.remove(i);
                    No1_ChooseCategory.updateTitle();
                    break;
                }
            }
        }
        else {
            catRL.setBackgroundColor(Color.parseColor("#AFAFAF"));
            No1_ChooseCategory.ChoosenCats.add(new Category(cat.getCatName(), null));
            No1_ChooseCategory.updateTitle();
        }



    }

}




