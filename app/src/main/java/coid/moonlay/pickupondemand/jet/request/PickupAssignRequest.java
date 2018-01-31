package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import org.json.JSONObject;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class PickupAssignRequest extends BaseNetworkRequest<Pickup>
{
    private String mCode;

    public PickupAssignRequest(Context context, String code)
    {
        super(context);
        mCode = code;
    }

    @Override
    protected Call<Pickup> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().assignPickup(mCode);
    }

    @Override
    protected void onResponseFailedOnUIThread(Response<Pickup> response)
    {
        super.onResponseFailedOnUIThread(response);
        try
        {
            if (Utility.Message.getResponseFailedMessage(R.string.failed_to_accept_task, response).contains(ApiConfig.TASK_ALREADY_ASSIGNED))
                showToast(Utility.Message.get(R.string.notification_task_already_assigned));
            else
                Utility.Message.getResponseMessage(R.string.failed_to_accept_task, response);
        }
        catch (Exception ex)
        {
            Utility.Message.getResponseMessage(R.string.failed_to_accept_task, response);
        }
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        Utility.Message.getNetworkFailureMessage(R.string.failed_to_accept_task, ex);
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        showToast(Utility.Message.get(R.string.failed_to_accept_task_request_timed_out));
    }
}
