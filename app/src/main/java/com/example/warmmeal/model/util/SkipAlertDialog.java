package com.example.warmmeal.model.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.warmmeal.R;

public class SkipAlertDialog {

    private final Activity activity;
    private AlertDialog alertDialog;

    public SkipAlertDialog(Activity activity)
    {
        this.activity = activity;
    }

    public void startAlertDialog(ISkipAlertDialog listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.skip_alert_dialog,null);
        builder.setView(view);
        builder.setCancelable(true);

        TextView pos = view.findViewById(R.id.positiveButton);
        TextView neg = view.findViewById(R.id.negativeButton);
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
