package com.example.warmmeal.splashscreen.presenter;

import com.example.warmmeal.splashscreen.view.ISplashScreen;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashScreenPresenter {

    private final ISplashScreen iSplashScreen;
    private static SplashScreenPresenter presenter;

    private SplashScreenPresenter(ISplashScreen iSplashScreen)
    {
        this.iSplashScreen = iSplashScreen;
    }

    public static SplashScreenPresenter getInstance(ISplashScreen iSplashScreen)
    {
        if(presenter == null)
        {
            presenter = new SplashScreenPresenter(iSplashScreen);
        }

        return presenter;
    }

    public void timer(int time)
    {
        Observable<Long> doAction = Observable.timer(time, TimeUnit.SECONDS);

        doAction.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((i)->{
            iSplashScreen.onTimerFinished();
        });
    }

}
