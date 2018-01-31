package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.activity.MainActivity;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class PickupCancelRequest extends BaseNetworkRequest<Pickup>
{
    private String mPickupCode;
    private CourierProfileRequest mCourierProfileRequest;

    public PickupCancelRequest(Context context, String pickupCode)
    {
        super(context);
        mPickupCode = pickupCode;
    }

    @Override
    protected Call<Pickup> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().cancelTrip(mPickupCode);
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
        showToast(Utility.Message.getResponseMessage(R.string.failed_to_cancel_trip, response));
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        showToast(Utility.Message.getNetworkFailureMessage(R.string.failed_to_cancel_trip, ex));
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
