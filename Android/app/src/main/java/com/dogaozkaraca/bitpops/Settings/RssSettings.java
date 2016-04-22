package com.dogaozkaraca.bitpops.Settings;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dogaozkaraca.bitpops.AdapterItems.CountryItem;
import com.dogaozkaraca.bitpops.AdapterItems.Feed;
import com.dogaozkaraca.bitpops.Adapters.FeedListOfUser_Adapter;
import com.dogaozkaraca.bitpops.Adapters.FeedList_Adapter;
import com.dogaozkaraca.bitpops.Utils.ColorScheme;
import com.dogaozkaraca.bitpops.R;
import com.dogaozkaraca.bitpops.Utils.Utils;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import dogaozkaraca.SwipeRefreshLayoutRotary;


public class RssSettings extends ActionBarActivity {
    List<CountryItem> lst;
    static ListView LA;
    static Context ctx;
    AsyncTask<Void, Void, Void> search;
    ArrayList[] lst5;
    ListView lv5;
    RelativeLayout searchRL;
    TextView tvSearchStatus;
    Button sendRequestToUs;
    SearchView searchView;
    WebView blurCommWebView;
    SwipeRefreshLayoutRotary mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_feed_settings);
        ActionBar ac = getSupportActionBar();
        ctx = this;
        ac.setBackgroundDrawable(new ColorDrawable(ColorScheme.getActionBarColor(this)));

       // SpannableString s = new SpannableString(getSupportActionBar().getTitle());
       // s.setSpan(new TypefaceSpanRo(this, "fonts/MainClockFont.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      //  s.setSpan(new ForegroundColorSpan(ColorScheme.getTextColor(this)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        ac.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ColorScheme.getStatusBarColor(this));
        }
        lv5 = (ListView) findViewById(R.id.listViewSearcher);
        lst5 = new ArrayList[1];
        searchRL = (RelativeLayout) findViewById(R.id.noresultsfound);


        tvSearchStatus = (TextView) findViewById(R.id.noresultsfound_text);
        sendRequestToUs = (Button) findViewById(R.id.noresultsfound_button);
        sendRequestToUs.setBackgroundColor(ColorScheme.getBackgroundColor(this));
        sendRequestToUs.setTextColor(ColorScheme.getTextColor(this));
        blurCommWebView = (WebView) findViewById(R.id.webView1);
        mSwipeRefreshLayout = (SwipeRefreshLayoutRotary) findViewById(R.id.swipe_search_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayoutRotary.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        blurCommWebView.getSettings().setJavaScriptEnabled(true);
        blurCommWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("http://rotaryhome.com/?cancelled")) {

                    blurCommWebView.stopLoading();
                    blurCommWebView.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);

                } else if (url.equals("http://rotaryhome.com/?done")) {
                    blurCommWebView.stopLoading();
                    blurCommWebView.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);


                } else {
                    view.loadUrl(url);
                }

                return true;
            }
        });

        sendRequestToUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.clearFocus();
                searchView.setIconified(true);
                searchView.setIconified(true);
                mSwipeRefreshLayout.setRefreshing(false);
                lv5.setVisibility(View.GONE);
                blurCommWebView.setVisibility(View.VISIBLE);
                blurCommWebView.loadUrl("http://rotary.dogaozkaraca.com/news_source_request.php");
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getApplicationWindowToken(), 0);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);


            }
        });






    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rss_feed_settings, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();


        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                lv5.setVisibility(View.GONE);
                searchRL.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.GONE);

                return false;
            }
        });

searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
    @Override
    public boolean onQueryTextSubmit(String query) {
        queryOnServer(query);

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
            queryOnServer(newText);
        return true;
    }
});


        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                if (!searchView.isIconified())
                {
                    searchRL.setVisibility(View.GONE);
                    mSwipeRefreshLayout.setVisibility(View.GONE);

                    lv5.setVisibility(View.GONE);

                    searchView.setIconified(true);
                    mSwipeRefreshLayout.setRefreshing(false);

                }
                else
                {
                    if(blurCommWebView.getVisibility() == View.VISIBLE)
                    {
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                        blurCommWebView.setVisibility(View.GONE);
                    }
                    else {
                        finish();
                    }
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void queryOnServer(final String query) {

        if (search != null)
            search.cancel(true);

        search = new blurSearch(query);
        search.execute();
    }



    public class blurSearch extends AsyncTask<Void, Void, Void> {
        String query;
        public blurSearch(String query) {
            super();
            this.query = query;
        }

        String[] result;
        protected void onPreExecute() {
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);

            mSwipeRefreshLayout.setRefreshing(true);

            lv5.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.


                String searchString = query;


            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            try {
                /* forming th java.net.URL object */
                URL url = new URL("http://rotary.dogaozkaraca.com/searchOnTheDatabase.php?string=" + searchString);
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


                    result = parts; // Successful
                    ;
                }else{
                    result = null; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d("Error", e.getLocalizedMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            mSwipeRefreshLayout.setRefreshing(false);

            lst5[0] = new ArrayList<Feed>();

               try {
                    searchRL.setVisibility(View.GONE);

                    for (int x2 = 0; x2 < result.length; x2++) {
                        String[] feedProps = result[x2].split("DORO");


                        lst5[0].add(new Feed(feedProps[0], feedProps[1]));


                        Log.w("RO_country", feedProps[0] + " and " + feedProps[1]);

                    }


                } catch(Exception e) {
                    searchRL.setVisibility(View.VISIBLE);

                    tvSearchStatus.setVisibility(View.VISIBLE);
                    tvSearchStatus.setText("No results found!\nBut, we can add missing news source for you.");
                    sendRequestToUs.setVisibility(View.VISIBLE);

                }
                lv5.setAdapter(new FeedList_Adapter(RssSettings.this, lst5[0]));



        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        LA= (ListView) findViewById(R.id.ListView1);
        refreshMyFeedList();

    }

    public static void refreshMyFeedList()
    {

        List<Feed> myfeed = new ArrayList<Feed>();


        //Load
        String[] titleList = loadArray("title", ctx);
        String[] urlList = loadArray("url", ctx);
        for (int x=0;x<titleList.length;x++) {
            myfeed.add(new Feed(titleList[x], urlList[x]));

        }

        LA.setAdapter(new FeedListOfUser_Adapter(ctx, myfeed));


    }
    public static String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("Rotary_RSS", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }

@Override
public void onBackPressed()
{

    if (!searchView.isIconified())
    {
        searchRL.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.GONE);

        lv5.setVisibility(View.GONE);
        searchView.setIconified(true);
        mSwipeRefreshLayout.setRefreshing(false);

    }
    else
    {
        if(blurCommWebView.getVisibility() == View.VISIBLE)
        {
            mSwipeRefreshLayout.setVisibility(View.GONE);

            blurCommWebView.setVisibility(View.GONE);
        }
        else {
            finish();
        }
    }

}


    public static void onAddSource(View v)
    {
      Utils.createNewSourceDialog(v.getContext());


    }



}
