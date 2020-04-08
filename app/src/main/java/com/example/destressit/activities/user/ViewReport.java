package com.example.destressit.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.destressit.activities.MainActivity;
import com.example.destressit.R;
import com.example.destressit.core.DatabaseHelper;
import com.example.destressit.core.GenericUtils;
import com.example.destressit.core.PreferenceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewReport extends AppCompatActivity {

    Number reportValues = (double)0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseHelper dbHelp = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);

        Intent intent = getIntent();
        if (intent.hasExtra("reportValues")) {
            reportValues = intent.getLongExtra("reportValues", 0);
            ProgressBar progressBar = findViewById(R.id.progressBar);
            progressBar.setProgress((int) Math.round((Long) reportValues));
            TextView stressPercent = findViewById(R.id.stressPercent);
            stressPercent.setText(Math.round((Long) reportValues) + " %");
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.dialog_alert, null);
            builder.setView(dialogView);
            TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
            textView.setText("Please wait until the result is loaded");
            final AlertDialog alert = builder.create();
            alert.show();

            final DatabaseReference dbref = database.getReference("users/" + dbHelp.getUKey() + "/reports");

            if (intent.hasExtra("detectionCheck") && intent.getBooleanExtra("detectionCheck", false)) {
                dbref.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Long audioResult = (Long) dataSnapshot.child("audioresult").getValue();
                        Long pupilResult = (Long) dataSnapshot.child("pupil_mm").getValue();
                        Long negVideo = (Long) dataSnapshot.child("video_negative").getValue();

                        Long questionnaireResult = PreferenceUtil.getLong(getApplicationContext(),"quizStress",0);
    //                    Long posVideo = (long)dataSnapshot.child("video_positive").getValue();

                        reportValues = (audioResult + pupilResult + negVideo + questionnaireResult) / 4;

                        ProgressBar progressBar = findViewById(R.id.progressBar);
                        progressBar.setProgress((int) Math.round((Long) reportValues));
                        TextView stressPercent = findViewById(R.id.stressPercent);
                        stressPercent.setText(Math.round((Long) reportValues) + " %");

                        alert.cancel();

                        dbref.child("questionnaire_value").setValue(questionnaireResult);
                        dbref.child("stressPercent").setValue(reportValues);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
            } else {
                dbref.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reportValues = (Long) dataSnapshot.child("stressPercent").getValue();
                        ProgressBar progressBar = findViewById(R.id.progressBar);
                        progressBar.setProgress((int) Math.round((Long) reportValues));
                        TextView stressPercent = findViewById(R.id.stressPercent);
                        stressPercent.setText(Math.round((Long) reportValues) + " %");

                        alert.cancel();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }
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
