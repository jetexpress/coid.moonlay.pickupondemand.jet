package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.fragment.UpdateRequiredDialogFragment;
import coid.moonlay.pickupondemand.jet.model.UpdateInfo;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class UpdateInfoRequest extends BaseNetworkRequest<UpdateInfo>
{
    private int mVersion;

    public UpdateInfoRequest(Context context, int version)
    {
        super(context);
        mVersion = version;
    }

    @Override
    protected Call<UpdateInfo> getCall()
    {
        return RetrofitProvider.getResourcesService().getUpdateInfo(mVersion);
    }

    @Override
    protected void showLoadingDialog()
    {

    }

    @Override
    protected void hideLoadingDialog()
    {

    }

    @Override
    protected void onTimeOutOnNetworkThread()
    {
        executeAsync();
    }
}
