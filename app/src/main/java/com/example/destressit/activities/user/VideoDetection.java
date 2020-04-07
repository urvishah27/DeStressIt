package com.example.destressit.activities.user;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.destressit.R;
import com.example.destressit.RecorderService;
import com.example.destressit.core.DatabaseHelper;
import com.example.destressit.core.PreferenceUtil;

import java.io.File;
import java.io.IOException;

public class VideoDetection extends Activity implements SurfaceHolder.Callback {
    private static final String TAG = VideoDetection.class.getSimpleName();
    public static SurfaceView mSurfaceView;
    public static SurfaceHolder mSurfaceHolder;
    public static Camera mCamera ;
    MediaRecorder mediaRecorder;
    private File tempfile;
    public static String gender;
    public static Float quizresult;

    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;

    String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detection);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkPermission();
        }

//        if (!hasPermissions(this, PERMISSIONS)) {
//            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
//            recreate();
//        }


    }

    protected void checkPermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(
                this,Manifest.permission.RECORD_AUDIO)
                + ContextCompat.checkSelfPermission(
                this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(
                this,Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){

            // Do something, when permissions not granted
            if(ActivityCompat.shouldShowRequestPermissionRationale(
                    this,Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,Manifest.permission.RECORD_AUDIO)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                // If we should give explanation of requested permissions

                // Show an alert dialog here with request explanation
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Camera, Audio and External" +
                        " Storage permissions are required to do the task.");
                builder.setTitle("Please grant those permissions");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(
                                VideoDetection.this,
                                new String[]{
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.RECORD_AUDIO,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                },
                                MY_PERMISSIONS_REQUEST_CODE
                        );
                    }
                });
                builder.setNeutralButton("Cancel",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                // Directly request for required permissions, without explanation
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },
                        MY_PERMISSIONS_REQUEST_CODE
                );
            }
        }else {
            // Do something, when permissions are already granted
            final AlertDialog.Builder newBuilder = new AlertDialog.Builder(this);
            newBuilder.setCancelable(false);
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.dialog_alert, null);
            newBuilder.setView(dialogView);
            TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
            textView.setText("Please keep your phone in line with your face to enable us to record your video and audio");
            dialogView.findViewById(R.id.dialog_ll).setVisibility(View.VISIBLE);
            Button acceptButton = (Button) dialogView.findViewById(R.id.accept);

            acceptButton.setText("Start");
            final AlertDialog alertDialog = newBuilder.create();
            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    cameraFunction();
                }
            });
            Button declineButton = (Button) dialogView.findViewById(R.id.decline);
            declineButton.setText("No");
            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    Intent i = new Intent(getApplicationContext(), NavigationActivity.class);
                    startActivity(i);
                }
            });
            alertDialog.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_CODE:{
                // When request is cancelled, the results array are empty
                if(
                        (grantResults.length >0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        + grantResults[3]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ){
                    // Permissions are granted
                    Toast.makeText(this,"Permissions granted.",Toast.LENGTH_SHORT).show();
                    cameraFunction();
                }else {
                    // Permissions are denied
                    Toast.makeText(this,"Permissions denied.",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        cameraFunction();

        return true;
    }


    public void cameraFunction(){
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        final Button button = findViewById(R.id.nextResult);


        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(VideoDetection.this, RecorderService.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startService(intent);
                MediaRecorderReady();
            }
        }, 1000);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopService(new Intent(VideoDetection.this, RecorderService.class));
                button.setBackgroundResource(R.drawable.background);
                button.setEnabled(true);
                mediaRecorder.stop();
                mediaRecorder.release();

            }
        }, 6000);

    }

    public void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        File audioFile = new File(Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/audio.wav");
        Log.e("path", Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/audio.wav");
        if(Build.VERSION.SDK_INT<26){
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            Log.e("audio","saved");
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            mediaRecorder.setOutputFile(audioFile);
//            Log.e("audio","saved");
//        }
       try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ConnectServeraudiomale()
    {


        String ipv4Address = "192.168.43.125";
        String portNumber = "8000";

        String postUrl = "http://" + ipv4Address + ":" + portNumber + "/audiomale";
        Log.e("serverip",postUrl);
        Log.e("posturl", postUrl);
        String selectedPath= Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/audio.wav";
        Log.e("servervideopath", selectedPath);
        File file = new File(selectedPath);
        RequestBody postBodyImage = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("audio",file.getName(),RequestBody.create(MediaType.parse("*/*"), file))
                .build();

        postRequest(postUrl, postBodyImage);
    }

    public void ConnectServeraudiofemale()
    {


        String ipv4Address = "192.168.43.125";
        String portNumber = "8000";

        String postUrl = "http://" + ipv4Address + ":" + portNumber + "/audiofemale";
        Log.e("serverip",postUrl);
        Log.e("posturl", postUrl);
        String selectedPath= Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                + "/audio.wav";
        Log.e("servervideopath", selectedPath);File file = new File(selectedPath);
        RequestBody postBodyImage = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("audio",file.getName(),RequestBody.create(MediaType.parse("*/*"), file))
                .build();

        postRequest(postUrl, postBodyImage);
    }

    public void ConnectServer()
    {


        String ipv4Address = "192.168.43.125";
        String portNumber = "8000";

        String postUrl = "http://" + ipv4Address + ":" + portNumber + "/video";
        Log.e("serverip",postUrl);
        Log.e("posturl", postUrl);
        String selectedPath= Environment.getExternalStorageDirectory().getPath() + "/video.mp4";
        Log.e("servervideopath", selectedPath);
        File file = new File(selectedPath);
        RequestBody postBodyImage = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("video",file.getName(),RequestBody.create(MediaType.parse("*/*"), file))
                .build();

        postRequest(postUrl, postBodyImage);
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

                        Log.e("serverfailure", "failure");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("serverconnected", "connected");

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

    public void getResult(View view){
//        sendquizresult();
        ConnectServer();
        gender=PreferenceUtil.getString(this,"gender");
        Log.e("gender",gender);
        if(gender=="Male"){
            ConnectServeraudiomale();
        }
        else{
            ConnectServeraudiofemale();
        }

        Intent i = new Intent(this, ViewReport.class);
        i.putExtra("detectionCheck",true);
        startActivity(i);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
