package com.example.walterzhang.instagram2.Profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.walterzhang.instagram2.R;

/**
 * Created by walterzhang on 9/9/18.
 */

public class EditProfileFragment extends Fragment {
    private static final String TAG = "EditProfileFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_editprofile,container,false);
        return view;
    }
}
