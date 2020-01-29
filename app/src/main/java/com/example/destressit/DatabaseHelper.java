package com.example.destressit;

import android.content.Context;
import android.util.Log;

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

    public DatabaseHelper(Context context){
        this.context = context;
    }

    public void addUser(String uname, String uemail, String uphone){

        DatabaseReference dbref = database.getReference("/users");
        DatabaseReference newUser = dbref.push();
        String key = newUser.getKey();

        HashMap<String, Object> result = new HashMap<>();
        result.put("uname", uname);
        result.put("uemail", uemail);
        result.put("uphone", uphone);
        result.put("stressPercent",null);

        PreferenceUtil.setString(context,"KEY",key);

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
        DatabaseReference newUser = dbref.push();
        String key = newUser.getKey();

        HashMap<String, Object> result = new HashMap<>();
        result.put("tname", uname);
        result.put("temail", uemail);

        PreferenceUtil.setString(context,"KEY",key);

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
        return "1";
//        return PreferenceUtil.getString(context,"KEY");
    }

}
