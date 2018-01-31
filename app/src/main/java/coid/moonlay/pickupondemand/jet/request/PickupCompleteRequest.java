package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.activity.MainActivity;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.fragment.TaskFragment;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class PickupCompleteRequest extends BaseNetworkRequest<Pickup>
{
    private String mPickupCode;

    public PickupCompleteRequest(Context context, String pickupCode)
    {
        super(context);
        mPickupCode = pickupCode;
    }

    @Override
    protected Call<Pickup> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().completePickup(mPickupCode);
    }

    @Override
    protected void onSuccessOnUIThread(Response<Pickup> response)
    {
        super.onSuccessOnUIThread(response);
        if (getContext() instanceof MainActivity)
            ((MainActivity)getContext()).cancelSendLocationLog();

        showToast(Utility.Message.get(R.string.success));
        getBaseActivity().getNavigator().popToDefaultFragment();
        getBaseActivity().getNavigator().showFragment(new TaskFragment());
    }

    @Override
    protected void onResponseFailedOnUIThread(Response<Pickup> response)
    {
        super.onResponseFailedOnUIThread(response);
        try
        {
            if (Utility.Message.getResponseFailedMessage(R.string.failed_to_complete_pickup, response).contains(ApiConfig.INVALID_STATUS_COMPLETED))
                onSuccessOnUIThread(response);
            else
                showToast(Utility.Message.getResponseFailedMessage(R.string.failed_to_complete_pickup, response));
        }
        catch (Exception ex)
        {
            showToast(Utility.Message.getResponseMessage(R.string.failed_to_complete_pickup, response));
        }
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        showToast(Utility.Message.getNetworkFailureMessage(R.string.failed_to_complete_pickup, ex));
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        showToast(Utility.Message.get(R.string.request_timed_out));
    }
}
