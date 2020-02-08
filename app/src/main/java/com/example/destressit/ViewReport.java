package com.example.destressit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.Observable;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class ViewReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
    }

    public void onclick(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
