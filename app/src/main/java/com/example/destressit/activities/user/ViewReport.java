package com.example.destressit.activities.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.destressit.activities.MainActivity;
import com.example.destressit.R;
import com.google.firebase.database.FirebaseDatabase;

public class ViewReport extends AppCompatActivity {

    Float reportValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        Bundle bundle = getIntent().getExtras();
        reportValues = bundle.getFloat("reportValues",0);


        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setProgress(Math.round(reportValues));

        TextView stressPercent = findViewById(R.id.stressPercent);
        stressPercent.setText(Math.round(reportValues)+" %");
    }

    public void getRecomm(View view){
        Intent i = new Intent(this, Recommendation.class);
        startActivity(i);
    }

    public void backDash(View view){
        Intent i = new Intent(this, NavigationActivity.class);
        startActivity(i);
    }
}
