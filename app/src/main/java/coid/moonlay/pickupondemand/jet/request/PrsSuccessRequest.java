package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.fragment.TaskFragment;
import coid.moonlay.pickupondemand.jet.model.SuccessDelivery;
import coid.moonlay.pickupondemand.jet.model.SuccessPRS;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jordan.leonardi on 3/5/2018.
 */

public class PrsSuccessRequest extends BaseNetworkRequest<Void> {
    String mCrsItemCode;
    SuccessPRS mSuccessPrs;

    public PrsSuccessRequest(Context context, String crsItemCode, SuccessPRS successPRS)
    {
        super(context);
        mCrsItemCode = crsItemCode;
        mSuccessPrs = successPRS;
    }

    @Override
    protected Call<Void> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().submitSuccessPrs(mCrsItemCode, mSuccessPrs);
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
