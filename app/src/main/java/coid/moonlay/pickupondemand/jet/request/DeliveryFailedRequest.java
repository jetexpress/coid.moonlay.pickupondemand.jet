package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.fragment.TaskFragment;
import coid.moonlay.pickupondemand.jet.model.FailedDelivery;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

public class DeliveryFailedRequest extends BaseNetworkRequest<Void>
{
    String mDrsCode;
    String mAwbNumber;
    FailedDelivery mFailedDelivery;

    public DeliveryFailedRequest(Context context, String drsCode, String awbNumber, FailedDelivery failedDelivery)
    {
        super(context);
        mDrsCode = drsCode;
        mAwbNumber = awbNumber;
        mFailedDelivery = failedDelivery;
    }

    @Override
    protected Call<Void> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().submitFailedDelivery(mDrsCode, mAwbNumber, mFailedDelivery);
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
        String message = Utility.Message.getResponseMessage(R.string.failed_to_submit, response);
        showToast(message);

    }

    @Override
    protected void onFailedOnUIThread(Exception ex)
    {
        super.onFailedOnUIThread(ex);
        String message = Utility.Message.getNetworkFailureMessage(R.string.failed_to_submit, ex);
        showToast(message);
    }

    @Override
    protected void onTimeOutOnUIThread()
    {
        super.onTimeOutOnUIThread();
        showToast(Utility.Message.get(R.string.request_timed_out));
    }
}
