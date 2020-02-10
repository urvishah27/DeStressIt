package com.example.destressit;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.destressit.core.PreferenceUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DatabaseHelper {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Context context;
    String type;


    public DatabaseHelper(Context context){
        this.context = context;
    }

    public void addUser(String uname, String uemail, String uphone){

        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference dbref = database.getReference("/users");
        DatabaseReference dbMap = database.getReference("/map");

        HashMap<String, Object> result = new HashMap<>();
        result.put("uname", uname);
        result.put("uemail", uemail);
        result.put("uphone", uphone);

        HashMap<String, Object> result1 = new HashMap<>();
        result1.put("email", uemail);
        result1.put("type", "user");

        PreferenceUtil.setString(context,"KEY",currentuser);

        dbMap.child(currentuser).updateChildren(result1);
        dbref.child(currentuser).updateChildren(result);

    }

    public void updateUser(String uname, String uphone){
        DatabaseReference dbref = database.getReference("/users/" + getUKey());
        DatabaseReference user = dbref.push();

        HashMap<String, Object> result = new HashMap<>();
        result.put("uname", uname);
        result.put("uphone", uphone);

        dbref.updateChildren(result);
    }

    public void getGender(String gender){
        DatabaseReference dbref = database.getReference("/users/" + getUKey());
        dbref.child("gender").setValue(gender);
    }

    public String getUKey(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void addTherapist(String uname, String uemail){

        DatabaseReference dbref = database.getReference("/therapists");
        DatabaseReference dbMap = database.getReference("/map");
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        HashMap<String, Object> result = new HashMap<>();
        result.put("tname", uname);
        result.put("temail", uemail);

        HashMap<String, Object> result1 = new HashMap<>();
        result1.put("email", uemail);
        result1.put("type", "therapist");

        PreferenceUtil.setString(context,"KEY",currentuser);

        dbMap.child(currentuser).updateChildren(result1);
        dbref.child(currentuser).updateChildren(result);

    }

    public void updateTherapist(String uname, String uphone){
        DatabaseReference dbref = database.getReference("/therapist/" + getTKey());
        DatabaseReference user = dbref.push();

        HashMap<String, Object> result = new HashMap<>();
        result.put("uname", uname);
        result.put("uphone", uphone);

        dbref.updateChildren(result);
    }

    public String getTKey(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

//    public String getType(final String email){
//        DatabaseReference dbref = database.getReference("map/");
//        dbref.addValueEventListener(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String currentuser = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
//                type = dataSnapshot.child(currentuser).child("type").getValue().toString();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//        return type;
//    }

}
