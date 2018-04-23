package coid.moonlay.pickupondemand.jet.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.model.Task;

/**
 * Created by jordan.leonardi on 3/5/2018.
 */

public class PrsHistoryOutletDetailFragment extends TaskDetailFragment {
    private final static String TASK_ARGS_PARAM = "TaskParam";
    private Task mTask;

    public PrsHistoryOutletDetailFragment()
    {
    }

    public static PrsHistoryOutletDetailFragment newInstance(Task task)
    {
        PrsHistoryOutletDetailFragment fragment = new PrsHistoryOutletDetailFragment();
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
        hidePickupAndDRS();
        hideTvPickupAndDRS();
        setLabel(Utility.Message.get(R.string.pickup_run_sheet));
    }

    @NonNull
    @Override
    protected Task getTask()
    {
        return mTask;
    }
}
