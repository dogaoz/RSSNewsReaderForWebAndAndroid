package com.dogaozkaraca.bitpops.Utils;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dogaozkaraca.bitpops.R;
import com.dogaozkaraca.bitpops.Settings.TwitterLogin;

public class WebBrowse extends Activity {
	String stringfromintent;
	String headlineArticle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_browser);

        WebView webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
    //    webView.setWebViewClient(new CustomWebViewClient());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	
           stringfromintent = extras.getString("urlofnews");
            Log.d("DO","Successfully sent extraintent "+stringfromintent);
        }
        else
        {
        	Log.e("DO","intent lost");
        }
     
		webView.loadUrl(stringfromintent);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String APP_SCHEME = "dolauncher";
                if (url.startsWith(APP_SCHEME)) {

                    Intent myIntent = new Intent(getBaseContext(), TwitterLogin.class);
                    myIntent.putExtra("url",url);
                    startActivity(myIntent);
                    return true;
                }
                return false;
            }
        });
	}
	






}
