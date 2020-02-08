package com.example.destressit;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.destressit.core.PreferenceUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    Context context;
    String type;


    public DatabaseHelper(Context context){
        this.context = context;
    }

    public void addUser(String uname, String uemail, String uphone){

        DatabaseReference dbref = database.getReference("/users");
        DatabaseReference dbMap = database.getReference("/map");
        DatabaseReference newUser = dbref.push();
        String key = newUser.getKey();

        HashMap<String, Object> result = new HashMap<>();
        result.put("uname", uname);
        result.put("uemail", uemail);
        result.put("uphone", uphone);

        HashMap<String, Object> result1 = new HashMap<>();
        result.put("email", uemail);
        result.put("type", "user");

        PreferenceUtil.setString(context,"KEY",key);

        dbMap.child(key).updateChildren(result1);
        dbref.child(key).updateChildren(result);

    }

    public void updateUser(String uname, String uphone){
        DatabaseReference dbref = database.getReference("/users/" + getUKey());
        DatabaseReference user = dbref.push();

        HashMap<String, Object> result = new HashMap<>();
        result.put("uname", uname);
        result.put("uphone", uphone);

        dbref.updateChildren(result);
    }

    public String getUKey(){
        return "1";
//        return PreferenceUtil.getString(context,"KEY");
    }

    public void addTherapist(String uname, String uemail){

        DatabaseReference dbref = database.getReference("/therapists");
        DatabaseReference dbMap = database.getReference("/map");
        DatabaseReference newUser = dbref.push();
        String key = newUser.getKey();

        HashMap<String, Object> result = new HashMap<>();
        result.put("tname", uname);
        result.put("temail", uemail);

        HashMap<String, Object> result1 = new HashMap<>();
        result.put("email", uemail);
        result.put("type", "therapist");

        PreferenceUtil.setString(context,"KEY",key);

        dbMap.child(key).updateChildren(result1);
        dbref.child(key).updateChildren(result);

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
        return "2";
//        return PreferenceUtil.getString(context,"KEY");
    }

    public String getType(final String email){
        DatabaseReference dbref = database.getReference("users/");
        dbref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> keyChildren = dataSnapshot.getChildren();
                for (DataSnapshot key : keyChildren) {
                    if(key.child("email").getValue()==email){
                        type = (String) key.child("type").getValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return type;
    }

}
