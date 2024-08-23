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

import com.example.warmmeal.R;
import com.example.warmmeal.fragment_profile.presenter.ProfilePresenter;
import com.example.warmmeal.login_ways.view.LoginWays;
import com.example.warmmeal.login_ways.view.OnSetUserRegisterSateResponse;
import com.example.warmmeal.model.database.DatabaseHandler;
import com.example.warmmeal.model.firebase.FirebaseHandler;
import com.example.warmmeal.model.network.NetworkAPI;
import com.example.warmmeal.model.pojo.FavouriteMeal;
import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.model.repository.RepositoryImpl;
import com.example.warmmeal.model.shared_pref.SharedPrefHandler;
import com.example.warmmeal.model.util.CustomAlertDialog;
import com.example.warmmeal.model.util.CustomProgressBar;
import com.example.warmmeal.model.util.ISkipAlertDialog;
import com.example.warmmeal.model.util.Navigator;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements OnLogOutResponse,OnSetUserRegisterSateResponse ,IProfileFragment,OnDownloadDataResponse, OnbBackupDataResponse {



    Button packUpButton,downloadButton,logOutButton;

    ProfilePresenter presenter;

    Context context;

    CustomAlertDialog customAlertDialog;

    ArrayList<FavouriteMeal> favouriteMeals;
    ArrayList<PlanMeal> planMeals;

    CustomProgressBar customProgressBar;


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
        downloadButton = view.findViewById(R.id.profileDownload);
        logOutButton = view.findViewById(R.id.profileLogOut);
        customAlertDialog = new CustomAlertDialog(requireActivity());
        customProgressBar = new CustomProgressBar(requireActivity());

        presenter = ProfilePresenter.getInstance(RepositoryImpl.getInstance(FirebaseHandler.getInstance(), NetworkAPI.getInstance(), DatabaseHandler.getInstance(view.getContext()), SharedPrefHandler.getInstance(context)),this);
        presenter.getFavouriteMeals();
        presenter.getPlanMeals();
    }
    private  void setUp() {
        packUpButton.setOnClickListener((e)->{
            customAlertDialog.startAlertDialog(new ISkipAlertDialog() {
                @Override
                public void onPositiveButtonClick() {
                    customProgressBar.startProgressBar();
                    presenter.packUpData(favouriteMeals,planMeals,ProfileFragment.this);
                }

                @Override
                public void onNegativeButtonClick() {

                }
            }, "Backup your data?", "Cancel", "Backup");
        });

        downloadButton.setOnClickListener((e)->{
            customAlertDialog.startAlertDialog(new ISkipAlertDialog() {
                @Override
                public void onPositiveButtonClick() {
                    customProgressBar.startProgressBar();
                    presenter.downloadData(ProfileFragment.this);
                }

                @Override
                public void onNegativeButtonClick() {

                }
            }, "Download your saved data?", "Cancel", "Download");
        });

        logOutButton.setOnClickListener((e)->{
            customAlertDialog.startAlertDialog(new ISkipAlertDialog() {
                @Override
                public void onPositiveButtonClick() {
                    presenter.logOut(ProfileFragment.this);
                }

                @Override
                public void onNegativeButtonClick() {

                }
            }, "Are you sure you want to log out?", "Cancel", "Log Out");
        });


    }

    @Override
    public void onLogOutSuccess() {
        presenter.setUserRegisterState(false,this);
    }

    @Override
    public void onLogOutFail(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUserRegisterState() {
        Navigator.navigateAndClearLast(requireActivity(), LoginWays.class);
        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onGetFavouriteMealsSuccess(ArrayList<FavouriteMeal> favouriteMeals) {
        this.favouriteMeals = favouriteMeals;
        //Toast.makeText(context, "done fav", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetPlanMealsSuccess(ArrayList<PlanMeal> planMeals) {
        this.planMeals = planMeals;
        //Toast.makeText(context, "done plan", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail(String message) {

    }


    @Override
    public void onDownloadFavouritesSuccess(ArrayList<FavouriteMeal> favouriteMeals) {
        for (FavouriteMeal favouriteMeal : favouriteMeals) {
            presenter.insertFavouriteMeal(favouriteMeal);
        }
        customProgressBar.dismissProgressBar();
    }

    @Override
    public void onDownloadPlanMealsSuccess(ArrayList<PlanMeal> planMeals) {
        for (PlanMeal planMeal : planMeals) {
            presenter.insertPlanMeal(planMeal);
        }
        Toast.makeText(context, "Data was downloaded successfully.", Toast.LENGTH_SHORT).show();
        customProgressBar.dismissProgressBar();
    }

    @Override
    public void onDownloadDataFail(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        customProgressBar.dismissProgressBar();
    }

    @Override
    public void onPackUpDataSuccess(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        customProgressBar.dismissProgressBar();
    }

    @Override
    public void onPackUpDataFail(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        customProgressBar.dismissProgressBar();
    }
}