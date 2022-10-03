package com.vnpt.mydailyfitness.HustCare.Exercise;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.vnpt.mydailyfitness.R;

public class RestDayActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest_day_layout);
        Button button=findViewById(R.id.button_restDay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestDayActivity.this.finish();
            }
        });
        Toolbar toolbar=(Toolbar) findViewById(R.id.exc_restDay_layout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestDayActivity.this.finish();
            }
        });
    }

}
