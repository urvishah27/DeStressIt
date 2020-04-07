package com.example.destressit.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.destressit.R;
import com.example.destressit.core.DatabaseHelper;
import com.example.destressit.core.PreferenceUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Questionnaire extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    TextView t1;
    RadioButton r1, r2, r3, r4, SRB;
    Integer i = 0;
    Integer prevLevel = 0, x = 0, y = 0, c = 0, d = 0;
    RadioGroup rg;
    String SRBtext;
    int selectedId;
    String UserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String[] questionList1 = {"Do you feel sad or low ?",
            "Do you feel tired and low on energy ?",
            "Do you feel guilty ?",
            "Do you have difficulty in making decisions ?"
    },

    questionList2 = {
            "Do you feel irritated ?",
            "Do you feel disinterested in things that earlier seemed pleasurable ?",
            "Do you believe that nothing will ever work out for you ?",
            "Do you have difficulty concentrating ?",
    },

    questionList3 = {
            "Do you feel you should be (or you are being) punished ?",
            "Do you feel restless and anxious ?",
            "Do you feel like a failure ?",
            "Do you have difficulty in sleeping ?"
    },

    questionList4 = {
            "Do you have thoughts about ending your life ?",
            "Do you feel unhappy even when good things happen ?",
            "Do you think your life isn't worth living ?",
            "Do you feel a need to harm yourself or your loved ones ?",
    };
    String[] quesAns = new String[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        connect();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionnaire);

        t1 = findViewById(R.id.textView);
        r1 = findViewById(R.id.radioButton3);
        r2 = findViewById(R.id.radioButton4);
        r3 = findViewById(R.id.radioButton5);
        r4 = findViewById(R.id.radioButton6);
        rg = findViewById(R.id.radioGroup);
        selectedId = rg.getCheckedRadioButtonId();

        quesAns[0] = questionList1[0];
        t1.setText(quesAns[0]);
        appendQuestions(questionList1);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedId = rg.getCheckedRadioButtonId();
            }
        });
    }

    public void appendQuestions(String[] questionList) {

        x = 0;
        y = 0;
        int a;
        if (i != 0) {
            a = i;
            for (String s : questionList) {
                quesAns[a++] = s;
                Log.d("CHECK4", "Appended: " + s);
            }


        } else {
            a = 1;
            for (int j = 1; j < questionList.length; j++) {
                quesAns[a++] = questionList[j];
                Log.d("CHECK4", "Appended: " + questionList[j]);
            }
        }

    }

    public int conditionalAppend(int x, int y) {
        if (prevLevel == 0) {
            if ((x >= 3) || (y >= 3) || ((x == 2) && (y == 2)) || (x + y == 3)) {
                appendQuestions(questionList4);
                return 4;
            } else if ((x < 3) && (y < 3)) {
                appendQuestions(questionList2);
                return 2;
            }
        } else if (prevLevel == 4) {
            if ((x >= 3) || (y >= 3) || ((x == 2) && (y == 2)) || (x + y == 3)) {
                Toast.makeText(this, "High", Toast.LENGTH_SHORT).show();
                return 5;
            } else if ((x < 3) && (y < 3)) {
                Toast.makeText(this, "Moderate", Toast.LENGTH_SHORT).show();
                return 5;
            }
        } else if (prevLevel == 2) {
            if ((x >= 3) || (y >= 3) || ((x == 2) && (y == 2)) || (x + y == 3)) {
                appendQuestions(questionList3);
                return 3;
            } else if ((x < 3) && (y < 3)) {
                Toast.makeText(this, "Low", Toast.LENGTH_SHORT).show();
                return 5;
            }
        } else if (prevLevel == 3) {
            if ((x >= 3) || (y >= 3) || ((x == 2) && (y == 2)) || (x + y == 3)) {
                Toast.makeText(this, "Moderate", Toast.LENGTH_SHORT).show();
                return 5;
            } else if ((x < 3) && (y < 3)) {
                Toast.makeText(this, "Low", Toast.LENGTH_SHORT).show();
                return 5;
            }
        }
        return 1;
    }

    public void onclick(View view) {

        Log.d("CHECK", Integer.toString(selectedId));

        SRB = (RadioButton) findViewById(selectedId);
        SRBtext = SRB.getText().toString();

        if (SRBtext.equalsIgnoreCase("Most of the times")) {
            c++;
            x++;
        } else if (SRBtext.equalsIgnoreCase("Always")) {
            d++;
            y++;
        }

        r1.setChecked(true);
        r2.setChecked(false);
        r3.setChecked(false);
        r4.setChecked(false);
        i = i + 1;
        Log.d("CHECK1", "i: " + i);
        Log.d("CHECK3", "Mod: " + i % 4);

        if (i % 4 == 0) {
            prevLevel = conditionalAppend(x, y);
            Log.d("CHECK2", "level: " + prevLevel);
        }

        if (prevLevel != 5)
            t1.setText(quesAns[i]);
        else
            endTask();
    }

    public void endTask(){
        Long stressPercent = (long)((c*0.75+d)*100)/i;
        PreferenceUtil.setLong(this,"quizStress",stressPercent);
        startActivity(new Intent(this,VideoDetection.class));
    }

    public void connect() {
        Log.e("quess","connect");
        String ipv4Address = "192.168.43.125";
        String portNumber = "8000";
        String postUrl = "http://" + ipv4Address + ":" + portNumber + "/uid";
        Log.e("serverip", postUrl);
        Log.e("posturl", postUrl);
        Log.e("uid", UserUID);
        MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        RequestBody postBody = RequestBody.create(mediaType, UserUID);

        postRequest(postUrl, postBody);
    }

    void postRequest(String postUrl, RequestBody postBody) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Log.e("send uid", "failure");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("send uid", "connected");

//                        try {
////                            responseText.setText(response.body().string());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
                    }
                });
            }
        });
    }


}
