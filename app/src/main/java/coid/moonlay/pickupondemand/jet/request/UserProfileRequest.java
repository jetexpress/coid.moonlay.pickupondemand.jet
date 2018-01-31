package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.activity.MainActivity;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.Login;
import coid.moonlay.pickupondemand.jet.model.UserProfile;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class UserProfileRequest extends BaseNetworkRequest<UserProfile>
{
    public UserProfileRequest()
    {
        this(null);
    }

    public UserProfileRequest(Context context)
    {
        super(context);
    }

    @Override
    protected Call<UserProfile> getCall()
    {
        return RetrofitProvider.getAuthorizedAuthService().getUserProfile();
    }

    @Override
    protected void onSuccessOnNetworkThread(Response<UserProfile> response)
    {
        DBQuery.truncate(UserProfile.class);
        UserProfile userProfile = response.body();
        userProfile.save();
    }

    @Override
    protected void onTimeOutOnNetworkThread()
    {
        execute();
    }

    @Override
    protected void onStartOnUIThread()
    {

    }

    @Override
    protected void onSuccessOnUIThread(Response<UserProfile> response)
    {
        super.onSuccessOnUIThread(response);
        getBaseActivity().finish();
        getBaseActivity().getNavigator().navigateToActivity(MainActivity.class);
    }

    @Override
    protected void onResponseFailedOnUIThread(Response<UserProfile> response)
    {
        super.onResponseFailedOnUIThread(response);
        DBQuery.truncate(Login.class);
        showToast(Utility.Message.getResponseFailedMessage(R.string.user_profile_get_failed, response));
//        getBaseActivity().finish();
//        getBaseActivity().getNavigator().navigateToActivity(MainActivity.class);
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        DBQuery.truncate(Login.class);
        showToast(Utility.Message.getNetworkFailureMessage(R.string.user_profile_get_failed, ex));
//        getBaseActivity().finish();
//        getBaseActivity().getNavigator().navigateToActivity(MainActivity.class);
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        DBQuery.truncate(Login.class);
        showToast(Utility.Message.get(R.string.request_timed_out));
//        getBaseActivity().finish();
//        getBaseActivity().getNavigator().navigateToActivity(MainActivity.class);
    }
}
