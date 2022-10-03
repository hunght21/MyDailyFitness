package com.vnpt.mydailyfitness.HustCare.Introduction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.vnpt.mydailyfitness.HustCare.Adapter.ObViewPagerAdapter;
import com.vnpt.mydailyfitness.R;

import me.relex.circleindicator.CircleIndicator;

public class OnBoardingActivity extends AppCompatActivity {
    private Button btnStart,btnNext,btnBack,btnSkip;
    private ViewPager mViewPager;
    private RelativeLayout layoutBottom;
    private CircleIndicator circleIndicator;
    private ObViewPagerAdapter obViewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        initUi();
        initListener();
        obViewPagerAdapter=new ObViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(obViewPagerAdapter);
        circleIndicator.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==2){
                    btnSkip.setVisibility(View.GONE);
                    layoutBottom.setVisibility(View.GONE);
                } else{
                    btnSkip.setVisibility(View.VISIBLE);
                    layoutBottom.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initListener(){
        btnSkip.setOnClickListener(view -> mViewPager.setCurrentItem(2));
        btnNext.setOnClickListener(view -> {
            if(mViewPager.getCurrentItem()<2){
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            }
        });
        btnBack.setOnClickListener(view -> {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
        });
    }
    private void initUi(){
        btnNext=findViewById(R.id.btn_next);
        btnBack=findViewById(R.id.btn_back);
        btnSkip=findViewById(R.id.btn_skip);
        btnStart=findViewById(R.id.btn_start);
        circleIndicator=findViewById(R.id.circle_indicator);
        layoutBottom=findViewById(R.id.layout_bottom);
        mViewPager=findViewById(R.id.view_pager1);
    }
}