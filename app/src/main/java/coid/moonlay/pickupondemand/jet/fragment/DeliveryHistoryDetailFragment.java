package coid.moonlay.pickupondemand.jet.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.model.Delivery;
import coid.moonlay.pickupondemand.jet.model.DeliveryHistory;
import coid.moonlay.pickupondemand.jet.model.Task;
import coid.moonlay.pickupondemand.jet.request.DeliveryRequest;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryHistoryDetailFragment extends TaskHistoryDetailFragment
{
    private static final String TASK_ARGS_PARAM = "TaskParam";
    private Task mTask;
    private DeliveryRequest mDeliveryRequest;

    public DeliveryHistoryDetailFragment()
    {
        // Required empty public constructor
    }

    public static DeliveryHistoryDetailFragment newInstance(Task task)
    {
        DeliveryHistoryDetailFragment fragment = new DeliveryHistoryDetailFragment();
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

            mDeliveryRequest = new DeliveryRequest(mContext, mTask.getDrsCode(), mTask.getCode())
            {
                @Override
                protected void onSuccessOnUIThread(Response<Delivery> response)
                {
                    if (response.body() != null)
                    {
                        showContent();
                        updateDeliveryData(response.body());
                    }
                    else
                        showContent(false);
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
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setLabel(Utility.Message.get(R.string.delivery_detail_label));
        if (mDeliveryRequest != null)
            mDeliveryRequest.executeAsync();
    }

    @Override
    protected void onRetry()
    {
        super.onRetry();
        if (mDeliveryRequest != null)
            mDeliveryRequest.executeAsync();
    }

    @Override
    protected Task getTask()
    {
        return mTask;
    }
}
