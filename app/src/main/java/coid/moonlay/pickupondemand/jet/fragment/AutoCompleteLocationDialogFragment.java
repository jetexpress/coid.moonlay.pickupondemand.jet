package coid.moonlay.pickupondemand.jet.fragment;

import java.util.ArrayList;
import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseItemSelectDialogFragment;
import coid.moonlay.pickupondemand.jet.model.Location;
import coid.moonlay.pickupondemand.jet.request.AutoCompleteLocationRequest;
import retrofit2.Response;

public class AutoCompleteLocationDialogFragment extends BaseItemSelectDialogFragment<Location>
{
    private AutoCompleteLocationRequest mAutoCompleteLocationRequest;

    @Override
    public void onResume()
    {
        super.onResume();
        showFilter();
    }

    @Override
    public void onStop()
    {
        if (mAutoCompleteLocationRequest != null)
            mAutoCompleteLocationRequest.cancel();
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        if (mAutoCompleteLocationRequest != null)
        {
            mAutoCompleteLocationRequest.clear();
            mAutoCompleteLocationRequest = null;
        }
        super.onDestroy();
    }

    @Override
    protected String getTitle()
    {
        return Utility.Message.get(R.string.waybill_consignee_choose_location);
    }

    @Override
    protected void doFilter(String filter)
    {
        if (mAutoCompleteLocationRequest != null && mAutoCompleteLocationRequest.isExecuting())
            mAutoCompleteLocationRequest.cancel();

        if (filter == null || filter.isEmpty())
        {
            clearData();
            showContent();
            return;
        }

        mAutoCompleteLocationRequest = new AutoCompleteLocationRequest(mContext, filter)
        {
            @Override
            protected void showLoadingDialog()
            {
                showProgressBar();
            }

            @Override
            protected void hideLoadingDialog()
            {
                showRetry(R.string.request_timed_out);
            }

            @Override
            protected void onSuccessOnUIThread(Response<List<Location>> response)
            {
                updateData(response.body());
                showContent(response.body().size() > 0);
            }
        };
        mAutoCompleteLocationRequest.executeAsync();
    }

    @Override
    protected void onRetry()
    {
        doFilter(getFilter());
    }
}
