package com.example.warmmeal.fragment_plan.presenter;

import android.util.Log;

import com.example.warmmeal.fragment_plan.view.IPlanFragmentView;
import com.example.warmmeal.model.pojo.PlanMeal;
import com.example.warmmeal.model.repository.Repository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanFragmentPresenter {

    private final CompositeDisposable compositeDisposable;

    private IPlanFragmentView iPlanFragmentView;

    private final Repository repository;

    private static PlanFragmentPresenter planFragmentPresenter;

    private PlanFragmentPresenter(Repository repository, IPlanFragmentView iPlanFragmentView) {
        this.repository = repository;
        this.iPlanFragmentView = iPlanFragmentView;
        compositeDisposable = new CompositeDisposable();
    }


    public static PlanFragmentPresenter getInstance(Repository repository, IPlanFragmentView iPlanFragmentView) {
        if (planFragmentPresenter == null) {
            planFragmentPresenter = new PlanFragmentPresenter(repository, iPlanFragmentView);
        }
        planFragmentPresenter.iPlanFragmentView = iPlanFragmentView;
        return planFragmentPresenter;
    }


    public void getAllMealsInPlan(String userId)
    {
        if(userId != null)
        {
            compositeDisposable.add(repository.getAllCalenderMeals(userId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            iPlanFragmentView::onGetAllMealsSuccess
                            ,
                            (e)-> iPlanFragmentView.onFailure(e.getMessage())
                    ));
        }else
        {
            iPlanFragmentView.onFailure("You are in the guest mode, Please login first.");
        }
    }

    public void deleteMealFromPlan(PlanMeal planMeal)
    {
        compositeDisposable.add(repository.deleteCalenderMeal(planMeal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        iPlanFragmentView::onDeleteMealSuccess,
                        (e)-> iPlanFragmentView.onFailure(e.getMessage())
                ));
    }

    public void clearCompositeDisposable()
    {
        compositeDisposable.clear();
    }

}
