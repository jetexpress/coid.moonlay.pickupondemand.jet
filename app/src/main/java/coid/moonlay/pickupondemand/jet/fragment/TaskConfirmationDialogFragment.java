package coid.moonlay.pickupondemand.jet.fragment;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import coid.moonlay.pickupondemand.jet.JetApplication;
import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.base.BaseDialogFragment;
import coid.moonlay.pickupondemand.jet.model.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskConfirmationDialogFragment extends BaseDialogFragment
{
    private static final String TASK_PARAM = "TaskParam";

    private TextView tv_title, tv_label_name, tv_name, tv_label_address, tv_address,
            tv_label_address_detail, tv_address_detail, tv_payment_method, tv_time_left;
    private ImageView img_vehicle;
    private Button btn_ok, btn_cancel;
    private ITaskConfirmationOnClickListener mConfirmationOnClickListener;
    private Task mTask;

    public TaskConfirmationDialogFragment()
    {
        // Required empty public constructor
    }

    public static TaskConfirmationDialogFragment newInstance(Task task)
    {
        TaskConfirmationDialogFragment dialogFragment = new TaskConfirmationDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(TASK_PARAM, task);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.CustomDialog);
        if (getArguments() != null)
            mTask = getArguments().getParcelable(TASK_PARAM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_confirmation_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setValue();
        setEvent();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog d = getDialog();
        if (d != null && d.getWindow() != null)
        {
            d.getWindow().setGravity(Gravity.CENTER);
            d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            d.setCancelable(false);
            d.setCanceledOnTouchOutside(false);
        }
        startCountdownTimer();
        JetApplication.getInstance().stopPickupNotificationSound();
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if (context instanceof ITaskConfirmationOnClickListener)
            mConfirmationOnClickListener = (ITaskConfirmationOnClickListener) context;
    }

    @Override
    public void onDestroy()
    {
        if (mConfirmationOnClickListener != null)
            mConfirmationOnClickListener = null;
        super.onDestroy();
    }

    private void setView()
    {
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_label_name = (TextView) findViewById(R.id.tv_label_name);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_label_address = (TextView) findViewById(R.id.tv_label_address);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_label_address_detail = (TextView) findViewById(R.id.tv_label_address_detail);
        tv_address_detail = (TextView) findViewById(R.id.tv_address_detail);
        tv_payment_method = (TextView) findViewById(R.id.tv_payment_method);
        tv_time_left = (TextView) findViewById(R.id.tv_time_left);
        img_vehicle = (ImageView) findViewById(R.id.img_vehicle);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
    }

    private void setValue()
    {
        tv_name.setText(mTask.getName());
        tv_address.setText(mTask.getAddress());
        tv_payment_method.setText(mTask.getPaymentMethod());
        if (mTask.isCar())
            img_vehicle.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_truck_white));
        else
            img_vehicle.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_motorcycle_white));
    }

    private void setEvent()
    {
        btn_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
                if (mConfirmationOnClickListener != null)
                    mConfirmationOnClickListener.onAcceptTaskFromNotification(mTask);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dismiss();
            }
        });
    }

    private void startCountdownTimer()
    {
        if (mTask.getCountDownStartTimeInMillis() > 0)
        {
            CountDownTimer pickupCountDownTimer = new CountDownTimer(mTask.getCountDownStartTimeInMillis(), 1000L)
            {
                @Override
                public void onTick(long millisUntilFinished)
                {
                    tv_time_left.setText(String.valueOf(millisUntilFinished / 1000));
                }

                @Override
                public void onFinish()
                {
                    dismiss();
                }
            };
            pickupCountDownTimer.start();
        }
        else
            dismiss();
    }

    public interface ITaskConfirmationOnClickListener
    {
        void onAcceptTaskFromNotification(Task task);
    }
}