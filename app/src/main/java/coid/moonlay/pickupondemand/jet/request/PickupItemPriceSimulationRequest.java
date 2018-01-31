package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.PickupItemSimulation;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;

public class PickupItemPriceSimulationRequest extends BaseNetworkRequest<PickupItemSimulation>
{
    private PickupItemSimulation mPickupItemSimulation;

    public PickupItemPriceSimulationRequest(Context context, PickupItemSimulation pickupItemSimulation)
    {
        super(context);
        mPickupItemSimulation = pickupItemSimulation;
    }

    @Override
    public Call<PickupItemSimulation> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().simulatePrice(mPickupItemSimulation);
    }
}