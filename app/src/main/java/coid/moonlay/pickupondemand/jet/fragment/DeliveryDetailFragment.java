package coid.moonlay.pickupondemand.jet.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.model.Task;

public class DeliveryDetailFragment extends TaskDetailFragment
{
    private final static String TASK_ARGS_PARAM = "TaskParam";
    private Task mTask;

    public DeliveryDetailFragment()
    {

    }

    public static DeliveryDetailFragment newInstance(Task task)
    {
        DeliveryDetailFragment fragment = new DeliveryDetailFragment();
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
            mTask = getArguments().getParcelable(TASK_ARGS_PARAM);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        showSingleContactButton();
        showDeliveryStatusButton();
        setLabel(Utility.Message.get(R.string.delivery_detail_label));
    }

    @NonNull
    @Override
    protected Task getTask()
    {
        return mTask;
    }
}
