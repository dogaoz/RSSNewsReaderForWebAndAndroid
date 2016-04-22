package com.dogaozkaraca.bitpops;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.dogaozkaraca.bitpops.FirstUse.No0_Welcome;
import com.dogaozkaraca.bitpops.FirstUse.No1_ChooseCategory;
import com.dogaozkaraca.bitpops.FirstUse.No2_ChooseRecommendedFeeds;
import com.github.paolorotolo.appintro.AppIntro;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Doga Oz on 3/26/2016.
 */
public class FluxFirstUse extends AppIntro {
    public static FluxFirstUse firstUseActivity;
    // Please DO NOT override onCreate. Use init.
    String TAG = "BitPops";
    public static int RC_SIGN_IN = 90;
    public static GoogleApiClient mGoogleApiClient;
    @Override
    public void init(Bundle savedInstanceState) {
        firstUseActivity = this;
        // Add your slide's fragments here.
        // AppIntro will automatically generate the dots indicator and buttons.
        addSlide(No0_Welcome.newInstance());
        addSlide(No1_ChooseCategory.newInstance());
        addSlide(No2_ChooseRecommendedFeeds.newInstance());

        //addSlide(second_fragment);
        //addSlide(third_fragment);
       // addSlide(fourth_fragment);

        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.
        //addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));

        // OPTIONAL METHODS
        // Override bar/separator color.
        setBarColor(Color.parseColor("#3F51B5"));
        setSeparatorColor(Color.parseColor("#2196F3"));
        setSwipeLock(true);
        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
        setProgressButtonEnabled(true);
        setDepthAnimation();
        nextEnabled = false;
        getSupportActionBar().setTitle("Welcome");


        // GOOGLE SIGN-IN

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



        // Turn vibration on and set intensity.
        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
        //setVibrate(true);
        //setVibrateIntensity(30);
    }

    public static void startG_SignIn()
    {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        firstUseActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private boolean skipped = false;
    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        if (savedCurrentItem == 0)
        {
            showSkipButton(false);
            skipped = true;
            nextEnabled = true;
            nextButton.performClick();

        }

    }

    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        FluxFirstUse.this.finish();
    }

    @Override
    public void onSlideChanged() {
        // Do something when the slide changes.


    }



    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
        if (currentPage == 0 && skipped == false)
        {
            //Login
            Toast.makeText(this,"Error, cant connect to server!",Toast.LENGTH_SHORT).show();
        }
        else if (currentPage == 1)
        {
            No1_ChooseCategory.updateTitle();
        }
        else if (currentPage == 2)
        {
            No2_ChooseRecommendedFeeds.updateList(FluxFirstUse.this);
            getSupportActionBar().setTitle("Almost Done...");
        }
        else if (currentPage == 3)
        {
            getSupportActionBar().setTitle("BitPops");
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
          if (requestCode == RC_SIGN_IN) {
              GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
              handleSignInResult(result);
          }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
           // mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
        } else {
            // Signed out, show unauthenticated UI.
        }
    }


}