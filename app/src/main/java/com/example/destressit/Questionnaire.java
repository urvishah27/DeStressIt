package com.example.destressit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Questionnaire extends AppCompatActivity {

    TextView t1;
    RadioButton r1,r2,r3,r4,SRB;
    Integer i=1;
    Integer c,d=0;
    RadioGroup rg;
    String SRBtext;
    int selectedId;
    String[] stringArray = {"Q1","Q2","Q3","Q4","Q5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);
        t1 = findViewById(R.id.textView);
        t1.setText((stringArray[0]));
        r1 = findViewById(R.id.radioButton3);
        r2 = findViewById(R.id.radioButton4);
        r3 = findViewById(R.id.radioButton5);
        r4 = findViewById(R.id.radioButton6);
        rg = findViewById(R.id.radioGroup);
        selectedId = rg.getCheckedRadioButtonId();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedId = rg.getCheckedRadioButtonId();
            }
        });
    }

    public void onclick(View view){

        Log.d("CHECK", Integer.toString(selectedId));

        SRB = (RadioButton) findViewById(selectedId);
        Toast.makeText(this,SRB.getText().toString(), Toast.LENGTH_SHORT).show();

        SRBtext = SRB.getText().toString();
        if(SRBtext == "Most of the times")
        {
            c=c+1;
        }
        if(SRBtext == "Always")
        {
            d=d+1;
        }

        r1.setChecked(true);
        r2.setChecked(false);
        r3.setChecked(false);
        r4.setChecked(false);
        t1.setText(stringArray[i]);
        i=i+1;

    }

}
