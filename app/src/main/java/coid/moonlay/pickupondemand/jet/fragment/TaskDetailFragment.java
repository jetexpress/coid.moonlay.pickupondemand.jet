package coid.moonlay.pickupondemand.jet.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.internal.LockOnGetVariable;
import com.google.android.gms.auth.GoogleAuthException;
import com.journeyapps.barcodescanner.Util;

import java.util.Locale;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseItemSelectDialogFragment;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.model.Contact;
import coid.moonlay.pickupondemand.jet.model.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class TaskDetailFragment extends BaseMainFragment
{
    private static final int CONTACT_REQUEST_CODE = 330;

    protected TextView tv_cancel, tv_contact, tv_navigation, tv_task_type, tv_time, tv_label_name, tv_name,
            tv_label_pic, tv_pic, tv_label_address, tv_address, tv_label_address_detail, tv_address_detail,tv_schedule,tv_label_schedule,
            tv_label_phone, tv_phone, tv_weight_label, tv_weight, tv_payment_method, tv_delivery_fee, tv_item_value, tv_total;
    protected Button btn_start_trip, btn_has_arrived, btn_create_waybill, btn_failed, btn_success, btn_contact_single,btn_prsjetid_cancel,btn_prsjetid_pickup;
    protected LinearLayout ll_bottom_container, ll_button_group_container,
            ll_delivery_status_button_container, ll_cod_detail, ll_price_detail_container,ll_delivery_status_button_container_prs;
    protected FrameLayout fl_contact_single_container;
    protected RelativeLayout rl_content_container;
    private String scheduleFormatted;

    public TaskDetailFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
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
    public void onResume()
    {
        super.onResume();
        String title = getTask().getTaskTypeLabel() + getTask().getCode();
        if(title.equals(""))
            title = getTask().getTaskTypeLabel() + getTask().getDrsCode();
        if(title.equals(""))
            title = getTask().getTaskTypeLabel() + getTask().getPrsCode();

        setTitle(title);
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
                case CONTACT_REQUEST_CODE :
                    Contact contact = new Contact(code.toString(), description.toString());
                    if (contact.isPhone())
                        doPhoneCall();
                    else if (contact.isSMS())
                        doSMS();
                    break;
                default: break;
            }
        }
    }

    @Override
    protected View getBaseContentLayout()
    {
        return rl_content_container;
    }

    private void setView()
    {
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_contact = (TextView) findViewById(R.id.tv_contact);
        tv_navigation = (TextView) findViewById(R.id.tv_navigation);
        tv_task_type = (TextView) findViewById(R.id.tv_task_type);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_label_name = (TextView) findViewById(R.id.tv_label_name);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_label_pic = (TextView) findViewById(R.id.tv_label_pic);
        tv_pic = (TextView) findViewById(R.id.tv_pic);
        tv_label_address = (TextView) findViewById(R.id.tv_label_address);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_label_address_detail = (TextView) findViewById(R.id.tv_label_address_detail);
        tv_address_detail = (TextView) findViewById(R.id.tv_address_detail);
        tv_label_phone = (TextView) findViewById(R.id.tv_label_phone);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_weight_label = (TextView) findViewById(R.id.tv_weight_label);
        tv_weight = (TextView) findViewById(R.id.tv_weight);
        tv_payment_method = (TextView) findViewById(R.id.tv_payment_method);
        tv_delivery_fee = (TextView) findViewById(R.id.tv_delivery_fee);
        tv_item_value = (TextView) findViewById(R.id.tv_item_value);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_label_schedule = (TextView) findViewById(R.id.tv_label_schedule);
        tv_schedule = (TextView) findViewById(R.id.tv_schedule);
        btn_start_trip = (Button) findViewById(R.id.btn_start_trip);
        btn_has_arrived = (Button) findViewById(R.id.btn_has_arrived);
        btn_create_waybill = (Button) findViewById(R.id.btn_create_waybill);
        btn_failed = (Button) findViewById(R.id.btn_failed);
        btn_success = (Button) findViewById(R.id.btn_success);
        btn_prsjetid_pickup = (Button) findViewById(R.id.btn_prsjetid_pickup);
        ll_bottom_container = (LinearLayout) findViewById(R.id.ll_bottom_container);
        ll_button_group_container = (LinearLayout) findViewById(R.id.ll_button_group_container);
        ll_delivery_status_button_container = (LinearLayout) findViewById(R.id.ll_delivery_status_button_container);
        ll_delivery_status_button_container_prs = (LinearLayout) findViewById(R.id.ll_delivery_status_button_container_prs);
        ll_cod_detail = (LinearLayout) findViewById(R.id.ll_cod_detail);
        ll_price_detail_container = (LinearLayout) findViewById(R.id.ll_price_detail_container);
        fl_contact_single_container = (FrameLayout) findViewById(R.id.fl_contact_single_container);
        btn_contact_single = (Button) findViewById(R.id.btn_contact_single);
        rl_content_container = (RelativeLayout) findViewById(R.id.rl_content_container);
    }

    private void setValue()
    {
        tv_task_type.setText(getTask().getTaskType());
        tv_time.setText("");
        tv_name.setText(getTask().getName());
        tv_address.setText(getTask().getAddress());
        tv_phone.setText(getTask().getPhone());
        tv_payment_method.setText(getTask().getPaymentMethod());
        tv_delivery_fee.setText(getTask().getDeliveryFeeString());
        tv_item_value.setText(getTask().getItemValueString());
        tv_total.setText(getTask().getCODFeeString());
        if(getTask().getScheduleDate().length()> 10){
            scheduleFormatted = getTask().getScheduleDate().substring(0, getTask().getScheduleDate().length()-8);
        }
        else {
            scheduleFormatted = "";
        }
        tv_schedule.setText(scheduleFormatted);

        if (getTask().isCOD())
            ll_cod_detail.setVisibility(View.VISIBLE);
        else
            ll_cod_detail.setVisibility(View.GONE);
    }

    private void setEvent()
    {
        tv_cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onCancelClicked();
            }
        });
        View.OnClickListener contactOnClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ContactSelectDialogFragment dialog = new ContactSelectDialogFragment();
                dialog.setTargetFragment(mFragment, CONTACT_REQUEST_CODE);
                dialog.show(getFragmentManager(), ContactSelectDialogFragment.class.getSimpleName());
            }
        };
        tv_contact.setOnClickListener(contactOnClickListener);
        fl_contact_single_container.setOnClickListener(contactOnClickListener);
        btn_contact_single.setOnClickListener(contactOnClickListener);

        tv_navigation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onNavigationClicked();
            }
        });
        btn_start_trip.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onStartTripClicked();
            }
        });
        btn_has_arrived.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onHasArrivedClicked();
            }
        });
        btn_create_waybill.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onCreatingWaybillClicked();
            }
        });
        btn_failed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onDeliveryFail();
            }
        });
        btn_success.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onDeliverySuccess();
            }
        });

        btn_prsjetid_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSuccessPrs();
            }
        });

    }

    protected void setLabel(String label)
    {
        String labelName = null;
        String labelPIC = null;
        String labelAddress = null;
        String labelAddressDetail = null;
        String labelPhone = null;
        String labelTotalWaybill = null;

        if (getTask().isPickup())
        {
            labelName = Utility.Message.get(R.string.pickup_name);
            labelPIC = Utility.Message.get(R.string.pickup_pic);
            labelAddress = Utility.Message.get(R.string.pickup_address);
            labelAddressDetail = Utility.Message.get(R.string.pickup_address_detail);
            labelPhone = Utility.Message.get(R.string.pickup_phone_number);
        }
        else if(getTask().isDRS())
        {
            labelName = Utility.Message.get(R.string.delivery_name);
            labelPIC = "";
            labelAddress = Utility.Message.get(R.string.delivery_address);
            labelAddressDetail = Utility.Message.get(R.string.delivery_address_detail);
            labelPhone = Utility.Message.get(R.string.delivery_phone_number);
        }else if(getTask().isPRSOutlet()){
            labelName = Utility.Message.get(R.string.pickup_run_sheet_outlet_name);
            labelAddress = Utility.Message.get(R.string.pickup_run_sheet_outlet_address);
            labelTotalWaybill = Utility.Message.get(R.string.pickup_run_sheet_outlet_total_waybill);
        }else{
            labelName = Utility.Message.get(R.string.pickup_run_sheet_jetid_name);
            labelAddress = Utility.Message.get(R.string.pickup_run_sheet_outlet_address);
        }
        tv_label_name.setText(labelName);
        tv_label_pic.setText(labelPIC);
        tv_label_address.setText(labelAddress);
        tv_label_address_detail.setText(labelAddressDetail);
        tv_label_phone.setText(labelPhone);
    }

    protected void setTotal(String total)
    {
        tv_total.setText(total);
    }

    protected void setTotal(Double total)
    {
        tv_total.setText(Utility.NumberFormatter.doubleToString(total, 0));
    }

    protected void setPhone(String phoneNumber)
    {
        tv_phone.setText(phoneNumber);
    }

    protected void setAddressDetail(String addressDetail)
    {
        tv_address_detail.setText(addressDetail);
    }

    protected void showPIC(String pic)
    {
        tv_pic.setText(pic);
        tv_label_pic.setVisibility(View.VISIBLE);
        tv_pic.setVisibility(View.VISIBLE);
    }

    protected void showWeight(Double weight)
    {
        String weightString = Utility.NumberFormatter.doubleToString(weight, 2) + " kg";
        tv_weight.setText(weightString);
        tv_weight_label.setVisibility(View.VISIBLE);
        tv_weight.setVisibility(View.VISIBLE);
    }

    protected void showStartTripButton()
    {
        btn_start_trip.setVisibility(View.VISIBLE);
        btn_has_arrived.setVisibility(View.GONE);
        btn_create_waybill.setVisibility(View.GONE);

        ll_delivery_status_button_container.setVisibility(View.GONE);
    }


    protected void showHasArrivedButton()
    {
        btn_start_trip.setVisibility(View.GONE);
        btn_has_arrived.setVisibility(View.VISIBLE);
        btn_create_waybill.setVisibility(View.GONE);

        ll_delivery_status_button_container.setVisibility(View.GONE);
    }

    protected void showGroupPrsJetid(){
        ll_bottom_container.setVisibility(View.VISIBLE);
        tv_label_name.setVisibility(View.VISIBLE);
        tv_label_address.setVisibility(View.VISIBLE);
        tv_cancel.setVisibility(View.GONE);
        tv_label_schedule.setVisibility(View.VISIBLE);
        tv_schedule.setVisibility(View.VISIBLE);
        ll_delivery_status_button_container_prs.setVisibility(View.VISIBLE);
        ll_delivery_status_button_container.setVisibility(View.GONE);
        ll_button_group_container.setVisibility(View.VISIBLE);

    }

    protected void showGroupHistoryJetid(){
        ll_bottom_container.setVisibility(View.VISIBLE);
        tv_label_name.setVisibility(View.VISIBLE);
        tv_label_address.setVisibility(View.VISIBLE);
        tv_cancel.setVisibility(View.GONE);
        tv_label_schedule.setVisibility(View.VISIBLE);
        tv_schedule.setVisibility(View.VISIBLE);
        ll_delivery_status_button_container.setVisibility(View.GONE);

    }



    protected void showCreateWaybillButton()
    {
        btn_start_trip.setVisibility(View.GONE);
        btn_has_arrived.setVisibility(View.GONE);
        btn_create_waybill.setVisibility(View.VISIBLE);
        ll_delivery_status_button_container.setVisibility(View.GONE);
    }

    protected void showDeliveryStatusButton()
    {
        btn_start_trip.setVisibility(View.GONE);
        btn_has_arrived.setVisibility(View.GONE);
        btn_create_waybill.setVisibility(View.GONE);
        ll_delivery_status_button_container.setVisibility(View.VISIBLE);
    }

    protected void showButtonGroup()
    {
        ll_button_group_container.setVisibility(View.VISIBLE);
        fl_contact_single_container.setVisibility(View.GONE);
    }

    protected void showSingleContactButton()
    {
        ll_button_group_container.setVisibility(View.GONE);
        fl_contact_single_container.setVisibility(View.VISIBLE);
    }

    protected void hidePickupAndDRS()
    {
        ll_price_detail_container.setVisibility(View.GONE);
    }

    protected void hideTvPickupAndDRS()
    {
        tv_phone.setVisibility(View.GONE);
        tv_address_detail.setVisibility(View.GONE);
        tv_payment_method.setVisibility(View.GONE);
        tv_delivery_fee.setVisibility(View.GONE);
        tv_item_value.setVisibility(View.GONE);
        tv_total.setVisibility(View.GONE);
    }

    protected void showCancelButton()
    {
        tv_cancel.setVisibility(View.VISIBLE);
    }

    protected void hideCancelButton()
    {
        tv_cancel.setVisibility(View.GONE);
    }

    protected void showNavigationButton()
    {
        tv_navigation.setVisibility(View.VISIBLE);
    }

    protected void hideNavigationButton()
    {
        tv_navigation.setVisibility(View.GONE);
    }

    protected void updateCancelButtonLabel(String label)
    {
        tv_cancel.setText(label);
    }

    protected void setTextCompleteLabel(String text)
    {
        btn_create_waybill.setText(text);
    }

    private void doPhoneCall()
    {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            showToast(Utility.Message.get(R.string.failed_to_connect_phone_call));
            return;
        }

        String uri = "tel:" + getTask().getPhone().trim();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }

    private void doSMS()
    {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", getTask().getPhone().trim());

        try
        {
            startActivity(smsIntent);
        }
        catch (android.content.ActivityNotFoundException ex)
        {
            showToast(Utility.Message.get(R.string.failed_to_sms));
        }
    }

    protected void onDeliveryFail()
    {
        getNavigator().showFragment(FailedDeliveryFragment.newInstance(getTask()));
    }

    protected void onDeliverySuccess()
    {
        getNavigator().showFragment(ProofOfDeliveryFragment.newInstance(getTask()));
    }

    protected void onSuccessPrs(){
        getNavigator().showFragment(PrsCompleteFragment.newInstance(getTask()));
    }

    protected void onCreatingWaybillClicked(){ showToast(Utility.Message.get(R.string.dev_not_implemented)); }
    protected void onCancelClicked(){ showToast(Utility.Message.get(R.string.dev_not_implemented)); }
    protected void onNavigationClicked(){
    String uri = String.format(Locale.ENGLISH, "geo:%f,%f", -6222.226379, 10622.808111);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        getContext().startActivity(intent);
    }

    protected void onStartTripClicked(){ showToast(Utility.Message.get(R.string.dev_not_implemented)); }
    protected void onHasArrivedClicked() { showToast(Utility.Message.get(R.string.dev_not_implemented)); }
    @NonNull
    protected abstract Task getTask();
}
