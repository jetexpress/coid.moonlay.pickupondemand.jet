package coid.moonlay.pickupondemand.jet.utility;

import java.util.List;

import coid.moonlay.pickupondemand.jet.network.INetworkRequest;
import coid.moonlay.pickupondemand.jet.network.INetworkRequestCallback;
import retrofit2.Response;

public class ChainRequest
{
//    private List<INetworkRequest> mNetworkRequestList;
//    private Integer mCount;
//    private Integer mLastIndex;
//
//    private ChainRequest()
//    {
//        mCount = 0;
//    }
//
//    public ChainRequest(List<INetworkRequest> networkRequestList)
//    {
//        this();
//        mNetworkRequestList = networkRequestList;
//        mLastIndex = mNetworkRequestList != null ? mNetworkRequestList.size() - 1 : 0;
//    }
//
//    public void execute()
//    {
//        if (mNetworkRequestList.size() <= 0)
//            return;
//
//        INetworkRequest networkRequest = mNetworkRequestList.get(mCount);
//        INetworkRequestCallback callback = new NetworkRequestCallback(networkRequest.getCallback())
//        {
//            @Override
//            public void onSuccess(Response response)
//            {
//                super.onSuccess(response);
//                if (mCount < mLastIndex)
//                {
//                    mCount++;
//                    execute();
//                }
//            }
//        };
//
//        networkRequest.setCallback(callback);
//        networkRequest.executeAsync();
//    }
//
//    private abstract class NetworkRequestCallback<T> implements INetworkRequestCallback<T>
//    {
//        INetworkRequestCallback mOriginalCallback;
//
//        NetworkRequestCallback(INetworkRequestCallback originalCallback)
//        {
//            mOriginalCallback = originalCallback;
//        }
//
//        @Override
//        public void onStart()
//        {
//            mOriginalCallback.onStart();
//        }
//
//        @Override
//        public void onSuccess(Response response)
//        {
//            mOriginalCallback.onSuccess(response);
//        }
//
//        @Override
//        public void onResponseFailed(Response response)
//        {
//
//            mOriginalCallback.onResponseFailed(response);
//        }
//
//        @Override
//        public void onFailed(Exception ex)
//        {
//            mOriginalCallback.onFailed(ex);
//        }
//
//        @Override
//        public void onTimeOut()
//        {
//            mOriginalCallback.onTimeOut();
//        }
//    }
}
