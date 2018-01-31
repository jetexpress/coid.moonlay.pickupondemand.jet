package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.model.PickupItem;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class PickupCreateWaybillRequest extends BaseNetworkRequest<PickupItem>
{
    private String mPickupCode;
    private PickupItem mPickupItem;

    public PickupCreateWaybillRequest(Context context, String pickupCode, PickupItem pickupItem)
    {
        super(context);
        mPickupCode = pickupCode;
        mPickupItem = pickupItem;
    }

    @Override
    protected Call<PickupItem> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().createWaybill(mPickupCode, mPickupItem.getCode(), mPickupItem);
    }

    @Override
    protected void onResponseFailedOnUIThread(Response<PickupItem> response)
    {
        super.onResponseFailedOnUIThread(response);
        try
        {
            if (Utility.Message.getResponseFailedMessage(R.string.failed_to_create_waybill, response).contains(ApiConfig.INVALID_STATUS_COMPLETED))
                onSuccessOnUIThread(response);
            else
                showToast(Utility.Message.getResponseFailedMessage(R.string.failed_to_create_waybill, response));
        }
        catch (Exception ex)
        {
            showToast(Utility.Message.getResponseFailedMessage(R.string.failed_to_create_waybill, response));
        }
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        showToast(Utility.Message.getNetworkFailureMessage(R.string.failed_to_create_waybill, ex));
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        showToast(Utility.Message.get(R.string.request_timed_out));
    }
}