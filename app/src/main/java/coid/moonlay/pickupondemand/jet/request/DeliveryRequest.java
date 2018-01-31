package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.Delivery;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;

public class DeliveryRequest extends BaseNetworkRequest<Delivery>
{
    private String mDrsCode;
    private String mAwbNumber;

    public DeliveryRequest(Context context, String drsCode, String awbNumber)
    {
        super(context);
        mDrsCode = drsCode;
        mAwbNumber = awbNumber;
    }

    @Override
    protected Call<Delivery> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().getDeliveryByCode(mDrsCode, mAwbNumber);
    }


}
