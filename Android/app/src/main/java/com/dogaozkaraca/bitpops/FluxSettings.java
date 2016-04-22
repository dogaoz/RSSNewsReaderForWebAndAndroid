package com.dogaozkaraca.bitpops;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dogaozkaraca.bitpops.Settings.InstagramLogin;
import com.dogaozkaraca.bitpops.Settings.RssSettings;
import com.dogaozkaraca.bitpops.Settings.TwitterLogin;

public class FluxSettings extends AppCompatActivity
{

    boolean isTwitterConnected = false;
    boolean isInstagramConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        TextView twConnect = (TextView) findViewById(R.id.textViewTwConnect);
        TextView instaConnect = (TextView) findViewById(R.id.textViewInstaConnect);

        Button twButton = (Button) findViewById(R.id.twitterConnectButton);
        Button instaButton = (Button) findViewById(R.id.instagramConnectButton);

        if (isTwitterConnected)
        {
            twButton.setText("\\u2713 Connected");
            twButton.setTextColor(Color.GRAY);
            twConnect.setTextColor(Color.GRAY);
        }
        else
        {
            //TODO : Connect to Twitter

        }

        if (isInstagramConnected)
        {
            instaButton.setText("\\u2713 Connected");
            instaButton.setTextColor(Color.GRAY);
            instaConnect.setTextColor(Color.GRAY);

        }
        else
        {
            //TODO : Connect to Twitter

        }



    }


    public void connectTwitter(View v)
    {
        Intent intent = new Intent(FluxSettings.this, TwitterLogin.class);

        FluxSettings.this.startActivity(intent);



    }
    public void connectInstagram(View v)
    {
        Intent intent = new Intent(FluxSettings.this, InstagramLogin.class);

        FluxSettings.this.startActivity(intent);



    }
    public void rssSettings(View v)
    {
        Intent intent = new Intent(FluxSettings.this, RssSettings.class);

        FluxSettings.this.startActivity(intent);



    }







}