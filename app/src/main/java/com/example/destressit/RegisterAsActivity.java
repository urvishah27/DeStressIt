package com.example.destressit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.destressit.core.PreferenceUtil;

public class RegisterAsActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton selectedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
    }

    public void nextClick(View view){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        selectedButton = (RadioButton) findViewById(selectedId);
        Toast.makeText(this,selectedButton.getText().toString(),Toast.LENGTH_LONG).show();
        PreferenceUtil.setString(this,"utype",selectedButton.getText().toString());
        if(selectedButton.getText().toString()=="User") {
            startActivity(new Intent(this, NavigationActivity.class));
        }
        else{
            startActivity(new Intent(this, therapistdashboard.class));
        }
    }

}

