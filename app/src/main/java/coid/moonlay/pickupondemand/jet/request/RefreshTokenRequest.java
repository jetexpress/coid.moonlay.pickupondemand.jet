package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.activity.LoginActivity;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.model.Login;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class RefreshTokenRequest extends BaseNetworkRequest<Login>
{
    private BaseNetworkRequest mUnauthorizedRequest;

    public RefreshTokenRequest(Context context, BaseNetworkRequest unauthorizedRequest)
    {
        super(context);
        mUnauthorizedRequest = unauthorizedRequest;
    }

    @Override
    protected Call<Login> getCall()
    {
        Login login = DBQuery.getSingle(Login.class);
        if (login == null)
        {
            navigateToLogin();
            return null;
        }

        return RetrofitProvider.getAuthService().refresh(login.getRefreshToken(), ApiConfig.LOGIN_CLIENT_ID, ApiConfig.REFRESH_GRANT_TYPE);
    }

    @Override
    protected void onSuccessOnNetworkThread(Response<Login> response)
    {
        DBQuery.truncate(Login.class);
        Login login = response.body();
        login.save();

        if (getContext() == null && mUnauthorizedRequest != null)
            mUnauthorizedRequest.execute();
    }

    @Override
    protected void onStartOnUIThread(){}

    @Override
    protected void onSuccessOnUIThread(Response<Login> response)
    {
        if (mUnauthorizedRequest != null)
            mUnauthorizedRequest.executeAsync();
    }

    @Override
    protected void onResponseFailedOnUIThread(Response<Login> response)
    {
        navigateToLogin();
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        navigateToLogin();
    }

    private void navigateToLogin()
    {
        if (getBaseActivity() == null)
            return;

        showToast("Login expired, silakan Login kembali");
        getBaseActivity().getNavigator().navigateToActivity(LoginActivity.class);
        getBaseActivity().finish();
    }
}
