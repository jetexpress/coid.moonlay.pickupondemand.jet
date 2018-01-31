package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.model.Login;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class LoginExternalRequest extends BaseNetworkRequest<Login>
{
    public static final String PROVIDER_FACEBOOK = "facebook";
    public static final String PROVIDER_GOOGLE = "google";
    @StringDef({PROVIDER_FACEBOOK, PROVIDER_GOOGLE})
    public @interface Provider {}

    private String mAccessToken;
    private String mProvider;

    /** @param provider One of {@link #PROVIDER_FACEBOOK}, {@link #PROVIDER_GOOGLE}.*/
    public LoginExternalRequest(Context context, String accessToken, @Provider String provider)
    {
        super(context);
        mAccessToken = accessToken;
        mProvider = provider;
    }

    @Override
    protected Call<Login> getCall()
    {
        return RetrofitProvider.getAuthorizedAuthService().loginFacebook(mAccessToken, mProvider);
    }

    @Override
    protected void onSuccessOnNetworkThread(Response<Login> response)
    {
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
        super.onResponseFailedOnUIThread(response);
        showToast(Utility.Message.getResponseMessage(R.string.login_failed, response));
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        showToast(Utility.Message.getNetworkFailureMessage(R.string.login_failed, ex));
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        showToast(Utility.Message.get(R.string.request_timed_out));
    }
}
