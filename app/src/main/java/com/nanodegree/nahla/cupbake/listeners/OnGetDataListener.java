package com.nanodegree.nahla.cupbake.listeners;

public interface OnGetDataListener<T,D> {
    void onSuccess(T data);
    void onFailed(D errorMsg);
}
