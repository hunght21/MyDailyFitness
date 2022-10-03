package com.vnpt.mydailyfitness.HustCare.Introduction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.vnpt.mydailyfitness.R;

public class OnBoardingFragment1 extends Fragment {


    public OnBoardingFragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.onboarding_1, container, false);
    }
}