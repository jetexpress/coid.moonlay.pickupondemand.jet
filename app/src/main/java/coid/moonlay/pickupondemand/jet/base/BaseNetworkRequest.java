package coid.moonlay.pickupondemand.jet.base;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.request.RefreshTokenRequest;
import retrofit2.Call;
import retrofit2.Response;

public abstract class BaseNetworkRequest<T>
{
    private Context mContext;
    private BaseActivity mBaseActivity;

    private Boolean mIsExecuting;
    private Boolean mIsSuccess;

    public BaseNetworkRequest(@Nullable Context context)
    {
        mContext = context;
        if (mContext != null && mContext instanceof BaseActivity)
            mBaseActivity = (BaseActivity) mContext;

        mIsExecuting = false;
        mIsSuccess = false;
    }

    public void execute()
    {
        mIsExecuting = true;

        if (getCall() == null)
        {
            if (isUIValid())
                showToast("Empty Call");
            return;
        }

//            if (isUIValid() && !Utility.NetworkConnectivity.isNetworkAvailable())
//            {
//                onTimeOutOnUIThread();
//                return;
//            }

        onStart();
        executeCall();
    }

    public void executeAsync()
    {
        Utility.Executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                execute();
            }
        });
    }

    private void executeCall()
    {
        try
        {
            final Response<T> response = getCall().execute();

            if (response.body() instanceof Void)
            {
                if (response.errorBody() == null && response.code() == HttpURLConnection.HTTP_OK)
                {
                    mIsSuccess = true;
                    onSuccess(response);
                    Log.d("NETWORK_127", this.getCall().request().url().toString() + ", SUCCESS");
                }
                else
                {
                    Utility.Message.setResponseFailedMessage(response); /** HAX00R */
                    onResponseFailed(response);
                    Log.d("NETWORK_127", response.raw().request().url().toString() + ", RESPONSE FAILED : " + Utility.Message.responseFailedMessage);
                }
            }
            else
            {
                if (response.body() != null)
                {
                    mIsSuccess = true;
                    onSuccess(response);
                    Log.d("NETWORK_127", this.getCall().request().url().toString() + ", SUCCESS");
                }
                else
                {
                    if (response.errorBody() == null && response.code() == HttpURLConnection.HTTP_OK)
                    {
                        mIsSuccess = true;
                        onSuccess(response);
                        Log.d("NETWORK_127", this.getCall().request().url().toString() + ", SUCCESS");
                    }
                    else
                    {
                        Utility.Message.setResponseFailedMessage(response); /** HAX00R */
                        onResponseFailed(response);
                        Log.d("NETWORK_127", response.raw().request().url().toString() + ", RESPONSE FAILED : " + Utility.Message.responseFailedMessage);
                    }
                }
            }
        }
        catch (final Exception ex)
        {
            if (isTimeout(ex))
            {
                onTimeOut();
                Log.d("NETWORK_127", this.getCall().request().url().toString() + ", TIMEOUT");
            }
            else
            {
                onFailed(ex);
                Log.d("NETWORK_127", this.getCall().request().url().toString() + ", FAILED");
            }
        }
        finally
        {
            mIsExecuting = false;
        }
    }

    private Boolean isTimeout(Throwable t)
    {
        return (t instanceof UnknownHostException ||
                t instanceof TimeoutException ||
                t instanceof SocketException ||
                t instanceof SocketTimeoutException);
    }

    public Boolean isSuccess()
    {
        return mIsSuccess;
    }

    public Boolean isExecuting()
    {
        return mIsExecuting;
    }

    public Boolean isIdle()
    {
        return !mIsExecuting;
    }

    private Boolean isUIValid()
    {
        return mBaseActivity != null && getBaseFragment() != null;
    }
    protected Context getContext()
    {
        return mContext;
    }

    protected String getString(Integer resId)
    {
        return mContext.getString(resId);
    }

    protected BaseActivity getBaseActivity()
    {
        return mBaseActivity;
    }

    protected BaseFragment getBaseFragment()
    {
        if (mBaseActivity != null)
        {
            FragmentManager fm = mBaseActivity.getSupportFragmentManager();
            Fragment fragment = fm.findFragmentById(mBaseActivity.getFragmentContainerId());
            if (fragment != null && fragment.isAdded() && fragment instanceof BaseFragment)
                return (BaseFragment) fragment;
        }
        return null;
    }

    protected void showLoadingDialog()
    {
        if (isUIValid())
            mBaseActivity.showLoadingDialog();
    }

    protected void hideLoadingDialog()
    {
        if (isUIValid())
            mBaseActivity.hideLoadingDialog();
    }

    protected void showToast(String message)
    {
        if (isUIValid())
            mBaseActivity.showToast(message);
    }

    private void onStart()
    {
        onStartOnNetworkThread();
        if (isUIValid())
            mBaseActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (isUIValid())
                        onStartOnUIThread();
                }
            });
    }
    private void onSuccess(final Response<T> response)
    {
        onSuccessOnNetworkThread(response);
        if (isUIValid())
            mBaseActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (isUIValid())
                        onSuccessOnUIThread(response);
                }
            });
    }
    private void onResponseFailed(final Response<T> response)
    {
        if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED)
        {
            RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(mContext, this);
            refreshTokenRequest.executeAsync();
        }
        else
        {
            onResponseFailedOnNetworkThread(response);
            if (isUIValid())
                mBaseActivity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (isUIValid())
                            onResponseFailedOnUIThread(response);
                    }
                });
        }
    }

    private void onFailed(final Exception ex)
    {
        onFailedOnNetworkThread(ex);
        if (isUIValid())
            mBaseActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (isUIValid())
                        onFailedOnUIThread(ex);
                }
            });
    }
    private void onTimeOut()
    {
        onTimeOutOnNetworkThread();
        if (isUIValid())
            mBaseActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    if (isUIValid())
                        onTimeOutOnUIThread();
                }
            });
    }

    public void cancel()
    {
        if (getCall() != null && !getCall().isCanceled())
            getCall().cancel();
    }

    public void clear()
    {
        cancel();
        mContext = null;
        mBaseActivity = null;
    }

    protected void onStartOnNetworkThread(){}
    protected void onSuccessOnNetworkThread(Response<T> response){}
    protected void onResponseFailedOnNetworkThread(Response<T> response){}
    protected void onFailedOnNetworkThread(Exception ex){}
    protected void onTimeOutOnNetworkThread(){}

    protected void onStartOnUIThread()
    {
        showLoadingDialog();
    }
    protected void onSuccessOnUIThread(Response<T> response)
    {
        hideLoadingDialog();
    }
    protected void onResponseFailedOnUIThread(Response<T> response)
    {
        hideLoadingDialog();
    }
    protected void onFailedOnUIThread(Exception ex)
    {
        hideLoadingDialog();
    }
    protected void onTimeOutOnUIThread()
    {
        hideLoadingDialog();
    }

    protected abstract Call<T> getCall();
}