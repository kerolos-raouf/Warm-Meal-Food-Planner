package com.example.warmmeal.model.internet_connection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;

import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.Observable;

public class ConnectivityObserver {

    private final ConnectivityManager connectivityManager;
    public static Status InternetStatus = Status.Unavailable;

    public ConnectivityObserver(Context context) {
      connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public Observable<Status> observeConnectivity() {
        return Observable.create(emitter -> {

            ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback()
            {

                @Override
                public void onAvailable(@NonNull Network network) {
                    super.onAvailable(network);
                    emitter.onNext(Status.Available);
                }

                @Override
                public void onLosing(@NonNull Network network, int maxMsToLive) {
                    super.onLosing(network, maxMsToLive);
                    emitter.onNext(Status.Losing);
                }

                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    emitter.onNext(Status.Lost);
                }

                @Override
                public void onUnavailable() {
                    super.onUnavailable();
                    emitter.onNext(Status.Unavailable);
                }
            };

            connectivityManager.registerDefaultNetworkCallback(networkCallback);
            emitter.setCancellable(() -> connectivityManager.unregisterNetworkCallback(networkCallback));
        });
    }

    public enum Status {
        Available, Unavailable, Losing, Lost
    }
}
