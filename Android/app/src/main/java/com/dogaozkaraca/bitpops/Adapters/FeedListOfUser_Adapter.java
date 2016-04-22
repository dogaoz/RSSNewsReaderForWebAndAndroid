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
import android.widget.TextView;

import com.dogaozkaraca.bitpops.R;
import com.dogaozkaraca.bitpops.AdapterItems.Feed;
import com.dogaozkaraca.bitpops.Settings.RssSettings;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FeedListOfUser_Adapter extends BaseAdapter {
    private Context mContext;
    private List<Feed> mListAppInfo;
    public FeedListOfUser_Adapter(Context ct, List<Feed> feedItemList) {
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
        Button circbutton;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Feed article = mListAppInfo.get(position);
  
        if(convertView == null) {
        	
         LayoutInflater inflater = LayoutInflater.from(mContext);
            
       	
       
        	 ViewHolder holder  = new ViewHolder();
       	 convertView = inflater.inflate(R.layout.do_rss_myfeed_item, null);
         holder.ItemTitle = (TextView)convertView.findViewById(R.id.textView1);
         holder.btn = (ImageButton) convertView.findViewById(R.id.button1);
        holder.circbutton = (Button)  convertView.findViewById(R.id.add_button_rss);
         convertView.setTag(holder);
        
        


        }
        final ViewHolder holder = (ViewHolder)convertView.getTag();

        holder.ItemTitle.setText(article.getTitle());
        //holder.circbutton.setColor(ColorScheme.getBackgroundColor(mContext));
        holder.circbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove RSS from list

                /////////////////////TITLE////////////////////////////
                //Load
                String[] titleList = loadArray("title", mContext);


                List<String> titles = new LinkedList<String>(Arrays.asList(titleList));
                //Do the things

                int findWhereItis = 970324;
                for(int x=0;x<titles.size();x++)
                {
                    if(titles.get(x).equals(article.getTitle()))
                    {
                        findWhereItis = x;
                    }

                }

                Log.w("myfeed","removed title : " + titles.get(findWhereItis));
                titles.remove(findWhereItis);

                //Convertback
                String[] simpleArray = new String[titles.size()];
                titles.toArray(simpleArray);


                //Save
                saveArray(simpleArray, "title", mContext);



                ////////URL

                //Load
                String[] urlList = loadArray("url", mContext);


                List<String> urls =new LinkedList<String>(Arrays.asList(urlList));
                //Do the things
                int findWhereItis2 = 970324;
                for(int x=0;x<urls.size();x++)
                {
                    if(urls.get(x).equals(article.getURL()))
                    {
                        findWhereItis2 = x;
                    }

                }
                Log.w("myfeed","removed url : " + urls.get(findWhereItis2));
                urls.remove(findWhereItis2);
                //Convertback
                String[] simpleArray2 = new String[urls.size()];
                urls.toArray(simpleArray2);
                //Save
                saveArray(simpleArray2, "url", mContext);

                /////////////////////////
                RssSettings.refreshMyFeedList();


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