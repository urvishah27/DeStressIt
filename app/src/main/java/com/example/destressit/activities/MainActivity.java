package com.example.destressit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.destressit.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onclick(View view){
        Intent i = new Intent(this,RegistrationActivity.class);
//        PreferenceUtil.clearAllPreferences(this);
        startActivity(i);
    }
}
