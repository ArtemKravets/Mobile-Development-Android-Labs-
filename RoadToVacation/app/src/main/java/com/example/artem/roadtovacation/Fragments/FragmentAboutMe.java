package com.example.artem.roadtovacation.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.artem.roadtovacation.R;

/**
 * Created by Artem on 21.06.2018.
 */

public class FragmentAboutMe extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about_me, container, false);

        return view;
    }
}
