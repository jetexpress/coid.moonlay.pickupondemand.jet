package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;
import android.util.Log;

import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.activity.MainActivity;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.CourierProfile;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class CourierProfileRequest extends BaseNetworkRequest<CourierProfile>
{

    public CourierProfileRequest(Context context)
    {
        super(context);
    }

    @Override
    protected Call<CourierProfile> getCall()
    {
        return RetrofitProvider.getAuthorizedAuthService().getCourierProfile();
    }

    @Override
    protected void onStartOnUIThread()
    {

    }

    @Override
    protected void onSuccessOnUIThread(Response<CourierProfile> response)
    {
        try
        {
            if (response.body().getHasTripStarted())
                ((MainActivity) getContext()).requestSendLocationLog();
            else
                ((MainActivity) getContext()).cancelSendLocationLog();
            Utility.OneSignal.sendTags(response.body());
        }
        catch (Exception ex)
        {
            Log.d("JET_127", ex.getMessage());
        }
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        executeAsync();
    }
}
