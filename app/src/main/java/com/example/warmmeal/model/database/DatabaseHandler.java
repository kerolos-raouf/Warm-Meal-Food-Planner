package com.example.warmmeal.model.database;

import android.content.Context;

import com.example.warmmeal.model.contracts.LocalDataSource;

public class DatabaseHandler implements LocalDataSource {

    Context context;
    private static DatabaseHandler databaseHandler;
    private DatabaseHandler(Context context) {
        this.context = context;
    }

    public static DatabaseHandler getInstance(Context context) {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(context);
        }
        return databaseHandler;
    }
}
