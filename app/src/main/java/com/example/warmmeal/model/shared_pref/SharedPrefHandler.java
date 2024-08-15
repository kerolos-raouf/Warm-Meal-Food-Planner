package com.example.warmmeal.model.shared_pref;

import com.example.warmmeal.model.contracts.ManagingAccountState;

public class SharedPrefHandler implements ManagingAccountState {

    private static SharedPrefHandler sharedPrefHandler;
    private SharedPrefHandler() {
    }

    public static SharedPrefHandler getInstance() {
        if (sharedPrefHandler == null) {
            sharedPrefHandler = new SharedPrefHandler();
        }
        return sharedPrefHandler;
    }

}
