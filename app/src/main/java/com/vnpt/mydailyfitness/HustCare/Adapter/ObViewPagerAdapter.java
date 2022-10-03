package com.vnpt.mydailyfitness.HustCare.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.vnpt.mydailyfitness.HustCare.Introduction.OnBoardingFragment1;
import com.vnpt.mydailyfitness.HustCare.Introduction.OnBoardingFragment2;
import com.vnpt.mydailyfitness.HustCare.Introduction.OnBoardingFragment3;

public class ObViewPagerAdapter extends FragmentStatePagerAdapter {


    public ObViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new OnBoardingFragment1();
            case 1:
                return new OnBoardingFragment2();
            case 2:
                return new OnBoardingFragment3();
            default:
                return new OnBoardingFragment1();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
