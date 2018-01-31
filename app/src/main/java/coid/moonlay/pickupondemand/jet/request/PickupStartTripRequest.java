package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.activity.MainActivity;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class PickupStartTripRequest extends BaseNetworkRequest<Pickup>
{
    private String mPickupCode;
    private CourierProfileRequest mCourierProfileRequest;

    public PickupStartTripRequest(Context context, String pickupCode)
    {
        super(context);
        mPickupCode = pickupCode;
    }

    @Override
    protected Call<Pickup> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().startTrip(mPickupCode);
    }

    @Override
    protected void onSuccessOnUIThread(Response<Pickup> response)
    {
        super.onSuccessOnUIThread(response);
        requestCourierProfile();
    }

    @Override
    protected void onResponseFailedOnUIThread(Response<Pickup> response)
    {
        super.onResponseFailedOnUIThread(response);
        try
        {
            if (Utility.Message.getResponseFailedMessage(R.string.failed_to_start_trip, response).contains(ApiConfig.HAS_TRIP_STARTED))
                showToast(Utility.Message.get(R.string.pickup_has_trip_started_message));
            else
                Utility.Message.getResponseMessage(R.string.failed_to_start_trip, response);
        }
        catch (Exception ex)
        {
            Utility.Message.getResponseMessage(R.string.failed_to_start_trip, response);
        }
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        showToast(Utility.Message.getNetworkFailureMessage(R.string.failed_to_start_trip, ex));
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        showToast(Utility.Message.get(R.string.request_timed_out));
    }

    @Override
    public void clear()
    {
        if (mCourierProfileRequest != null)
        {
            mCourierProfileRequest.clear();
            mCourierProfileRequest = null;
        }
        super.clear();
    }

    private void requestCourierProfile()
    {
        mCourierProfileRequest = new CourierProfileRequest(getContext());
        mCourierProfileRequest.executeAsync();
    }
}
