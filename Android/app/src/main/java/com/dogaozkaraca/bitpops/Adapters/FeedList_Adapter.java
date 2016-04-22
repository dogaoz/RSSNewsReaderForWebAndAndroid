package com.dogaozkaraca.bitpops.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dogaozkaraca.bitpops.R;
import com.dogaozkaraca.bitpops.AdapterItems.Feed;
import com.dogaozkaraca.bitpops.Settings.RssSettings;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class FeedList_Adapter extends BaseAdapter {
    private Context mContext;
    private List<Feed> mListAppInfo;
    public FeedList_Adapter(Context ct, List<Feed> feedItemList) {
        mContext = ct;
        mListAppInfo = feedItemList;
        

    }
 
    @Override
    public int getCount() {
        return mListAppInfo.size();
    }
 
    @Override
    public Object getItem(int position) {
        return mListAppInfo.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public class ViewHolder{
        TextView ItemTitle;
        ImageButton btn;
        ImageView favIcon;
        Button circbutton;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Feed article = mListAppInfo.get(position);
  
        if(convertView == null) {
        	
         LayoutInflater inflater = LayoutInflater.from(mContext);
            

        	 ViewHolder holder  = new ViewHolder();
       	 convertView = inflater.inflate(R.layout.do_rss_source_item, null);
         holder.ItemTitle = (TextView)convertView.findViewById(R.id.textView1);
            holder.favIcon = (ImageView) convertView.findViewById(R.id.faviconImage);
         holder.btn = (ImageButton) convertView.findViewById(R.id.button1);
         holder.circbutton = (Button)  convertView.findViewById(R.id.add_button_rss);
         convertView.setTag(holder);
        
        


        }
        final ViewHolder holder = (ViewHolder)convertView.getTag();

        holder.ItemTitle.setText(article.getTitle());
        Picasso.with(mContext).load(article.getFaviconURL()).placeholder(R.drawable.ic_public_black_48dp).into(holder.favIcon, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(mContext).load(article.getFaviconURL_alt2()).placeholder(R.drawable.ic_public_black_48dp).into(holder.favIcon, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(mContext).load(article.getFaviconURL_alt3()).placeholder(R.drawable.ic_public_black_48dp).into(holder.favIcon, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(mContext).load(article.getFaviconURL_alt4()).placeholder(R.drawable.ic_public_black_48dp).into(holder.favIcon);
                            }
                        });
                    }
                });
            }
        });

         holder.circbutton.setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 //add RSS to list


                 /////////////////////TITLE////////////////////////////
                 //Load
                 String[] titleList = loadArray("title", mContext);


                 List<String> titles =new LinkedList<String>(Arrays.asList(titleList));
                 //Do the things
                 titles.add(article.getTitle());

                 Log.w("myfeed", "added title : " + article.getTitle());
                 //Convertback
                 String[] simpleArray = new String[titles.size()];
                 titles.toArray(simpleArray);
                 //Save
                 saveArray(simpleArray, "title", mContext);



                 ////////URL

                 //Load
                 String[] urlList = loadArray("url", mContext);


                 List<String> urls = new LinkedList<String>(Arrays.asList(urlList));
                 //Do the things

                 Log.w("myfeed", "added url : " + article.getURL());
                 urls.add(article.getURL());

                 //Convertback
                 String[] simpleArray2 = new String[urls.size()];
                 urls.toArray(simpleArray2);
                 //Save
                 saveArray(simpleArray2, "url", mContext);

                 /////////////////////////
                 try // TO-DO : fix it. It calls rss settings when its not in it.
                         // for example adapter used in first time use activity too.
                 {
                     RssSettings.refreshMyFeedList();
                 }
                 catch (Exception e)
                 {

                 }
                 Toast.makeText(mContext, "News Source Added!", Toast.LENGTH_LONG).show();
                 mListAppInfo.remove(position);
                 FeedList_Adapter.this.notifyDataSetChanged();

             }
         });
        
        
        return convertView;
    }


    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("Rotary_RSS", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }

    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("Rotary_RSS", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }


}