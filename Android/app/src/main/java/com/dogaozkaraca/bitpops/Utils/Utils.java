package com.dogaozkaraca.bitpops.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.dogaozkaraca.bitpops.AdapterItems.Category;
import com.dogaozkaraca.bitpops.AdapterItems.Feed;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by doga on 07/03/16.
 */
public class Utils {
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line;
        }

            /* Close Stream */
        if(null!=inputStream){
            inputStream.close();
        }
        return result;
    }
    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static void createNewSourceDialog(Context ct)
    {
        Dialog dl = new newSourceDialog(ct);
        if (!dl.isShowing())
        {
            dl.show();
            dl.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            dl.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        }


    }


    public static boolean saveNewFeed(String feedName,String feedLink,Context mContext)
    {
        // FEED NAME (TITLE)
        //Load Feed Name Array from Shared Preferences
        String[] titleList = loadArray("title", mContext);
        List<String> titles =new LinkedList<String>(Arrays.asList(titleList));
        //Add new Feed Name to Array
        titles.add(feedName);
        //Convert String list to array and save
        String[] simpleArray = new String[titles.size()];
        titles.toArray(simpleArray);
        saveArray(simpleArray, "title", mContext);


        // FEED LINK (URL)
        //Load Feed Name Array from Shared Preferences
        String[] urlList = loadArray("url", mContext);
        List<String> urls = new LinkedList<String>(Arrays.asList(urlList));
        //Add new Feed Name to Array
        urls.add(feedLink);
        //Convert String list to array and save
        String[] simpleArray2 = new String[urls.size()];
        urls.toArray(simpleArray2);
       return saveArray(simpleArray2, "url", mContext);
    }

    public static boolean deleteFeed(String feedName,String feedLink,Context mContext)
    {
        // FEED NAME (TITLE)
        String[] titleList = loadArray("title", mContext);


        List<String> titles = new LinkedList<String>(Arrays.asList(titleList));

        int findWhereItis = 970324;
        for(int x=0;x<titles.size();x++)
        {
            if(titles.get(x).equals(feedName))
            {
                findWhereItis = x;
            }

        }

        titles.remove(findWhereItis);
        String[] simpleArray = new String[titles.size()];
        titles.toArray(simpleArray);
        saveArray(simpleArray, "title", mContext);

        // FEED LINK (URL)
        String[] urlList = loadArray("url", mContext);
        List<String> urls =new LinkedList<String>(Arrays.asList(urlList));
        int findWhereItis2 = 970324;
        for(int x=0;x<urls.size();x++)
        {
            if(urls.get(x).equals(feedLink))
            {
                findWhereItis2 = x;
            }

        }
        urls.remove(findWhereItis2);
        String[] simpleArray2 = new String[urls.size()];
        urls.toArray(simpleArray2);
        return saveArray(simpleArray2, "url", mContext);
    }

    public static boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("Rotary_RSS", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }

    public static String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("Rotary_RSS", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }

    public static ArrayList<Feed> getFeedsByCategory(final ArrayList<Category> choosenCats, int howmanyitemperCat) {

        ArrayList<Feed> feeds = new ArrayList<>();

        for (int i=0;i< choosenCats.size();i++) {
            Category cat = choosenCats.get(i);
            Log.w("Fluxy",cat.getCatName());
            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            try {
                /* forming th java.net.URL object */
                URL url = new URL("http://rotary.dogaozkaraca.com/getFeedListByCategory.php?category=" + cat.getCatName());
                urlConnection = (HttpURLConnection) url.openConnection();

                 /* optional request header */
                urlConnection.setRequestProperty("Content-Type", "application/json");

                /* optional request header */
                urlConnection.setRequestProperty("Accept", "application/json");

                /* for Get request */
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();
                String[] parts = {"",""};
                /* 200 represents HTTP OK */
                if (statusCode == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    String response = Utils.convertInputStreamToString(inputStream);
                    parts = response.split("SPACE");

                    Log.w("RO_country", "space splitted");
                    for (String p : parts) {
                        Log.w("fluxy", "Parts : " + p);
                    }
                    ;
                } else {
                    //result = null; //"Failed to fetch data!";
                }

                try {

                    for (int k = 0; k < howmanyitemperCat; k++) {
                        String[] feedProps = parts[k].split("DORO");
                        Log.w("fluxy",feedProps[0]);
                        Log.w("fluxy",feedProps[1]);

                        feeds.add(new Feed(feedProps[0], feedProps[1]));

                    }


                } catch(Exception e) {
                    // ERROR. NONE FOUND
                    e.printStackTrace();
                    Log.e("fluxy",e.toString());

                }
            } catch (Exception e) {
                Log.d("Error", e.getLocalizedMessage());
            }

        }


        return feeds;
    }



}
