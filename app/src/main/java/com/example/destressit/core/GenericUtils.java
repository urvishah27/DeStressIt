package com.example.destressit.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.destressit.R;

public class GenericUtils {

    public void showInfoDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_alert, null);
        builder.setView(dialogView);
        TextView textView = (TextView) dialogView.findViewById(R.id.dialog_message);
        textView.setText(message);
        AlertDialog alert = builder.create();
        alert.show();
    }

}
