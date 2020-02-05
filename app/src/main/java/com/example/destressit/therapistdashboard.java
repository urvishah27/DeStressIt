package com.example.destressit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;

public class therapistdashboard extends AppCompatActivity {
    Button contact;
    private static final int REQUEST_CALL=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_therapistdashboard);
        contact=(Button)findViewById(R.id.myButton2);

    }
    public void onclick(View view){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.dialog_alert, null);
            builder.setView(dialogView);
            TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
            textView.setText("How do you want to contact?");
            dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
            Button acceptButton = (Button) dialogView.findViewById(R.id.accept);
            acceptButton.setText("Email");
            final AlertDialog alertDialog = builder.create();
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    String[] recipients={"mailto@gmail.com"};
                    intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Reply to report");
                    intent.setType("text/html");
                    intent.setPackage("com.google.android.gm");
                    startActivity(Intent.createChooser(intent, "Send mail"));
                    alertDialog.dismiss();
                }
            });
            Button declineButton = (Button) dialogView.findViewById(R.id.decline);
            declineButton.setText("Call");

            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    makecall();
                    alertDialog.dismiss();
                }
            });
            alertDialog.show();
        }
        private void makecall() {
        if (ContextCompat.checkSelfPermission(therapistdashboard.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(therapistdashboard.this,new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
        }
        else{
            String dial="tel:7506086775";
            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makecall();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

