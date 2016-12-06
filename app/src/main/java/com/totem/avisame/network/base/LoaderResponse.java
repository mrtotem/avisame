package com.totem.avisame.network.base;

public class LoaderResponse<T> {
    private T response;
    private Error error;

    public LoaderResponse(T response) {
        this.response = response;
    }

    public LoaderResponse(Error error) {
        this.error = error;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
