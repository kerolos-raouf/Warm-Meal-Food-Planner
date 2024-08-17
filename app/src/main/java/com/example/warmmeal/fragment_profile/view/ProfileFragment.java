package com.example.warmmeal.fragment_profile.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;

import com.example.warmmeal.R;
import com.example.warmmeal.fragment_profile.presenter.ProfilePresenter;
import com.example.warmmeal.login_ways.view.LoginWays;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.Navigator;

import java.util.Objects;

public class ProfileFragment extends Fragment implements OnLogOutResponse {



    Button packUpButton,logOutButton;

    ProfilePresenter presenter;

    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setUp();

    }


    private void init(View view) {
        context  = view.getContext();
        packUpButton = view.findViewById(R.id.profilePackUp);
        logOutButton = view.findViewById(R.id.profileLogOut);

        presenter = ProfilePresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(view.getContext()), SharedPrefHandler.getInstance()));
    }
    private  void setUp() {
        packUpButton.setOnClickListener((e)->{

        });
        logOutButton.setOnClickListener((e)->{
            presenter.logOut(this);
        });
    }

    @Override
    public void onLogOutSuccess() {
        Navigator.navigateAndClearLast(requireActivity(), LoginWays.class);
        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogOutFail(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}