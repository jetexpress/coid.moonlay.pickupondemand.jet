package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.fragment.TaskFragment;
import coid.moonlay.pickupondemand.jet.model.SuccessDelivery;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class DeliverySuccessRequest extends BaseNetworkRequest<Void>
{
    String mDrsCode;
    String mAwbNumber;
    SuccessDelivery mSuccessDelivery;

    public DeliverySuccessRequest(Context context, String drsCode, String awbNumber, SuccessDelivery successDelivery)
    {
        super(context);
        mDrsCode = drsCode;
        mAwbNumber = awbNumber;
        mSuccessDelivery = successDelivery;
    }

    @Override
    protected Call<Void> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().submitSuccessDelivery(mDrsCode, mAwbNumber, mSuccessDelivery);
    }

    @Override
    protected void onSuccessOnUIThread(Response<Void> response)
    {
        super.onSuccessOnUIThread(response);
        showToast("Submit Sukses");
        getBaseActivity().getNavigator().popToDefaultFragment();
        getBaseActivity().getNavigator().showFragment(new TaskFragment());

    }

    @Override
    protected void onResponseFailedOnUIThread(Response<Void> response)
    {
        super.onResponseFailedOnUIThread(response);
        showToast(Utility.Message.getResponseMessage(R.string.failed_to_submit, response));
    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        showToast(Utility.Message.getNetworkFailureMessage(R.string.failed_to_submit, ex));
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        showToast(Utility.Message.get(R.string.request_timed_out));
    }
}


