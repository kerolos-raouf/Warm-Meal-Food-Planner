package com.example.warmmeal.model.repository;

import com.example.warmmeal.login.view.OnLoginResponse;
import com.example.warmmeal.login_ways.view.OnLoginWithGmailResponse;
import com.example.warmmeal.model.contracts.ManagingAccount;
import com.example.warmmeal.model.contracts.ManagingAccountState;
import com.example.warmmeal.model.contracts.RemoteDataSource;
import com.example.warmmeal.signup.view.OnCreatingAccountResponse;
import com.google.firebase.auth.FirebaseUser;

public interface Repository extends RemoteDataSource ,ManagingAccount, ManagingAccountState {

}
