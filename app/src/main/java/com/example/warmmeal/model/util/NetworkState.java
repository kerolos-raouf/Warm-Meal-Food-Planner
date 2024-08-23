package com.example.warmmeal.model.util;

public class NetworkState<T> {

    public NetworkState() {

    }
    public NetworkState(T data) {

    }

    public class Error extends NetworkState {
        public Error(String message) {
            super(message);
        }
    }

    public class Loading extends NetworkState {
        public Loading() {

        }
    }
    public class Success extends NetworkState {
        public Success(T data) {
            super(data);
        }
    }

}
