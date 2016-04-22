package com.dogaozkaraca.bitpops.FirstUse;

/**
 * Created by Doga Oz on 3/26/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dogaozkaraca.bitpops.FluxFirstUse;
import com.dogaozkaraca.bitpops.R;
import com.google.android.gms.common.SignInButton;

public class No0_Welcome extends Fragment {


    public static No0_Welcome newInstance() {
        No0_Welcome sampleSlide = new No0_Welcome();

       // Bundle args = new Bundle();
       // args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
       // sampleSlide.setArguments(args);

        return sampleSlide;
    }

    private int layoutResId;

    public No0_Welcome() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
       //     layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.firstuse_0_welcome, container, false);

        SignInButton google_signin = (SignInButton) v.findViewById(R.id.g_sign_in_button);

        google_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FluxFirstUse.firstUseActivity.startG_SignIn();
            }
        });


        return v;
    }

}