package com.example.destressit.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.destressit.activities.user.NavigationActivity;
import com.example.destressit.R;
import com.example.destressit.activities.therapist.TherapistsNavActivity;
import com.example.destressit.core.DatabaseHelper;
import com.example.destressit.core.PreferenceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText phone;

    String type;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseHelper dbHelp = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
        builder.setView(dialogView);
        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
        textView.setText("Please wait until the data loads");
        final AlertDialog alert = builder.create();
        alert.show();

        name = findViewById(R.id.nameEdit);
        email = findViewById(R.id.emailEdit);
        phone = findViewById(R.id.phoneEdit);

        type = PreferenceUtil.getString(this,"utype");
        Toast.makeText(this,type,Toast.LENGTH_SHORT).show();
        if(type.equalsIgnoreCase("user")){
            DatabaseReference dbref = database.getReference("users/" + dbHelp.getUKey());
            dbref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot dname = dataSnapshot.child("uname");
                    DataSnapshot demail = dataSnapshot.child("uemail");
                    DataSnapshot dphone = dataSnapshot.child("uphone");

                    name.setText(dname.getValue(String.class));
                    email.setText(demail.getValue(String.class));
                    email.setEnabled(false);
                    phone.setText(dphone.getValue(String.class));

                    alert.cancel();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            DatabaseReference dbref = database.getReference("therapists/" + dbHelp.getTKey());
            dbref.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DataSnapshot dname = dataSnapshot.child("tname");
                    DataSnapshot demail = dataSnapshot.child("temail");
                    DataSnapshot dphone = dataSnapshot.child("tphone");

                    name.setText(dname.getValue(String.class));
                    email.setText(demail.getValue(String.class));
                    phone.setText(dphone.getValue(String.class));

                    alert.cancel();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void saveClick(View view){
        if(type.equalsIgnoreCase("user")){
            dbHelp.updateUser(name.getText().toString(),phone.getText().toString());
            startActivity(new Intent(this, NavigationActivity.class));
        } else {
            dbHelp.updateTherapist(name.getText().toString(),phone.getText().toString());
            startActivity(new Intent(this, TherapistsNavActivity.class));
        }

    }


}
