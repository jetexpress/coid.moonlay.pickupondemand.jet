package coid.moonlay.pickupondemand.jet.network;

import retrofit2.Response;

public interface INetworkRequestCallback<T>
{
    void onStart();
    void onSuccess(Response<T> response);
    void onResponseFailed(Response<T> response);
    void onFailed(Exception ex);
    void onTimeOut();
}
