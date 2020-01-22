package com.example.destressit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.destressit.core.PreferenceUtil;

public class EditProfileActivity extends AppCompatActivity {

    EditText name;
    EditText email;
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.nameEdit);
        email = findViewById(R.id.emailEdit);
        phone = findViewById(R.id.phoneEdit);

        String setName = PreferenceUtil.getString(this,"uname");
        if(setName!=null)
            name.setText(PreferenceUtil.getString(this,"uname"));

        email.setText(PreferenceUtil.getString(this,"uemail"));

        String setPhone = PreferenceUtil.getString(this,"uphone");
        if(setPhone!=null)
            phone.setText(setPhone);

    }

    public void saveClick(View view){
        PreferenceUtil.setString(this,"uname",name.getText().toString());
        PreferenceUtil.setString(this,"uphone",phone.getText().toString());
        startActivity(new Intent(this,NavigationActivity.class));
    }


}
