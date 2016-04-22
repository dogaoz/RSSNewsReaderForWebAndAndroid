package com.dogaozkaraca.bitpops.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dogaozkaraca.bitpops.R;


/**
 * Created by doga on 10/05/15.
 */
public class LoadingDialog extends ProgressDialog implements DialogInterface.OnDismissListener {
    public LoadingDialog(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);

        RelativeLayout loadingRL = (RelativeLayout) findViewById(R.id.loadingRelativeLayout);
        TextView loadingText = (TextView)	findViewById(R.id.loadingText);

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
       // mAdView = (AdView) findViewById(R.id.ad_view);
     //   if (RotaryHome.isAdFree(getContext()) == true)
     //   {
    //        mAdView.setVisibility(View.GONE);
     //   }
     //   else {
            // Start loading the ad in the background.
            // Create an ad request. Check logcat output for the hashed device ID to
            // get test ads on a physical device. e.g.
            // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
       //     AdRequest adRequest = new AdRequest.Builder().build();

            // Start loading the ad in the background.
         //   mAdView.loadAd(adRequest);
      //  }


    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        // Do whatever

       // if (mAdView != null) {
       //     mAdView.destroy();
      //  }
    }


}
