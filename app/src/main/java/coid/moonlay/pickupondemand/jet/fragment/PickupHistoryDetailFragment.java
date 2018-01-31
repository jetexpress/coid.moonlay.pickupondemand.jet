package coid.moonlay.pickupondemand.jet.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.adapter.PickupItemAdapter;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.model.PickupItem;
import coid.moonlay.pickupondemand.jet.model.Task;
import coid.moonlay.pickupondemand.jet.request.PickupRequest;
import retrofit2.Response;

public class PickupHistoryDetailFragment extends TaskHistoryDetailFragment
{
    private static final String TASK_ARGS_PARAM = "TaskParam";
    private Task mTask;
    private PickupRequest mPickupRequest;
    private PickupItemAdapter mPickupItemAdapter;

    public static PickupHistoryDetailFragment newInstance(Task task)
    {
        PickupHistoryDetailFragment fragment = new PickupHistoryDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(TASK_ARGS_PARAM, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mTask = getArguments().getParcelable(TASK_ARGS_PARAM);
            if (mTask == null)
                return;

            mPickupRequest =  new PickupRequest(mContext, mTask.getCode())
            {
                @Override
                protected void onSuccessOnUIThread(Response<Pickup> response)
                {
                    showContent();
                    setUIState(response.body());
                }

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
            };

            mPickupItemAdapter = new PickupItemAdapter(mContext, new ArrayList<PickupItem>(), null);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setLabel(Utility.Message.get(R.string.pickup_detail_label));
        if (mPickupRequest != null)
            mPickupRequest.executeAsync();
    }

    @Override
    protected void onRetry()
    {
        super.onRetry();
        if (mPickupRequest != null)
            mPickupRequest.executeAsync();
    }

    @Override
    protected Task getTask()
    {
        return mTask;
    }

    @Override
    protected PickupItemAdapter getPickupItemAdapter()
    {
        return mPickupItemAdapter;
    }

    private void setUIState(Pickup pickup)
    {
        if (pickup == null || !pickup.hasPickupItems() || mPickupItemAdapter == null)
            return;

        mPickupItemAdapter.updateData(pickup.getPickupItemList());
        showPickupItemList(pickup);
        setTotal(pickup.getTotalFee());
    }
}
