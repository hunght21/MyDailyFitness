package com.vnpt.mydailyfitness.HustCare.Introduction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.vnpt.mydailyfitness.HustCare.Login.LoginActivity;
import HomePage.MainActivity;
import com.vnpt.mydailyfitness.R;

public class OnBoardingFragment3 extends Fragment {
    private View mView;
    private Button btnStart;
    public OnBoardingFragment3() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView= inflater.inflate(R.layout.onboarding3, container, false);
        btnStart=mView.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(view -> {
            nextActivity();
        });
        return mView;
    }
    private void nextActivity(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            Intent intent=new Intent(getActivity(),LoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent=new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }
}