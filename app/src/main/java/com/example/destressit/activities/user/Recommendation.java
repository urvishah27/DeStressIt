package com.example.destressit.activities.user;

import android.os.Bundle;

import com.example.destressit.ImageAdapter;
import com.example.destressit.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Recommendation extends AppCompatActivity {

    private Object ViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        ViewPager mViewPager = (ViewPager)findViewById(R.id.ViewPager1);
        ImageAdapter adapterView = new ImageAdapter(this);
        mViewPager.setAdapter(adapterView);

    }

}
