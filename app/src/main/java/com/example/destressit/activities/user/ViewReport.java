package com.example.destressit.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.destressit.activities.MainActivity;
import com.example.destressit.R;

public class ViewReport extends AppCompatActivity {

    String reportValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        Bundle bundle = getIntent().getExtras();
        reportValues = bundle.getString("reportValues");

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(Integer.parseInt(reportValues));
    }

    public void getRecomm(View view){
        Intent i = new Intent(this, Recommendation.class);
        startActivity(i);
    }

    public void backDash(View view){
        Intent i = new Intent(this, DashboardFragment.class);
        startActivity(i);
    }
}
