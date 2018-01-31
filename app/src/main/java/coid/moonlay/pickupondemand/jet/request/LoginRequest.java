package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import org.json.JSONObject;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.model.Login;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class LoginRequest extends BaseNetworkRequest<Login>
{
    private String mUsername;
    private String mPassword;

    public LoginRequest(String username, String password)
    {
        this(null, username, password);
    }

    public LoginRequest(Context context, String username, String password)
    {
        super(context);
        mUsername = username;
        mPassword = password;
    }

    @Override
    protected Call<Login> getCall()
    {
        return RetrofitProvider.getAuthService().login(mUsername, mPassword, ApiConfig.LOGIN_CLIENT_ID, ApiConfig.LOGIN_GRANT_TYPE);
    }

    @Override
    protected void onSuccessOnNetworkThread(Response<Login> response)
    {
        super.onSuccessOnNetworkThread(response);
        DBQuery.truncate(Login.class);
        Login login = response.body();
        login.save();
    }

    @Override
    protected void onSuccessOnUIThread(Response<Login> response)
    {
        UserProfileRequest userProfileRequest = new UserProfileRequest(getContext());
        userProfileRequest.executeAsync();
    }

    @Override
    protected void onResponseFailedOnUIThread(Response<Login> response)
    {
        if (Utility.Message.responseFailedMessage.contains(ApiConfig.LOGIN_INVALID_USERNAME_OR_PASSWORD))
            showToast(Utility.Message.get(R.string.login_invalid_username_or_password));
        else if (Utility.Message.responseFailedMessage.contains(ApiConfig.LOGIN_UNAUTHORIZED))
            showToast(Utility.Message.get(R.string.login_unauthorized));
        else
            showToast(Utility.Message.getResponseFailedMessage(R.string.login_failed, response));

        super.onResponseFailedOnUIThread(response);
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        showToast(Utility.Message.getNetworkFailureMessage(R.string.login, ex));
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        showToast(Utility.Message.get(R.string.request_timed_out));
    }
}
