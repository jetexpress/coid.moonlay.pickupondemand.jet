package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;

public class PickupRequest extends BaseNetworkRequest<Pickup>
{
    private String mPickupCode;

    public PickupRequest(Context context, String pickupCode)
    {
        super(context);
        mPickupCode = pickupCode;
    }

    @Override
    protected Call<Pickup> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().getPickupByCode(mPickupCode);
    }
}
