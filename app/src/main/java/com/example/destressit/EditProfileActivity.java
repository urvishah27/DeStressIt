package com.example.destressit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.destressit.core.PreferenceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText phone;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseHelper dbHelp = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.nameEdit);
        email = findViewById(R.id.emailEdit);
        phone = findViewById(R.id.phoneEdit);

        DatabaseReference dbref = database.getReference("users/" + dbHelp.getUKey());

        dbref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dname = dataSnapshot.child("uname");
                DataSnapshot demail = dataSnapshot.child("uemail");
                DataSnapshot dphone = dataSnapshot.child("uphone");

                name.setText(dname.getValue(String.class));
                email.setText(demail.getValue(String.class));
                phone.setText(dphone.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void saveClick(View view){
        dbHelp.updateUser(name.getText().toString(),phone.getText().toString());
        startActivity(new Intent(this,NavigationActivity.class));
    }


}
