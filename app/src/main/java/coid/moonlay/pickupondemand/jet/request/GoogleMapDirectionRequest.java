package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.map.Direction;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class GoogleMapDirectionRequest extends BaseNetworkRequest<Direction>
{
    private String mOrigin;
    private String mDestination;

    public GoogleMapDirectionRequest(Context context, String origin, String destination)
    {
        super(context);
        mOrigin = origin;
        mDestination = destination;
    }

    @Override
    protected Call<Direction> getCall()
    {
        return RetrofitProvider.getMapService().getDirection(mOrigin, mDestination);
    }

    @Override
    protected void onResponseFailedOnUIThread(Response<Direction> response)
    {
        super.onResponseFailedOnUIThread(response);
        showToast(Utility.Message.getResponseMessage(R.string.failed_to_get_direction, response));
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        showToast(Utility.Message.getNetworkFailureMessage(R.string.failed_to_get_direction, ex));
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        showToast(Utility.Message.get(R.string.request_timed_out));
    }
}
