package com.example.warmmeal.model.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Navigator {

    public static void navigate(Context context, Class cls)
    {
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
    }

}
