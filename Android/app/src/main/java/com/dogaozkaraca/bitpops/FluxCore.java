package com.dogaozkaraca.bitpops;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dogaozkaraca.bitpops.AdapterItems.FluxItem;
import com.dogaozkaraca.bitpops.Adapters.NewsAdapter;
import com.dogaozkaraca.bitpops.Utils.ColorScheme;
import com.dogaozkaraca.bitpops.instagram.InstagramApp;
import com.dogaozkaraca.bitpops.loadFeed.loadInstagram;
import com.dogaozkaraca.bitpops.loadFeed.loadRSS;
import com.dogaozkaraca.bitpops.loadFeed.loadTwitter;
import com.yalantis.phoenix.PullToRefreshView;
/*
import com.dogaozkaraca.flux.loadFeed.loadFacebook;
import com.dogaozkaraca.flux.loadFeed.loadFlickr;
import com.dogaozkaraca.flux.loadFeed.loadFoursquare;
import com.dogaozkaraca.flux.loadFeed.loadInstagram;
import com.dogaozkaraca.flux.loadFeed.loadLinkedIn;

import com.dogaozkaraca.flux.loadFeed.loadTumblr;
import com.dogaozkaraca.flux.loadFeed.loadTwitter;
import com.facebook.AccessToken;
import instagram.InstagramApp;
import twitter4j.conf.ConfigurationBuilder;
*/
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import twitter4j.conf.ConfigurationBuilder;


/**
 * Created by doga.ozkaraca on 4/4/2015.
 */

public class FluxCore {

    public static Activity activity;
    public static boolean isDemoActive = false;
    public static GridView feed;
    static String FACEBOOK_APPID;
    static String FACEBOOK_PERMISSION;
    static ConfigurationBuilder twitterConnector ;
    static InstagramApp instagramConnector;
    public static  int isInDoCenter = 0;
    public static int isFeedRefreshing = 0;
    public static int isDoCenterAnimating = 0;
    public static RelativeLayout DoFeed_content;
    public static RelativeLayout DoCenter_content;
    public static RelativeLayout insideDoCenter_content;

    public static PullToRefreshView pullToRefreshView;
    public static GridView offlineFeed;
    public static TextView savedArticlesText;
    public static View savedArticlesLine;
    public static View revealView;

    public static NewsAdapter newsAdapterPublic;
    public static List<FluxItem> rssitems[] = new ArrayList[50];
    static int howmuchrssitems =0;
    public static List<FluxItem> facebookitems = new ArrayList<FluxItem>();
    public static List<FluxItem> twitteritems = new ArrayList<FluxItem>();
    public static List<FluxItem> instagramitems = new ArrayList<FluxItem>();

    public static List<FluxItem> flickritems = new ArrayList<FluxItem>();
    public static List<FluxItem> tumblritems = new ArrayList<FluxItem>();
    public static List<FluxItem> linkedinitems = new ArrayList<FluxItem>();
    public static List<FluxItem> foursquareitems = new ArrayList<FluxItem>();


    public static void reloadOfflineView()
    {
        //OFFLINE ARTICLES
            List<FluxItem> OfflineFeedItemList = new ArrayList<FluxItem>();



            String[] titleList = loadArrayOffline("title", activity);
         //   String[] contentList = loadArrayOffline("article", activity);

            String[] imageurlList = loadArrayOffline("postimage", activity);

            String[] urlList = loadArrayOffline("url", activity);
            for (int x=0;x<titleList.length;x++) {

                Log.w("offlinefeedadded", titleList[x] +  imageurlList[x] + urlList[x]);
           OfflineFeedItemList.add(new FluxItem("rss", null, titleList[x], imageurlList[x], urlList[x], null, null, null, null, false));


            }
          //  offlineFeed.setAdapter(new OfflineNewsAdapter(activity, OfflineFeedItemList));


    }

