package com.dogaozkaraca.bitpops.Settings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dogaozkaraca.bitpops.R;
import com.dogaozkaraca.bitpops.Utils.ColorScheme;
import com.dogaozkaraca.bitpops.Utils.WebBrowse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterLogin extends ActionBarActivity {

	TextView UserNameTV;
	Twitter twitter;
	Button buttonConnectToTwitter;
    Button buttonDisconnectFromTwitter;
	String UserNameFromTwitter;
	String NameFromTwitter;
	String UserIVfromTwitter;
	String UserBGfromTwitter;
	RequestToken requestToken;
	String PREFS_D = "twitterToken";
	String PREFS_DE = "twittersecretToken";
	SharedPreferences settings;
	SharedPreferences settings3;


	public final static String consumerKey = "o8ufWqpujqTcRKutJdxwA"; // "key here";
	public final static String consumerSecret = "bqrKOX1sgXlHXp7mttme78BZxxfy3NV38avF6t1YrQ"; // "secret key here";
	private final String CALLBACKURL = "dolauncher:///";  //Callback URL that tells the WebView to load this activity when it finishes with twitter.com. (see manifest)

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			// Respond to the action bar's Up/Home button
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//SpannableString s = new SpannableString(getSupportActionBar().getTitle());
		//s.setSpan(new TypefaceSpanRo(this, "fonts/MainClockFont.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		//s.setSpan(new ForegroundColorSpan(ColorScheme.getTextColor(this)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		//getSupportActionBar().setTitle(s);
		setContentView(R.layout.do_twitter_activity);

		android.support.v7.app.ActionBar ac = getSupportActionBar();

		ac.setBackgroundDrawable(new ColorDrawable(ColorScheme.getActionBarColor(this)));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(ColorScheme.getStatusBarColor(this));
		}
        RelativeLayout tipRL = (RelativeLayout) findViewById(R.id.tiprl);
        tipRL.setBackgroundColor(ColorScheme.getBackgroundColor(this));
        TextView tipText = (TextView) findViewById(R.id.textView13);
        tipText.setTextColor(ColorScheme.getTextColor(this));
        TextView tipTextTitle = (TextView) findViewById(R.id.textView1);
        tipTextTitle.setTextColor(ColorScheme.getTextColor(this));
		UserNameTV = (TextView) findViewById(R.id.textView15);
		buttonConnectToTwitter = (Button) findViewById(R.id.button1);
        buttonDisconnectFromTwitter = (Button) findViewById(R.id.button3);



		buttonConnectToTwitter.setBackgroundColor(ColorScheme.getBackgroundColor(this));
		buttonConnectToTwitter.setTextColor(ColorScheme.getTextColor(this));
		buttonDisconnectFromTwitter.setBackgroundColor(ColorScheme.getBackgroundColor(this));
		buttonDisconnectFromTwitter.setTextColor(ColorScheme.getTextColor(this));

 	  	settings = getSharedPreferences(PREFS_D, 0);
 		
 		String twitterToken =  settings.getString(PREFS_D, null);
 		
 		settings3 = getSharedPreferences(PREFS_DE, 0);
 		
 		String twittersecretToken =  settings.getString(PREFS_DE, null);
 	  
	     	if (twitterToken != null || twittersecretToken !=null)
 	   	{
	     		TwitterDOAuth();
            UserNameTV.setText("You have successfully connected!\n" +
                    "You can enable twitter for your news feed now.");
            buttonDisconnectFromTwitter.setVisibility(View.VISIBLE);
            buttonConnectToTwitter.setVisibility(View.GONE);
 	   	}
	     	else if (twitterToken == null || twittersecretToken ==null)
	     	{

				UserNameTV.setText("You're not logged into Twitter!");
                buttonDisconnectFromTwitter.setVisibility(View.GONE);
                buttonConnectToTwitter.setVisibility(View.VISIBLE);
	     	}
	   	
	}

	public void getPerm(View v)
	{
		try {
			OAuthLogin();
		}
		catch (Exception e)
		{

			AlertDialog.Builder builder = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
			builder.setTitle("Connection Error");
			builder.setMessage("We can't connect to Twitter. Your internet connection or Twitter servers can cause connection problems.\nThis problem can be temporary,Please try again later.");
			builder.setCancelable(false)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							//dialog.cancel();

						}
					});

			AlertDialog alertdialog = builder.create();
			alertdialog.show();
		}
	}
	
	
	
	/*
	 * - Creates object of Twitter and sets consumerKey and consumerSecret
	 * - Prepares the URL accordingly and opens the WebView for the user to provide sign-in details
	 * - When user finishes signing-in, WebView opens your activity back
	 */
	void OAuthLogin() {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		
		new AsyncTask<String,String,String>(){
			   final ProgressDialog ringProgressDialog = ProgressDialog.show(TwitterLogin.this, "Please wait ...", "Connecting to Twitter..", true);

			@Override
			protected void onPreExecute()
			{
				 
				           ringProgressDialog.setCancelable(true);

			}
			  @Override
		    	  protected String doInBackground(String... args) {
				  String authUrl = null;
			try {
				requestToken = twitter.getOAuthRequestToken(CALLBACKURL);
				authUrl= requestToken.getAuthenticationURL();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		
		return authUrl;
			  }
			  @Override
			  protected void onPostExecute(String result) {
				  ringProgressDialog.dismiss();

				  Intent i=new Intent(TwitterLogin.this,WebBrowse.class);
				  i.putExtra("urlofnews", Uri
	                        .parse(result).toString());
				  TwitterLogin.this.startActivity(i);
				  
			  }

		  }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		

	}
	/*
	 * Twitter Logout
	 * Do Launcher
	 * Twitter Integration
	 */
	
	public void TwitterDOLogout(View c)
	{
		CookieSyncManager.createInstance(this);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.removeSessionCookie();
         // gets Twitter instance with default credentials
		SharedPreferences mSharedPreferences = getSharedPreferences(PREFS_D, MODE_PRIVATE);

		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.remove(PREFS_D);
		editor.commit();
		SharedPreferences mSharedPreferences2 = getSharedPreferences(PREFS_DE, MODE_PRIVATE);

		SharedPreferences.Editor editor4 = mSharedPreferences2.edit();
		editor4.remove(PREFS_DE);
		editor4.commit();
	      
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

		twitterStream.shutdown();
		twitterStream.cleanUp();
//		  Intent i=new Intent(TwitterLogin.this,WebBrowse.class);
//		  i.putExtra("urlofnews", "http://twitter.com/logout");
//		  TwitterLogin.this.startActivity(i);
		  final SharedPreferences settings =getSharedPreferences("DONetworks" , 0);
			SharedPreferences.Editor editor3 = settings.edit();
			    
				 	 editor3.putBoolean("SN_twitter",false);
				 	  editor3.commit();

		finish();
	}

	/*
	 * Twitter Get Identity
	 * Do Launcher
	 * Twitter Integration
	 * 
	 * 
	 */
	
	void TwitterDOAuth() {
		final ConfigurationBuilder cb = new ConfigurationBuilder();
settings = getSharedPreferences(PREFS_D, 0);
		
		String twitterToken =  settings.getString(PREFS_D, null);
		settings3 = getSharedPreferences(PREFS_DE, 0);
		
		String twittersecretToken =  settings3.getString(PREFS_DE, null);
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey("o8ufWqpujqTcRKutJdxwA")
		  .setOAuthConsumerSecret("bqrKOX1sgXlHXp7mttme78BZxxfy3NV38avF6t1YrQ")
		  .setOAuthAccessToken(twitterToken)
		  .setOAuthAccessTokenSecret(twittersecretToken);
		
		new AsyncTask<String,String,String>(){
			   final ProgressDialog ringProgressDialog = ProgressDialog.show(TwitterLogin.this, "Please wait ...", "Connecting to Twitter..", true);

			@Override
			protected void onPreExecute()
			{
				 
				           ringProgressDialog.setCancelable(false);

			}
			  @Override
		    	  protected String doInBackground(String... args) {
     	   Log.w("DO-Twitter","ASYNC");
     	
     	
         TwitterFactory factory = new TwitterFactory(cb.build());
         // gets Twitter instance with default credentials

	       Twitter twitter = factory.getInstance();
     
         try {
			User user = twitter.verifyCredentials();
	
         
       
           UserNameFromTwitter = twitter.getScreenName();
			User user22 = twitter.showUser(UserNameFromTwitter);

           NameFromTwitter = user22.getName();
          UserIVfromTwitter = user22.getProfileImageURL();
          UserBGfromTwitter = user22.getProfileBannerMobileURL();
     	} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         catch (Exception er)
         {
    		 String PREFS_FEED = "twitterToken";
	      	   SharedPreferences settings = getSharedPreferences(PREFS_FEED, 0);
	      	      SharedPreferences.Editor editor = settings.edit();
	      	      editor.putString(PREFS_FEED,null);

	      	      // Commit the edits!
	      	      editor.commit();
	      	      
	     		 String PREFS_FEED2 = "twittersecretToken";
	        	   SharedPreferences settings2 = getSharedPreferences(PREFS_FEED2, 0);
	        	      SharedPreferences.Editor editor3 = settings2.edit();
	        	      editor3.putString(PREFS_FEED,null);

	        	      // Commit the edits!
	        	      editor3.commit(); 
         }



             
             
         
         Log.w("DO-Twitter","adapter");


    

		
		return null;
			  }
			  @Override
			  protected void onPostExecute(String result) {
				  ringProgressDialog.dismiss();
				  if (UserNameFromTwitter !=null)
				  {


                          UserNameTV.setText("You're logged in as "+NameFromTwitter+" (@"+UserNameFromTwitter+")"  +
                                  "!");
                          buttonDisconnectFromTwitter.setVisibility(View.VISIBLE);
                          buttonConnectToTwitter.setVisibility(View.GONE);

			    //  Picasso.with(TwitterLogin.this).load(UserIVfromTwitter).into(UserIV);
			      
			    //  new AsyncTask<Object, Object, Object>() {
			    //	    private Bitmap pic;

			    	   
			    //	    @Override
			    //	    protected Object doInBackground(final Object... objects) {
			    //	        try {
			    //	            final URL url = new URL(UserBGfromTwitter);
			    //	            final URLConnection conn = url.openConnection();
			    //	            pic = BitmapFactory.decodeStream(conn.getInputStream());
			    //	        } catch (final Exception ex) {
			    //	        }
			    //	        return null;
			    //	    }

			    //	    @Override
			    //	    protected void onPostExecute(final Object o) {
			    //	    	  RL.setBackground(new BitmapDrawable(pic));
			    	    	  
			    //	    }
			    //	}.execute();
			    
			    
				  }
				  else
				  {



                          UserNameTV.setText("There is an error occurred and\nYou're not logged into Twitter!");
                          buttonDisconnectFromTwitter.setVisibility(View.GONE);
                          buttonConnectToTwitter.setVisibility(View.VISIBLE);

				  }
				  
			  }

		  }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		

	}


	/*
	 * - Called when WebView calls your activity back.(This happens when the user has finished signing in)
	 * - Extracts the verifier from the URI received
	 * - Extracts the token and secret from the URL 
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
	final Uri uri = Uri.parse(intent.getStringExtra("url"));

		if (intent.getStringExtra("url").startsWith(CALLBACKURL))
		 {

	      new AsyncTask<Object, Object, Object>() {

			   final ProgressDialog ringProgressDialog = ProgressDialog.show(TwitterLogin.this, "Please wait ...", "Getting permission...", true);

			   @Override
			   protected void onPreExecute()
			   {
		           ringProgressDialog.setCancelable(false);

			   }
	    	    @Override
	    	    protected Object doInBackground(final Object... objects) {
	    			try {
	    				String verifier = uri.getQueryParameter("oauth_verifier");
	    				Log.e("Do-Verifier",verifier);
	    				String outhtoken = uri.getQueryParameter("oauth_token");
	    				Log.e("Do-outhtoken",outhtoken);
	    				AccessToken accessToken = twitter.getOAuthAccessToken(requestToken,
	    						verifier);
	    		
	    				String token = accessToken.getToken(), secret = accessToken
	    						.getTokenSecret();
	    				Log.e("Do-accesstoken",token);
	    				Log.e("Do-accesstokensecret",secret);

	    				 String PREFS_FEED = "twitterToken";
	    	      	   SharedPreferences settings = getSharedPreferences(PREFS_FEED, 0);
	    	      	      SharedPreferences.Editor editor = settings.edit();
	    	      	      editor.putString(PREFS_FEED,token);

	    	      	      // Commit the edits!
	    	      	      editor.commit();
	    	      	      
	    	        	   SharedPreferences settings2232 = getSharedPreferences(PREFS_DE, 0);
	    	        	      SharedPreferences.Editor editor333 = settings2232.edit();
	    	        	      editor333.putString(PREFS_DE,secret);

	    	        	      // Commit the edits!
	    	        	      editor333.commit(); 
	    	        	      
	    	        	 
	    				 //after everything, display the first tweet 

	    			} catch (TwitterException ex) {
	    				Log.e("DO-error", "" + ex.getMessage());
	    			}
	    			catch (Exception er)
	    			{
	    				Log.e("DO-error", "" + er.getMessage());

	    			}
	    	    	
	    	        return null;
	    	    }

	    	    @Override
	    	    protected void onPostExecute(final Object o) {
	    	    	  ringProgressDialog.dismiss();
	        	  	settings = getSharedPreferences(PREFS_D, 0);
	        		
	        		String twitterToken =  settings.getString(PREFS_D, null);
	        		
	        		settings3 = getSharedPreferences(PREFS_DE, 0);
	        		
	        		String twittersecretToken =  settings3.getString(PREFS_DE, null);
	        	  Log.e("DO-TWITTER",twitterToken+ "_--------------_" + twittersecretToken);
	    	     	if (twitterToken == null || twittersecretToken ==null)
	        	   	{
                        UserNameTV.setText("There is an error occurred and\nYou're not logged into Twitter!");
                        buttonDisconnectFromTwitter.setVisibility(View.GONE);
                        buttonConnectToTwitter.setVisibility(View.VISIBLE);
	        	
	        	   	}
	        	   	else
	        	   	{
                       TwitterDOAuth();
	        	   	}
	    	    }
	    	}.execute();

	}

	}


}