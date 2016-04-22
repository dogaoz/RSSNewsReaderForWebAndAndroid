package com.dogaozkaraca.bitpops.loadFeed;

import android.util.Log;

import com.dogaozkaraca.bitpops.AdapterItems.FluxItem;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;


public class loadTwitter {

	public static Twitter twitter;
	public static int loadTwitter(String type, String[] TimeLine , String[] PostTitle , String[] ImageURL , String[] PostURL, List<FluxItem> FeedItemList, ConfigurationBuilder twitterConnector)
    {
    	  
		try
		{
			TwitterFactory factory = new TwitterFactory(twitterConnector.build());
			// gets Twitter instance with default credentials
			twitter = factory.getInstance();
	         
	        	
User user = twitter.verifyCredentials();

	            Paging page = new Paging(1, 110);//page number, number per page
	            ResponseList<Status> statuses = twitter.getHomeTimeline(page);
	     
	            int n = 0;

			for (twitter4j.Status status : statuses) {
	            
	            URLEntity[] uent = status.getURLEntities();
	                
	            if (n < 95)
	            {
	            	String TwUserName = "";
	            	String TwPost = "";
	            	String TwPostImageUrl = "http://";
	            	String TwPostUrl = "";
	            	String TwFavCount = "";
	            	
	            	TwUserName = "@" + status.getUser().getScreenName();
	            	TwPost = status.getText();
	            	Date TwTimePosted = status.getCreatedAt();
	            	MediaEntity[] mediaEntities = status.getMediaEntities();
	            	String mediaUrl ;
	            	try
	            	{
	            	mediaUrl = mediaEntities[0].getMediaURL();
	            	Log.w("DOT",mediaUrl);
	            	}
	            	catch(Exception e)
	            	{
	            		mediaUrl=null;
		            	Log.w("DOT","error");

	            	}
	         //  Example Twitter Post Time : Fri Aug 08 14:57:02 EEST 2014
	       	SimpleDateFormat  format = new SimpleDateFormat("ddd MMM dd HH:mm:ss Z yyyy",Locale.US);  
				 
//			    Date date = null;
//				try {
//					date = format.parse(TwTimePosted.toString());
//				} catch (ParseException e) {
//					Log.e("DoDate","Twitter Date parse error : "+ e);
//				
//				}  
	            Log.w("DoFeed_Twitter","Twitter Post Time Parsed :" + TwTimePosted);

	            	TwPostImageUrl = status.getUser().getOriginalProfileImageURL();

	                StringBuffer address = new StringBuffer();
	                address.append("http://twitter.com/#!/");
	                address.append( status.getUser().getScreenName());
	                address.append("/status/");
	                address.append(status.getId());

	                TwPostUrl = address.toString();

	                String TwRTCount = status.getRetweetCount() + "";

					TwFavCount = status.getFavoriteCount() + "";
					// TwitterPostReplyUrl[n]
					Log.w("DoFeed_Twitter", "Twitter Username :" + TwUserName);
					Log.w("DoFeed_Twitter", "Twitter Post :" + TwPost);
					FluxItem df = new FluxItem(type ,TwTimePosted,TwPost,TwPostImageUrl,TwPostUrl,status.getId()+"",mediaUrl,TwFavCount, TwRTCount,status.isFavorited());
                    df.setSourceName(TwUserName);
					df.setIfRetweeted(status.isRetweeted());

				FeedItemList.add(df);

	                n++;
	            }

	        
	                
	                
	            }
	          

		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 1;
		}



    	return 0;
    }

}
