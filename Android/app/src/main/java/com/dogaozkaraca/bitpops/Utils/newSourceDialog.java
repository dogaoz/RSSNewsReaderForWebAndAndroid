package com.dogaozkaraca.bitpops.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.dogaozkaraca.bitpops.R;


/**
 * Created by doga on 10/05/15.
 */
public class newSourceDialog extends ProgressDialog implements DialogInterface.OnDismissListener {
    public newSourceDialog(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_source_dialog);

        RelativeLayout dialogRL = (RelativeLayout) findViewById(R.id.dialog_RL);
        final EditText feedName = (EditText) findViewById(R.id.feedNameET);
        final EditText feedLink = (EditText) findViewById(R.id.feedUrlET);
        Button add_button = (Button) findViewById(R.id.button_add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.saveNewFeed(feedName.getText().toString(),feedLink.getText().toString(),getContext());
                newSourceDialog.this.dismiss();
            }
        });







    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        // Do whatever

       // if (mAdView != null) {
       //     mAdView.destroy();
      //  }
    }



}
