package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.PickupItem;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class PickupItemUnlockRequestByUnlockCode extends BaseNetworkRequest<PickupItem>
{
    private String mPickupCode;
    private String mUnlockCode;

    public PickupItemUnlockRequestByUnlockCode(Context context, String pickupCode, String unlockCode)
    {
        super(context);
        mPickupCode = pickupCode;
        mUnlockCode = unlockCode;
    }

    @Override
    protected Call<PickupItem> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().unlockPickupItemByUnlockCode(mPickupCode, mUnlockCode);
    }

    @Override
    protected void onResponseFailedOnUIThread(Response<PickupItem> response)
    {
        super.onResponseFailedOnUIThread(response);
        showToast(Utility.Message.get(R.string.failed_to_unlock_pickup_item));
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        showToast(Utility.Message.get(R.string.failed_to_unlock_pickup_item));
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        showToast(Utility.Message.get(R.string.request_timed_out));
    }
}
