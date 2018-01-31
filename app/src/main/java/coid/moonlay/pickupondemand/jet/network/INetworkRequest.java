package coid.moonlay.pickupondemand.jet.network;

public interface INetworkRequest<T>
{
    INetworkRequestCallback<T> getCallback();
    void setCallback(INetworkRequestCallback<T> networkRequestCallback);
    void execute();
}