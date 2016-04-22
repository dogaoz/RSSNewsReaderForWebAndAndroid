package com.dogaozkaraca.bitpops.loadFeed;

import android.util.Log;

import com.dogaozkaraca.bitpops.AdapterItems.FluxItem;
import com.dogaozkaraca.bitpops.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class loadRSS {



	   public static int loadRSS(String type, String rssURL, List<FluxItem> FeedItemList, String sourceName)
	    {

            String feed_fix = null;

	    	try {


                InputStream inputStream = null;
                HttpURLConnection urlConnection = null;
                Integer result = 0;
                try {
                /* forming th java.net.URL object */
                    URL url = new URL("http://rotary.dogaozkaraca.com/getSourceProps.php?url="+rssURL);
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
                        feed_fix = response;
                                //parseResult(response);
                        result = 1; // Successful
                    }else{
                        result = 0; //"Failed to fetch data!";
                    }
                } catch (Exception e) {
                    Log.d("Error", e.getLocalizedMessage());
                }






                if (feed_fix != null && feed_fix.equals("for_billboard"))
                {






                    JSONObject json = tillNow_JSON.getJSONFromUrl("http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=100&q="+ rssURL);
                    // parsing json object
                    if (json.getInt("responseStatus") == 200) {
                        Log.d("RSS","responseStatus=200");
                        JSONObject responseData = json.getJSONObject("responseData");
                        JSONObject feed =  responseData.getJSONObject("feed");
                        JSONArray entries = feed.getJSONArray("entries");


                        for (int i = 0; i < entries.length(); i++) {

                            JSONObject post = (JSONObject) entries.getJSONObject(i);

                            String title =post.getString("title");
                            String content_snippet = post.getString("contentSnippet");
                            String publishedDate = post.getString("publishedDate");
                            // java.text.ParseException: Unparseable date: "Fri, 22 Aug 2014 14:29:11 -0700" (at offset 0)

                            SimpleDateFormat  format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z",Locale.US);

                            Date date = null;
                            try {
                                date = format.parse(publishedDate);
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Log.d("DoFeed_DateTime","RSS Date Parsed : " +date + "////with this post : " + title);


                            String url = post.getString("link");
                            String author = post.getString("author");
                            String content = post.getString("content");
                            Log.w("DoFeed_RSS","Content : "+content);

                            String finalimgurl = "no_image_fix";


                            if (finalimgurl != null)
                            {
                                FluxItem df = new FluxItem(type ,date,content,finalimgurl,url,"",null,content_snippet,content,null);
                                df.setSourceName(sourceName);
                                FeedItemList.add(df);
                            }
                            else
                            {
                                FluxItem df = new FluxItem(type ,date,content,"",url,"",null,content_snippet,content,null);
                                df.setSourceName(sourceName);
                                FeedItemList.add(df);

                            }
                            Log.d("FluxItem","Title : "+title +"  Link : "+url+"  Author : "+author + "  PublishedDate : "+ publishedDate);
                        }
                    }
                    else
                    {
                        return 1;
                    }



                }
                //else if (feed_fix != null )
               // {



             //   }
                else if (feed_fix != null && (feed_fix.equals("no_images_t1") || feed_fix.equals("unwanted_wrong_images")))
                {




                    JSONObject json = tillNow_JSON.getJSONFromUrl("http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=100&q="+ rssURL);
                    // parsing json object
                    if (json.getInt("responseStatus") == 200) {
                        Log.d("RSS","responseStatus=200");
                        JSONObject responseData = json.getJSONObject("responseData");
                        JSONObject feed =  responseData.getJSONObject("feed");
                        JSONArray entries = feed.getJSONArray("entries");


                        for (int i = 0; i < entries.length(); i++) {
                            JSONObject post = (JSONObject) entries.getJSONObject(i);

                            String title = post.getString("title");
                            String content_snippet = post.getString("contentSnippet");
                            String publishedDate = post.getString("publishedDate");
                            // java.text.ParseException: Unparseable date: "Fri, 22 Aug 2014 14:29:11 -0700" (at offset 0)

                            SimpleDateFormat  format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z",Locale.US);

                            Date date = null;
                            try {
                                date = format.parse(publishedDate);
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Log.d("DoFeed_DateTime","RSS Date Parsed : " +date + "////with this post : " + title);


                            String url = post.getString("link");
                            String author = post.getString("author");
                            String content = post.getString("content");
                            Log.w("DoFeed_RSS","Content : "+content);

                            String finalimgurl = "no_image_fix";


                            if (finalimgurl != null)
                            {
                                FluxItem df = new FluxItem(type ,date,title,finalimgurl,url,"",null,content_snippet,content,null);
                                df.setSourceName(sourceName);
                                FeedItemList.add(df);
                            }
                            else
                            {
                                FluxItem df = new FluxItem(type ,date,title,"",url,"",null,content_snippet,content,null);
                                df.setSourceName(sourceName);
                                FeedItemList.add(df);

                            }
                            Log.d("FluxItem","Title : "+title +"  Link : "+url+"  Author : "+author + "  PublishedDate : "+ publishedDate);
                        }
                    }
                    else
                    {
                        return 1;
                    }



                }
                else
                {


                    JSONObject json = tillNow_JSON.getJSONFromUrl("http://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=100&q="+ rssURL);
                    // parsing json object
                    if (json.getInt("responseStatus") == 200) {
                        Log.d("RSS","responseStatus=200");
                        JSONObject responseData = json.getJSONObject("responseData");
                        JSONObject feed =  responseData.getJSONObject("feed");
                        JSONArray entries = feed.getJSONArray("entries");


                        for (int i = 0; i < entries.length(); i++) {
                            JSONObject post = (JSONObject) entries.getJSONObject(i);

                            String title = post.getString("title");
                            String content_snippet = post.getString("contentSnippet");
                            String publishedDate = post.getString("publishedDate");
                            // java.text.ParseException: Unparseable date: "Fri, 22 Aug 2014 14:29:11 -0700" (at offset 0)

                            SimpleDateFormat  format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z",Locale.US);

                            Date date = null;
                            try {
                                date = format.parse(publishedDate);
                            } catch (ParseException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Log.d("DoFeed_DateTime","RSS Date Parsed : " +date + "////with this post : " + title);


                            String url = post.getString("link");
                            String author = post.getString("author");
                            String content = post.getString("content");
                            Log.w("DoFeed_RSS","Content : "+content);

                            String finalimgurl = null;
                            try
                            {
                                String[] getimg1 = content.split("<img src=\"");
                                Log.w("DoFeed_RSS","Image URL try : " + getimg1[0] + getimg1[1]);

                                String[] getimg2 = getimg1[1].split("\"");
                                Log.w("DoFeed_RSS","Image URL try : " + getimg2[0] + getimg2[1]);

                                finalimgurl = getimg2[0];
                                Log.d("DoFeed_RSS","Image URL try : " + finalimgurl);

                            }
                            catch(Exception e)
                            {
                                Log.e("DoFeed_RSS","Image URL try 1 error : " + e);

                                try
                                {

                                    JSONArray mediagroups =  post.getJSONArray("mediaGroups");
                                    JSONObject contents =  mediagroups.getJSONObject(0);
                                    JSONArray imageurlb =  contents.getJSONArray("contents");
                                    JSONObject imageurln =  imageurlb.getJSONObject(0);
                                    String imageurl =  imageurln.getString("url");

                                    finalimgurl = imageurl.toString();
                                    Log.d("DoFeed_RSS","Image URL try 2 : " + finalimgurl);


                                }
                                catch (Exception e2)
                                {
                                    Log.e("DoFeed_RSS","Image URL try 2 error : " + e2);

                                }

                            }

                            //String content = post.getString("content");

                            if (finalimgurl != null)
                            {
                                FluxItem df = new FluxItem(type ,date,title,finalimgurl,url,"",null,content_snippet,content,null);
                                df.setSourceName(sourceName);
                                FeedItemList.add(df);
                            }
                            else
                            {
                                FluxItem df = new FluxItem(type ,date,title,"",url,"",null,content_snippet,content,null);
                                df.setSourceName(sourceName);
                                FeedItemList.add(df);

                            }
                            Log.d("FluxItem","Title : "+title +"  Link : "+url+"  Author : "+author + "  PublishedDate : "+ publishedDate);
                        }
                    }
                    else
                    {
                        return 1;
                    }



                }










			} catch (JSONException e) {
			return 1;
			}
	    	

	    	return 0;
	    }
}
