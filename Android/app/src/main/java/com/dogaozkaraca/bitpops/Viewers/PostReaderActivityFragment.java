package com.dogaozkaraca.bitpops.Viewers;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dogaozkaraca.bitpops.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class PostReaderActivityFragment extends Fragment {

    public PostReaderActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_reader, container, false);
    }
}
