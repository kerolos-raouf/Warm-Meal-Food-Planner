package com.example.warmmeal.model.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.warmmeal.R;

public class CustomAlertDialog {

    private final Activity activity;
    private AlertDialog alertDialog;

    public CustomAlertDialog(Activity activity)
    {
        this.activity = activity;
    }

    public void startAlertDialog(ISkipAlertDialog listener,String message,String negativeButtonText,String positiveButtonText)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_alert_dialog,null);
        builder.setView(view);

        Button pos = view.findViewById(R.id.alertDialogPositive);
        Button neg = view.findViewById(R.id.alertDialogNegative);
        TextView messageText = view.findViewById(R.id.alertDialogMessage);

        pos.setText(positiveButtonText);
        neg.setText(negativeButtonText);
        messageText.setText(message);

        pos.setOnClickListener(v -> {
            listener.onPositiveButtonClick();
            alertDialog.dismiss();
        });
        neg.setOnClickListener(v -> {
            listener.onNegativeButtonClick();
            alertDialog.dismiss();
        });

        alertDialog = builder.create();
        alertDialog.show();
    }

}
