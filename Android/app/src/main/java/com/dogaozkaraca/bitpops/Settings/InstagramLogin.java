package com.dogaozkaraca.bitpops.Settings;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dogaozkaraca.bitpops.R;
import com.dogaozkaraca.bitpops.Utils.ColorScheme;
import com.dogaozkaraca.bitpops.instagram.InstagramApp;


public class InstagramLogin extends ActionBarActivity {
	 public static final String INSTACLIENT_ID = "cf6a80940a984aa1b94d90fd3a550fc5";
	 public static final String INSTACLIENT_SECRET = "23895aa86cb6447ead6643e061173057";
	 public static final String INSTACALLBACK_URL = "instado://a";
	 static InstagramApp mApp;
	 Button btnConnect;
    public static Context ctx;
	static TextView tvSummary2;
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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.do_instagram_activity);
ctx = this;
		ActionBar ac = getSupportActionBar();

		ac.setBackgroundDrawable(new ColorDrawable(ColorScheme.getActionBarColor(this)));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//SpannableString s = new SpannableString(getSupportActionBar().getTitle());
		//s.setSpan(new TypefaceSpanRo(this, "fonts/MainClockFont.ttf"), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		//getSupportActionBar().setTitle(s);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(ColorScheme.getStatusBarColor(this));
		}
		 mApp = new InstagramApp(InstagramLogin.this,
    	        INSTACLIENT_ID,
    	        INSTACLIENT_SECRET,
    	        INSTACALLBACK_URL);
	btnConnect = (Button) findViewById(R.id.button1);
	tvSummary2 = (TextView) findViewById(R.id.textView2);

        RelativeLayout tipRL = (RelativeLayout) findViewById(R.id.tiprl);
        tipRL.setBackgroundColor(ColorScheme.getBackgroundColor(this));
        TextView tipText = (TextView) findViewById(R.id.textView13);
        tipText.setTextColor(ColorScheme.getTextColor(this));
        TextView tipTextTitle = (TextView) findViewById(R.id.textView1);
        tipTextTitle.setTextColor(ColorScheme.getTextColor(this));
        btnConnect.setBackgroundColor(ColorScheme.getBackgroundColor(this));
        btnConnect.setTextColor(ColorScheme.getTextColor(this));
	
	if (mApp.hasAccessToken()) {
		btnConnect.setText("Disconnect from instagram");
		tvSummary2.setText(" You're logged in as "+ mApp.getName() + "\n( " + mApp.getUserName()+" )");
		
		Log.w("DO-Instagram"," Name :"+ mApp.getName() + "Username:" + mApp.getUserName() );
	}
	else
	{
		tvSummary2.setText("You're not logged into instagram!");
		btnConnect.setText("Connect to Instagram");


	}
	
	}

	
	public void instalogin(View v)
	{
		if (mApp.hasAccessToken()) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(
					InstagramLogin.this);
			builder.setMessage("Disconnect from Instagram?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialog, int id) {
									mApp.resetAccessToken();
									btnConnect.setText("Connect to Instagram");
									tvSummary2.setText("You have logged out from Instagram !");

						     	
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface dialog, int id) {
									dialog.cancel();
								}
							});
			final AlertDialog alert = builder.create();
			alert.show();
		} else {
			mApp = new InstagramApp(InstagramLogin.this,
					INSTACLIENT_ID,
					INSTACLIENT_SECRET,
					INSTACALLBACK_URL);
			mApp.authorize();
			btnConnect.setText("Disconnect from instagram");
			  final SharedPreferences settings =getSharedPreferences("DONetworks" , 0);
				SharedPreferences.Editor editor3 = settings.edit();
				    
					 	 editor3.putBoolean("SN_instagram", false);
					 	  editor3.commit();





		}
	}


    public static void icompleted()
    {
		mApp = new InstagramApp(ctx,
				INSTACLIENT_ID,
				INSTACLIENT_SECRET,
				INSTACALLBACK_URL);
        tvSummary2.setText(" You're logged in as " + mApp.getName() + "\n( " + mApp.getUserName() + " )\n" +
                "You can enable instagram for your news feed now.");
    }
}
