package com.example.destressit;

import android.content.Intent;
import android.os.Bundle;

import com.example.destressit.core.PreferenceUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.NestedScrollingChild;
import androidx.core.widget.NestedScrollView;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.TextView;

public class Recommendation extends AppCompatActivity {

    private Object ViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        ViewPager mViewPager = (ViewPager)findViewById(R.id.ViewPager1);
        ImageAdapter2 adapterView = new ImageAdapter2(this);
        mViewPager.setAdapter(adapterView);

    }

}
