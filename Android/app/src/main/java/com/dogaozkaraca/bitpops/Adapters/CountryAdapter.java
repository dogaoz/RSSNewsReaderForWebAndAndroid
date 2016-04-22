package com.dogaozkaraca.bitpops.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dogaozkaraca.bitpops.Utils.ColorScheme;
import com.dogaozkaraca.bitpops.R;
import com.dogaozkaraca.bitpops.AdapterItems.CountryItem;
import com.dogaozkaraca.bitpops.Utils.LoadingDialog;
import com.dogaozkaraca.bitpops.AdapterItems.Feed;
import com.dogaozkaraca.bitpops.Utils.Utils;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends BaseAdapter {
    private Context mContext;
    private List<CountryItem> mListAppInfo;
    public CountryAdapter(Context ct, List<CountryItem> feedItemList) {
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
        ImageButton fakeCheckBox;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CountryItem article = mListAppInfo.get(position);
  
        if(convertView == null) {
        	
         LayoutInflater inflater = LayoutInflater.from(mContext);
            
       	
       
        	 ViewHolder holder  = new ViewHolder();
       	 convertView = inflater.inflate(R.layout.do_country_item, null);
         holder.ItemTitle = (TextView)convertView.findViewById(R.id.textView1);
         holder.btn = (ImageButton) convertView.findViewById(R.id.button1);
         convertView.setTag(holder);
        
        


        }
        final ViewHolder holder = (ViewHolder)convertView.getTag();

		if (article.getDrawable() !=null)
       holder.btn.setImageDrawable(article.getDrawable());

        holder.btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openChooser(article.getCountryCoded(), article.getCountry());
            }
        });

        holder.ItemTitle.setText(article.getCountry());
        convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


                openChooser(article.getCountryCoded(),article.getCountry());
        }
        });
        
        
        return convertView;
    }

    public class source_Item
    {
        String title;
        String URL;
        public source_Item(String title,String URL)
        {
            this.title = title;
            this.URL = URL;

        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle()
        {
            return this.title;
        }

        public void setURL(String URL)
        {
            this.URL = URL;
        }

        public String getURL()
        {
            return this.URL;
        }
    }
public void openChooser(final String country, final String articleCountry)
{
    final Dialog dialog2 = new Dialog(mContext);
    dialog2.setCancelable(true);

    final View view = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.choose_final_item, null);

    final ListView lv = (ListView) view.findViewById(R.id.rss_choose_detailview);
    final TextView tv = (TextView) view.findViewById(R.id.tw);
    final ArrayList lst = new ArrayList<Feed>();

    new AsyncTask<String,String, String[]>(){

        LoadingDialog dialog = new LoadingDialog(mContext);
        protected void onPreExecute() {
            this.dialog.setMessage("Please Wait...");
            this.dialog.setCancelable(false);
            try {

                this.dialog.show();
            }
            catch(Exception e){}
        }
        @Override
        protected String[] doInBackground(String... params) {
            // TODO: attempt authentication against a network service.


            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            Integer result = 0;
            try {
                /* forming th java.net.URL object */
                URL url = new URL("http://rotary.dogaozkaraca.com/Ro_getFeed.php?country="+country);
                urlConnection = (HttpURLConnection) url.openConnection();

                 /* optional request header */
                urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
                urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode ==  200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    String response = Utils.convertInputStreamToString(inputStream);
                    String[] parts = response.split("SPACE");

                    Log.w("RO_country","space splitted");


                    result = 1; // Successful
                    return parts;
                }else{
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d("Error", e.getLocalizedMessage());
            }




            return null;
        }

        @Override
        protected void onPostExecute(final String[] result) {


            if(result != null)
            {

                for (int x2=0; x2<result.length;x2++)
                {
                    String[]feedProps = result[x2].split("DORO");


                    lst.add(new Feed(feedProps[0],feedProps[1]));


                        Log.w("RO_country",feedProps[0]+" and " + feedProps[1]);

                }


            }
            else
            {
                Log.w("RO_country","null returned");

            }
            lv.setAdapter(new FeedList_Adapter(mContext, lst));
            this.dialog.hide();
            tv.setText(articleCountry);
            tv.setBackgroundColor(ColorScheme.getActionBarColor(mContext));
            dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog2.setContentView(view);

            dialog2.show();
        }
    }.execute();
}

}