    public static String[] loadArrayOffline(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("Rotary_RSS_saved", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }

    public static void refreshTheFeed()
    {

        pullToRefreshView.setRefreshing(true);



        try {
            if (isFeedRefreshing == 0) {
                if (isNetworkAvailable()) {
                    // feed.resetScroll();
                    FeedLoader loadfeed = new FeedLoader();

                    final SharedPreferences settings = activity.getSharedPreferences("DONetworks", 0);

                    final Boolean rss = settings.getBoolean("SN_rss", true);

                    Boolean fb = false;
                    Boolean tw = false;
                    Boolean insta = false;

                        fb = settings.getBoolean("SN_facebook", false);
                        tw = settings.getBoolean("SN_twitter", false);
                        insta = settings.getBoolean("SN_instagram", false);


                    Boolean flickr = false;
                    Boolean tumblr = false;
                    Boolean linkedin = false;
                    Boolean foursquare = false;

                        flickr = settings.getBoolean("SN_flickr", false);
                        tumblr = settings.getBoolean("SN_tumblr", false);
                        linkedin = settings.getBoolean("SN_linkedin", false);
                        foursquare = settings.getBoolean("SN_foursquare", false);

                    //Placement		 RSS Feed,   Facebook ,Twitter ,Instagram,Flickr, Tumblr,LinkedIn, Foursquare

                    loadfeed.execute(rss.toString(), fb.toString(), tw.toString(), insta.toString(), flickr.toString(), tumblr.toString(), linkedin.toString(), foursquare.toString());
                }
                else
                {
                    FluxMain.showMessage("No Network Connection!");
                }
            }

        }
        catch(Exception e){}

    }

    public static void reloadVariables()
    {

        try
        {
            insideDoCenter_content.setBackgroundColor(ColorScheme.getBackgroundColor(activity));
            savedArticlesText.setTextColor(ColorScheme.getTextColor(activity));
            savedArticlesLine.setBackgroundColor(ColorScheme.getTextColor(activity));
            revealView.setBackgroundColor(ColorScheme.getBackgroundColor(activity));

           // SharedPreferences settings = activity.getSharedPreferences("SettingsMore", 0);
           // String getItemSize = settings.getString("itemSize", "normal");


        }
        catch(Exception e)
        {

        }

    }
    public static void load(final Activity mActivity ,PullToRefreshView mPullToRefreshView,GridView mFeed)
    {

        feed = mFeed;
        activity = mActivity;

        pullToRefreshView = mPullToRefreshView;
        pullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshTheFeed();
                    }
                }, 2000);
            }
        });

        feed.setNumColumns(2);


        reloadOfflineView();
        refreshTheFeed();
        reloadVariables();


        final int[] cx = new int[1];
        final int[] cy = new int[1];
        //revealView = customScreen.findViewById(R.id.reveal_viewD);
        final int[] location = new int[2];
       // revealView.setBackgroundColor(ColorScheme.getBackgroundColor(activity));
