package coid.moonlay.pickupondemand.jet.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseItemSelectDialogFragment;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.model.FailedDelivery;
import coid.moonlay.pickupondemand.jet.model.OperationStatus;
import coid.moonlay.pickupondemand.jet.model.Task;
import coid.moonlay.pickupondemand.jet.request.DeliveryFailedRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class FailedDeliveryFragment extends BaseMainFragment
{
    private static final int OPERATION_STATUS_REQUEST_CODE = 310;
    public static final String TASK_ARGS_PARAM = "TaskParam";
    private Button btn_finish;
    private TextInputLayout input_layout_operation_status;
    private TextInputEditText et_operation_status;
    private CheckBox checkbox_retry;
    private EditText et_note;

    private OperationStatus mOperationStatus;
    private Task mTask;

    public FailedDeliveryFragment()
    {
        // Required empty public constructor
    }

    public static FailedDeliveryFragment newInstance(Task task)
    {
        FailedDeliveryFragment fragment = new FailedDeliveryFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_failed_delivery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setEvent();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setTitle(Utility.Message.get(R.string.task_failed_delivery));
        setNotificationMenuEnabled(false);
        setDisplayBackEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            CharSequence code = data.getStringExtra(BaseItemSelectDialogFragment.SELECTED_ITEM_CODE_ARGS_PARAM);
            CharSequence description = data.getStringExtra(BaseItemSelectDialogFragment.SELECTED_ITEM_DESCRIPTION_ARGS_PARAM);
            switch (requestCode)
            {
                case OPERATION_STATUS_REQUEST_CODE :
                    mOperationStatus = new OperationStatus(code.toString(), description.toString());
                    et_operation_status.setText(mOperationStatus.getName());
                    break;
                default: break;
            }
        }
    }
    private void setView()
    {
        btn_finish = (Button) findViewById(R.id.btn_finish);
        input_layout_operation_status = (TextInputLayout) findViewById(R.id.input_layout_operation_status);
        et_operation_status = (TextInputEditText) findViewById(R.id.et_operation_status);
        checkbox_retry = (CheckBox) findViewById(R.id.checkbox_retry);
        et_note = (EditText) findViewById(R.id.et_note);
    }

    private void setEvent()
    {
        et_operation_status.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                OperationStatusSelectDialogFragment dialog = new OperationStatusSelectDialogFragment();
                dialog.setTargetFragment(mFragment, OPERATION_STATUS_REQUEST_CODE);
                dialog.show(getFragmentManager(), OperationStatusSelectDialogFragment.class.getSimpleName());
            }
        });
        et_operation_status.addTextChangedListener(Utility.Validation.getValidateEmptyTextWatcher(input_layout_operation_status, et_operation_status));
        btn_finish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                submit();
            }
        });
    }

    private Boolean isValid()
    {
        Integer errorCount = 0;
        if (Utility.Validation.isEditTextEmpty(input_layout_operation_status, et_operation_status))
            errorCount++;

        return errorCount <= 0;
    }

    private void submit()
    {
        if (!isValid())
            return;

        FailedDelivery failedDelivery = new FailedDelivery();
        failedDelivery.setNote(et_note.getText().toString().trim());
        failedDelivery.setOperationCode(mOperationStatus.getCode());
        failedDelivery.setRetry(checkbox_retry.isChecked());

        DeliveryFailedRequest deliveryFailedRequest = new DeliveryFailedRequest(mContext, mTask.getDrsCode(), mTask.getCode(), failedDelivery);
        deliveryFailedRequest.executeAsync();
    }
}
