package coid.moonlay.pickupondemand.jet.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.adapter.PickupItemAdapter;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.model.Delivery;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.model.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class TaskHistoryDetailFragment extends BaseMainFragment
{
    private TextView tv_task_type, tv_task_code, tv_status, tv_date, tv_label_info_detail, tv_label_name, tv_name,
            tv_label_phone, tv_phone, tv_label_address, tv_address, tv_pickup_item_list_label, tv_payment_method,
            tv_delivery_fee, tv_item_value, tv_total;
    private LinearLayout ll_content_container, ll_pickup_item_list_container, ll_cod_detail;

    public TaskHistoryDetailFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_history_detail, container, false);
    }
    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setValue();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setTitle(Utility.Message.get(R.string.task_history));
        setNotificationMenuEnabled(false);
        setDisplayBackEnabled(true);
    }

    @Override
    protected View getBaseContentLayout()
    {
        return ll_content_container;
    }

    private void setView()
    {
        tv_task_type = (TextView) findViewById(R.id.tv_task_type);
        tv_task_code = (TextView) findViewById(R.id.tv_task_code);
        tv_status = (TextView) findViewById(R.id.tv_status);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_label_info_detail = (TextView) findViewById(R.id.tv_label_info_detail);
        tv_label_name = (TextView) findViewById(R.id.tv_label_name);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_label_phone = (TextView) findViewById(R.id.tv_label_phone);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_label_address = (TextView) findViewById(R.id.tv_label_address);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_pickup_item_list_label = (TextView) findViewById(R.id.tv_pickup_item_list_label);
        tv_payment_method = (TextView) findViewById(R.id.tv_payment_method);
        tv_delivery_fee = (TextView) findViewById(R.id.tv_delivery_fee);
        tv_item_value = (TextView) findViewById(R.id.tv_item_value);
        tv_total = (TextView) findViewById(R.id.tv_total);
        ll_content_container = (LinearLayout) findViewById(R.id.ll_content_container);
        ll_pickup_item_list_container = (LinearLayout) findViewById(R.id.ll_pickup_item_list_container);
        ll_cod_detail = (LinearLayout) findViewById(R.id.ll_cod_detail);
    }

    private void setValue()
    {
        tv_task_type.setText(getTask().getTaskTypeLabel());
        tv_task_code.setText(getTask().getCode());
        tv_status.setText(getTask().getStatus());
        tv_date.setText(getTask().getFormattedDate());
        tv_name.setText(getTask().getName());
        tv_address.setText(getTask().getAddress());
        tv_phone.setText(getTask().getPhone());

        if (getTask().isCOD())
            ll_cod_detail.setVisibility(View.VISIBLE);
        else
            ll_cod_detail.setVisibility(View.GONE);

        tv_payment_method.setText(getTask().getPaymentMethod());
        tv_delivery_fee.setText(getTask().getDeliveryFeeString());
        tv_item_value.setText(getTask().getItemValueString());
        tv_total.setText(getTask().getCODFeeString());
    }

    protected void setLabel(String label)
    {
        String labelInfoDetail = Utility.Message.get(R.string.task_info_detail) + " " + label;
        String labelName = Utility.Message.get(R.string.task_name) + " " + label;
        String labelAddress = Utility.Message.get(R.string.task_address) + " " + label;
        String labelPhone = Utility.Message.get(R.string.task_phone) + " " + label;
        tv_label_info_detail.setText(labelInfoDetail);
        tv_label_name.setText(labelName);
        tv_label_address.setText(labelAddress);
        tv_label_phone.setText(labelPhone);
    }

    protected void setTotal(Double total)
    {
        tv_total.setText(Utility.NumberFormatter.doubleToString(total, 0));
    }

    protected void showPickupItemList(Pickup pickup)
    {
        String pickupItemListLabel = Utility.Message.get(R.string.pickup_list_of_pickup_item) + " (" + String.valueOf(pickup.getPickupItemList().size()) + ")";
        tv_pickup_item_list_label.setText(pickupItemListLabel);

        for (int i = 0; i < pickup.getPickupItemList().size(); i++)
        {
            getPickupItemAdapter().addPickupItemView(i, ll_pickup_item_list_container);
        }

        ll_pickup_item_list_container.setVisibility(View.VISIBLE);
    }

    protected void updateDeliveryData(Delivery delivery)
    {
        tv_status.setText(delivery.getOperationStatusName());
        if (delivery.getWaybillHandover() != null)
            tv_date.setText(delivery.getWaybillHandover().getFormattedHandoverDate());
    }

    protected PickupItemAdapter getPickupItemAdapter(){ return null;}
    protected abstract Task getTask();
}