/*
        circleButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {


                circleButton.getLocationOnScreen(location);

                cx[0] = (location[0] + (circleButton.getWidth() / 2));
                cy[0] = location[1] + (GUIUtils.getStatusBarHeight(activity) / 2);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                            if (isDoCenterAnimating != 1) {
                                if (isInDoCenter == 0) {
                                    // Show the panel
                                    isDoCenterAnimating=1;
                                    DoCenter_content.setVisibility(View.VISIBLE);
                                    GUIUtils.showRevealEffect(revealView, cx[0], cy[0], revealAnimationListener);

                                } else {
                                    // Hide the Panel
                                    isDoCenterAnimating =1;
                                    Animation aa = fadeOutDO(200);
                                    insideDoCenter_content.startAnimation(aa);
                                    aa.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {
                                            insideDoCenter_content.setVisibility(View.VISIBLE);

                                        }

                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            insideDoCenter_content.setVisibility(View.GONE);
                                            GUIUtils.hideRevealEffectDoCenter(revealView, cx[0], cy[0], 1920, revealHideAnimationListener);

                                        }

                                        @Override
                                        public void onAnimationRepeat(Animation animation) {
                                        }
                                    });


                                }

                            }
                        }
                        else
                            {

                            if (isInDoCenter == 0) {
                                // Show the panel
                                DoCenter_content.setVisibility(View.VISIBLE);
                                Animation bottomUp = AnimationUtils.loadAnimation(activity,
                                        R.anim.bottom_up);

                                bottomUp.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {
                                        revealView.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        Animation aa = fadeInDO(200);
                                        aa.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                insideDoCenter_content.setVisibility(View.VISIBLE);
                                                isInDoCenter = 1;

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {
                                            }
                                        });
                                        insideDoCenter_content.startAnimation(aa);

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {

                                    }
                                });

                                DoCenter_content.startAnimation(bottomUp);


                            } else {
                                // Hide the Panel
                                final Animation bottomDown = AnimationUtils.loadAnimation(activity,
                                        R.anim.bottom_down);
                                Animation aa = fadeOutDO(200);
                                aa.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animation animation) {
                                        bottomDown.setAnimationListener(new Animation.AnimationListener() {
                                            @Override
                                            public void onAnimationStart(Animation animation) {
                                                insideDoCenter_content.setVisibility(View.INVISIBLE);

                                            }

                                            @Override
                                            public void onAnimationEnd(Animation animation) {
                                                isInDoCenter = 0;
                                                DoCenter_content.setVisibility(View.GONE);

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animation animation) {

                                            }
                                        });
                                        DoCenter_content.startAnimation(bottomDown);


                                    }

                                    @Override
                                    public void onAnimationRepeat(Animation animation) {
                                    }
                                });
                                insideDoCenter_content.startAnimation(aa);

                            }
                        }

            }

        });

*/
       /* circleButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                feed.setSelection(0);
                return true;
            }
        });
*/









    }
    public static Animator.AnimatorListener revealAnimationListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {

            Animation aa = fadeInDO(200);
            insideDoCenter_content.startAnimation(aa);
            aa.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    insideDoCenter_content.setVisibility(View.VISIBLE);

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    isInDoCenter = 1;
                    isDoCenterAnimating=0;

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    };
    public static Animator.AnimatorListener revealHideAnimationListener = new Animator.AnimatorListener() {

        @Override
        public void onAnimationStart(Animator animation) {



        }

        @Override
        public void onAnimationEnd(Animator animation) {

            DoCenter_content.setVisibility(View.GONE);
            isInDoCenter = 0;
            isDoCenterAnimating=0;

        }

        @Override
        public void onAnimationCancel(Animator animation) {}

        @Override
        public void onAnimationRepeat(Animator animation) {}
    };
    public static class FeedLoader extends AsyncTask<String, String, List<FluxItem>> {
        // XXX: Load Feed

        List<FluxItem> FeedItemList = new ArrayList<FluxItem>();

        @Override
        protected List<FluxItem> doInBackground(String... params) {
            publishProgress("UPDATINGFEED"); // Calls onProgressUpdate()

            //Initialize Adapter List

            String TimeLine[] = new String[1000];
            String PostTitle[] = new String[1000];
            String ImageURL[] = new String[1000];
            String PostURL[] = new String[1000];
            if (params[0].equals("true")) //RSS
            {
                publishProgress("RSSLoading");




                //Load
                String[] titleList = loadArray("title", activity);
                String[] urlList = loadArray("url", activity);
                howmuchrssitems=titleList.length;
                rssitems = new ArrayList[titleList.length];

                for (int x=0;x<titleList.length;x++) {
                    try {
                        rssitems[x] = new ArrayList<FluxItem>();

                        int loaded_wo_problem;
                        try {
                            loaded_wo_problem = loadRSS.loadRSS("rss", urlList[x], rssitems[x], titleList[x]);
                        } catch (Exception e) {
                            loaded_wo_problem = 1;
                        }
                        if (loaded_wo_problem == 0) {
                            Log.i("DoFeed", "RSS_Loaded Successfully");
                        } else {
                            Log.e("DoFeed", "RSS_Loading Failed");
                            publishProgress("RSSFAILED");
                        }
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                        Log.e("RotaryRSS","What The fuck is happening!");
                        Log.e("DoFeed", "RSS_Loading Failed");
                        publishProgress("RSSFAILED");
                    }
                }


            }
/*
            if (params[1].equals("true")) //Facebook
            {
                publishProgress("FacebookLoading");
                int loaded_wo_problem = loadFacebook("facebook",TimeLine,PostTitle,ImageURL,PostURL,facebookitems);
                if (loaded_wo_problem==0)
                {
                    Log.i("DoFeed","Facebook_Loaded Successfully");
                }
                else
                {
                    Log.i("DoFeed","Facebook loading failed.");

                    publishProgress("FBFAILED");

                }
            }
            */
           if (params[2].equals("true")) //Twitter
            {
                publishProgress("TwitterLoading");

                int loaded_wo_problem = loadTwitter.loadTwitter("twitter",TimeLine,PostTitle,ImageURL,PostURL,twitteritems,twitterConnector);
                if (loaded_wo_problem==0)
                {
                    Log.i("DoFeed","Twitter_Loaded Successfully");

                }
                else
                {
                    publishProgress("TWFAILED");
                }
            }

            if (params[3].equals("true")) //Instagram
            {
                publishProgress("InstagramLoading");

                int loaded_wo_problem = loadInstagram.loadInstagram("instagram", TimeLine, PostTitle, ImageURL, PostURL, instagramitems, instagramConnector);
                if (loaded_wo_problem==0)
                {
                    Log.i("DoFeed","Instagram_Loaded Successfully");
                }
                else
                {
                    publishProgress("INSTAFAILED");
                }
            }
            /*
            if (params[4].equals("true")) //Flickr
            {
                publishProgress("FlickrLoading");

                int loaded_wo_problem = loadFlickr.loadFlickr(activity, flickritems);
                if (loaded_wo_problem==0)
                {
                    Log.i("DoFeed","Flickr_Loaded Successfully");
                }
                else
                {
                    publishProgress("FLICKRFAILED");
                }
            }
            if (params[5].equals("true"))//Tumblr
            {
                publishProgress("TumblrLoading");

                int loaded_wo_problem = loadTumblr.loadTumblr(activity,tumblritems);
                if (loaded_wo_problem==0)
                {
                    Log.i("DoFeed","Tumblr_Loaded Successfully");
                }
                else
                {
                    publishProgress("TUMBLRFAILED");
                }

            }
            if (params[6].equals("true"))//LinkedIn
            {
                publishProgress("LinkedInLoading");

                int loaded_wo_problem = loadLinkedIn.loadLinkedIn(activity,linkedinitems);
                if (loaded_wo_problem==0)
                {
                    Log.i("DoFeed","Linkedin_Loaded Successfully");
                }
                else
                {
                    publishProgress("LINKEDINFAILED");
                }

            }
            if (params[7].equals("true"))//Foursquare
            {
                publishProgress("FoursquareLoading");

                int loaded_wo_problem = loadFoursquare.loadFoursquare(activity,foursquareitems);
                if (loaded_wo_problem==0)
                {
                    Log.i("DoFeed", "Foursquare_Loaded Successfully");
                }
                else if (loaded_wo_problem==2)
                {
                    publishProgress("FoursquareAG");



                }
                else
                {
                    publishProgress("FOURSQUAREFAILED");
                }

            }
        */

            publishProgress("Done...");
            return FeedItemList;
        }

        @Override
        protected void onPostExecute(List<FluxItem> FeedItemList) {
//	      		Collections.sort(, new Comparator<Date>() {
//
//	    		  				@Override
//	    		  				public int compare(Date lhs, Date rhs) {
//	    		  					// TODO Auto-generated method stub
//	    		  					return 0;
//	    		  				}
//
//	    		      			});

            Collections.sort(FeedItemList, new SortFeedByDate());

            awesomeTimeline(activity, FeedItemList);


            if (isDemoActive)
                    {

                      FeedItemList = new ArrayList<FluxItem>();

                        //Posts
                        FluxItem df = new FluxItem("twitter", null, "What a beautiful day!", "photourl", "posturl", "userid", "http://rotaryhome.com/rotaryDemo/jennifer.jpg", "5", "2", true);
                        df.setSourceName("Jennifer W.");
                        FeedItemList.add(df);

                        FluxItem df4 = new FluxItem("rss", null, "How Motocross Riders Don't Die All the Time", "http://rotaryhome.com/rotaryDemo/rotarynews.jpg", "posturl", "userid", "http://rotaryhome.com/rotaryDemo/rotarynews.jpg", "0", null, null);
                        df4.setSourceName("Rotary News");
                        FeedItemList.add(df4);

                        FluxItem df2 = new FluxItem("flickr", null, "Winning is everything!", "http://rotaryhome.com/rotaryDemo/nick.jpg", "posturl", "userid", "http://rotaryhome.com/rotaryDemo/nick.jpg", "0", null, false);
                        df2.setSourceName("Nick K.");
                        FeedItemList.add(df2);

                        FluxItem df3 = new FluxItem("instagram", null, "Playing guitar today", "http://rotaryhome.com/rotaryDemo/guitar.jpg", "posturl", "userid", "http://rotaryhome.com/rotaryDemo/theman.jpg", "0", null, false);
                        df3.setSourceName("John");
                        FeedItemList.add(df3);


                    }

            ////

            newsAdapterPublic =  new NewsAdapter(activity, FeedItemList);
            feed.setAdapter(newsAdapterPublic);



            pullToRefreshView.setRefreshing(false);
            FluxMain.feedloaded();

            isFeedRefreshing = 0;

        }

        private void randomizeFeed(Activity activity, List<FluxItem> FeedItemList) {


            //Randomize the feed


            Random rnd = new Random();
            for (int i = FeedItemList.size() - 1; i >= 0; i--) {
                int index = rnd.nextInt(i + 1);
                // Simple swap
                FluxItem temp = FeedItemList.get(i);
                FluxItem temp2 = FeedItemList.get(index);
                FeedItemList.set(index,temp);
                FeedItemList.set(i,temp2);

            }

        }

        private void awesomeTimeline(Activity activity, List<FluxItem> FeedItemList) {


            //Awesomize the feed

            int addedfacebooktill=0;
            int addedtwittertill=0;
            int addedinstagramtill=0;
            int addedflickrtill=0;
            int addedtumblrtill=0;
            int addedlinkedintill=0;
            int addedfoursquaretill=0;
            int a=0;

            int howmuchitemsrssintotal =0;
            int addedRSStill[]=new int[howmuchrssitems];
            for (int k=0;k<howmuchrssitems;k++)
            {
                addedRSStill[k] =0;
                if(rssitems != null && rssitems[k] != null)
                howmuchitemsrssintotal = howmuchitemsrssintotal + rssitems[k].size();
            }
            while(a <(howmuchitemsrssintotal+facebookitems.size()+instagramitems.size()+twitteritems.size()+flickritems.size()+tumblritems.size()+linkedinitems.size()+foursquareitems.size())) {

                Random rnd = new Random();
                //Social network + rss count
                // 0 for rss
                // 1 for facebook
                // 2 for twitter
                // 3 for instagram
                // 4 for ...



                int index = rnd.nextInt(8);


                        if (index==0)
                        {   //use RSS as item
                            int rssNext;
                            if (howmuchrssitems !=0) {
                                rssNext = rnd.nextInt(howmuchrssitems);
                                try {
                                    if (addedRSStill[rssNext] < rssitems[rssNext].size()) {
                                        FeedItemList.add(rssitems[rssNext].get(addedRSStill[rssNext]));
                                        addedRSStill[rssNext]++;
                                        a++;
                                    }
                                }
                                catch(Exception e)
                                {

                                }
                            }



                        }

                        if (index==1)
                        {   //use Facebook as item
                            if (addedfacebooktill < facebookitems.size()) {

                                FeedItemList.add(facebookitems.get(addedfacebooktill));
                                addedfacebooktill++;
                                a++;
                            }

                        }

                        if (index==2)
                        {   //use Twitter as item
                            if (addedtwittertill < twitteritems.size()) {

                                FeedItemList.add(twitteritems.get(addedtwittertill));
                                addedtwittertill++;
                                a++;
                            }

                        }

                        if (index==3)
                        {   //use Instagram as item
                            if (addedinstagramtill <instagramitems.size()) {

                                FeedItemList.add(instagramitems.get(addedinstagramtill));
                                addedinstagramtill++;
                                a++;
                            }

                        }

                if (index==4)
                {   //use Flickr as item
                    if (addedflickrtill <flickritems.size()) {

                        FeedItemList.add(flickritems.get(addedflickrtill));
                        addedflickrtill++;
                        a++;
                    }

                }

                if (index==5)
                {   //use Tumblr as item
                    if (addedtumblrtill <tumblritems.size()) {

                        FeedItemList.add(tumblritems.get(addedtumblrtill));
                        addedtumblrtill++;
                        a++;
                    }

                }

                if (index==6)
                {   //use LinkedIn as item
                    if (addedlinkedintill <linkedinitems.size()) {

                        FeedItemList.add(linkedinitems.get(addedlinkedintill));
                        addedlinkedintill++;
                        a++;
                    }

                }
                if (index==7)
                {   //use Foursquare as item
                    if (addedfoursquaretill <foursquareitems.size()) {

                        FeedItemList.add(foursquareitems.get(addedfoursquaretill));
                        addedfoursquaretill++;
                        a++;
                    }

                }






            }



        }

        @Override
        protected void onPreExecute()
        {
            isFeedRefreshing = 1;
            //Facebook Stream Creator by doga.ozkaraca
            FACEBOOK_APPID = "309052015940593";
            FACEBOOK_PERMISSION = "read_stream";

            rssitems = null;
            facebookitems = null;
            facebookitems = new ArrayList<FluxItem>();
            twitteritems = null;
            twitteritems = new ArrayList<FluxItem>();
            instagramitems = null;
            instagramitems = new ArrayList<FluxItem>();

                pullToRefreshView.setRefreshing(true);

            FluxMain.showMessage("Loading...");

            //Twitter Stream Creator by doga.ozkaraca
            SharedPreferences settings = activity.getSharedPreferences("twitterToken", 0);
            String twitterToken =  settings.getString("twitterToken", null);
            SharedPreferences settings3 = activity.getSharedPreferences("twittersecretToken", 0);
            String twittersecretToken =  settings3.getString("twittersecretToken", null);
            twitterConnector = new ConfigurationBuilder();
            twitterConnector.setDebugEnabled(true)
                    .setOAuthConsumerKey("o8ufWqpujqTcRKutJdxwA")
                    .setOAuthConsumerSecret("bqrKOX1sgXlHXp7mttme78BZxxfy3NV38avF6t1YrQ")
                    .setOAuthAccessToken(twitterToken)
                    .setOAuthAccessTokenSecret(twittersecretToken);

            //Instagram Stream Creator by doga.ozkaraca
            String INSTACLIENT_ID = "cf6a80940a984aa1b94d90fd3a550fc5";
            String INSTACLIENT_SECRET = "23895aa86cb6447ead6643e061173057";
            String INSTACALLBACK_URL = "instado://a";
            instagramConnector = new InstagramApp(activity,
                    INSTACLIENT_ID,
                    INSTACLIENT_SECRET,
                    INSTACALLBACK_URL);



        }

        @Override
        protected void onProgressUpdate(String... text)
        {
            setMessages(text[0]);

        }
    }
    static void shuffleFeedButProtectTimeline(Integer[] intArr) {


    }


  /*  public static int loadFacebook(String type, String[] TimeLine ,String[] PostTitle ,String[] ImageURL ,String[] PostURL, List<FluxItem> FeedItemList ) {




        if (isLoggedIn())
        {
            loadFacebook.facebookLoadIntoItemList(FeedItemList,type,activity);

        }
        else
        {

            return 1;
        }
        return 0;
    }



    public static boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

*/

    public static class SortFeedByDate implements Comparator<FluxItem> {


        @Override
        public int compare(FluxItem lhs, FluxItem rhs) {

            //if (lhs.getTimePosted() == null || lhs.getTimePosted() == null)
//				        return 0;
//
            //return (lhs.getTimePosted().getTime() > lhs.getTimePosted().getTime() ? 1 : -1);     //descending

            try {
                Calendar c1 = Calendar.getInstance();
                c1.setTime(lhs.getTimePosted());
                c1.set(Calendar.HOUR, 0);
                c1.set(Calendar.MINUTE, 0);
                c1.set(Calendar.SECOND, 0);
                c1.set(Calendar.MILLISECOND, 0);
                Date t1 = c1.getTime();
                c1.setTime(rhs.getTimePosted());
                c1.set(Calendar.HOUR, 0);
                c1.set(Calendar.MINUTE, 0);
                c1.set(Calendar.SECOND, 0);
                c1.set(Calendar.MILLISECOND, 0);
                Date t2 = c1.getTime();

                if (t2.compareTo(t1) != 0)
                    return t2.compareTo(t1) * -1;
                else {
                    c1.setTime(lhs.getTimePosted());
                    c1.set(Calendar.YEAR, 0);
                    c1.set(Calendar.MONTH, 0);
                    c1.set(Calendar.DATE, 0);
                    t1 = c1.getTime();
                    c1.setTime(rhs.getTimePosted());
                    c1.set(Calendar.YEAR, 0);
                    c1.set(Calendar.MONTH, 0);
                    c1.set(Calendar.DATE, 0);
                    t2 = c1.getTime();
                    return t2.compareTo(t1);

                }
            }catch(Exception e)
            {
                return 0;
            }



        }
    }


    public static Animation fadeInDO(int duration)
    {
        final Animation in = new AlphaAnimation(0.0f, 1.0f);
        in.setDuration(duration);
        return in;
    }

    public static Animation fadeOutDO(int duration)
    {
        final Animation out = new AlphaAnimation(1.0f, 0.0f);
        out.setDuration(duration);
        return out;
    }

    //public static Animation slideInDO(int duration)
    //{

    //    Animation inFromLeft = new TranslateAnimation(
    //            Animation.RELATIVE_TO_PARENT, -1.0f,
    //            Animation.RELATIVE_TO_PARENT, 0.0f,
    //            Animation.RELATIVE_TO_PARENT, 0.0f,
    //            Animation.RELATIVE_TO_PARENT, 0.0f);
    //    inFromLeft.setDuration(duration);
    //    inFromLeft.setInterpolator(new AccelerateInterpolator());

    //    return inFromLeft;
    //}

    //public static Animation slideOutDO(int duration)
    //{

    //    Animation outtoRight = new TranslateAnimation(
    //            Animation.RELATIVE_TO_PARENT, 0.0f,
    //            Animation.RELATIVE_TO_PARENT, +1.0f,
    //            Animation.RELATIVE_TO_PARENT, 0.0f,
    //            Animation.RELATIVE_TO_PARENT, 0.0f);
    //    outtoRight.setDuration(duration);
    //    outtoRight.setInterpolator(new AccelerateInterpolator());
    //    return outtoRight;
    //}

    public static void setMessages(String text)
    {

        if (text.equals("UPDATINGFEED"))
        {
           // loadingText.setText("Loading...");

        }
      /*  if (text.equals("FoursquareLoading"))
        {
            Animation aa = fadeOutDO(400);
            loadingText.startAnimation(aa);
            aa.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    loadingText.setText(R.string.foursquare_feed_is_loading);
                    loadingText.startAnimation(fadeInDO(400));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

            });

        }
        if (text.equals("TumblrLoading"))
        {
            Animation aa = fadeOutDO(400);
            loadingText.startAnimation(aa);
            aa.setAnimationListener(new Animation.AnimationListener(){

                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    loadingText.setText(R.string.tumblr_feed_is_loading);
                    loadingText.startAnimation(fadeInDO(400));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}

            });

        }
        if (text.equals("FacebookLoading"))
        {
            Animation aa = fadeOutDO(400);
            loadingText.startAnimation(aa);
            aa.setAnimationListener(new Animation.AnimationListener(){

                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    loadingText.setText(R.string.facebook_feed_is_loading);
                    loadingText.startAnimation(fadeInDO(400));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}

            });

        }
        */
        if (text.equals("TwitterLoading"))
        {


        }
        if (text.equals("InstagramLoading"))
        {


        }
        /*
        if (text.equals("FlickrLoading"))
        {
            Animation aa = fadeOutDO(400);
            loadingText.startAnimation(aa);
            aa.setAnimationListener(new Animation.AnimationListener(){

                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    loadingText.setText(R.string.flickr_feed_is_loading);
                    loadingText.startAnimation(fadeInDO(400));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}

            });

        }
        if (text.equals("LinkedInLoading"))
        {
            Animation aa = fadeOutDO(400);
            loadingText.startAnimation(aa);
            aa.setAnimationListener(new Animation.AnimationListener(){

                @Override
                public void onAnimationStart(Animation animation) {}

                @Override
                public void onAnimationEnd(Animation animation) {
                    loadingText.setText(R.string.linkedin_feed_is_loading);
                    loadingText.startAnimation(fadeInDO(400));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {}

            });

        }*/
        if (text.equals("RSSLoading"))
        {


        }

        if (text.equals("RSSFAILED"))
        {
            //loadingText.setText("News Connection Failed");
            

        }
        if (text.equals("TUMBLRFAILED"))
        {
           // loadingText.setText("Tumblr Connection Failed");

        }
        if (text.equals("FLICKRFAILED"))
        {
           // loadingText.setText("Flickr Connection Failed");
        }
        if (text.equals("INSTAFAILED"))
        {
          //  loadingText.setText("Instagram Connection Failed");
        }
        if (text.equals("TWFAILED"))
        {
          //  loadingText.setText("Twitter Connection Failed");
        }
        if (text.equals("LINKEDINFAILED"))
        {
          //  loadingText.setText("LinkedIn Connection Failed");
        }
        if (text.equals("FOURSQUAREFAILED"))
        {
          //  loadingText.setText("Foursquare Connection Failed");
        }


        if (text.equals("FBFAILED"))
        {
           // loadingText.setText("Facebook Connection Failed");

        }
    }
    public static boolean  isNetworkAvailable(){

        final ConnectivityManager connMgr = (ConnectivityManager)
                activity.getSystemService(Context.CONNECTIVITY_SERVICE);

       // final android.net.NetworkInfo wifi =connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

       // final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if(activeNetworkInfo != null && activeNetworkInfo.isConnected() ){

            return true;
        }
        else
        {

            pullToRefreshView.setRefreshing(false);

            return false;
        }

    }


    public static String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("Rotary_RSS", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + "_" + i, null);
        return array;
    }
}
