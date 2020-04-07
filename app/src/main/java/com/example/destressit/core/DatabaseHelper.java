package com.example.destressit.core;

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
        HashMap<String, Object> reports = new HashMap<>();
        reports.put("audioresult","10000");
        reports.put("pupil_mm","10000");
        reports.put("video_negative","10000");
        reports.put("video_positive","10000");

        HashMap<String, Object> result = new HashMap<>();
        result.put("uname", uname);
        result.put("uemail", uemail);
        result.put("uphone", uphone);
        result.put("reports",reports);

        HashMap<String, Object> result1 = new HashMap<>();
        result1.put("email", uemail);
        result1.put("type", "user");

        PreferenceUtil.setString(context,"KEY",currentuser);

        dbMap.child(currentuser).updateChildren(result1);
        dbref.child(currentuser).updateChildren(result);

    }

    public void updateUser(String uname, String uphone){
        DatabaseReference dbref = database.getReference("/users/" + getUKey());

        HashMap<String, Object> result = new HashMap<>();
        result.put("uname", uname);
        result.put("uphone", uphone);
        PreferenceUtil.setString(context,"uname",uname);
        PreferenceUtil.setString(context,"uphone",uphone);

        dbref.updateChildren(result);
    }

    public void setGender(String gender){
        DatabaseReference dbref = database.getReference("/users/" + getUKey());
        dbref.child("gender").setValue(gender);
        PreferenceUtil.setString(context,"gender",gender);
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
        DatabaseReference dbref = database.getReference("/therapists/" + getTKey());

        HashMap<String, Object> result = new HashMap<>();
        result.put("tname", uname);
        result.put("tphone", uphone);
        PreferenceUtil.setString(context,"uphone",uphone);

        dbref.updateChildren(result);
    }

    public String getTKey(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}
