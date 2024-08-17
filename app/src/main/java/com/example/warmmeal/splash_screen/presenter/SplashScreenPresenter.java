package com.example.warmmeal.splash_screen.presenter;

import com.example.warmmeal.splash_screen.view.ISplashScreen;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashScreenPresenter {

    private final ISplashScreen iSplashScreen;
    private static SplashScreenPresenter presenter;
    private Observable<Long> doAction;
    private CompositeDisposable disposable;

    private SplashScreenPresenter(ISplashScreen iSplashScreen)
    {
        disposable = new CompositeDisposable();
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
        doAction = Observable.timer(time, TimeUnit.SECONDS);

        disposable.add(doAction.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((i)->{
            iSplashScreen.onTimerFinished();
        }));

    }
    public void stopTimer()
    {
        disposable.clear();
    }

}
