package com.example.destressit.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.destressit.activities.MainActivity;
import com.example.destressit.R;

public class ViewReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
    }

    public void onclick(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
