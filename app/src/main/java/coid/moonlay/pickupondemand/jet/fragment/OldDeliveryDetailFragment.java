package coid.moonlay.pickupondemand.jet.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.base.BaseItemSelectDialogFragment;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.model.Contact;
import coid.moonlay.pickupondemand.jet.model.Delivery;

/**
 * A simple {@link Fragment} subclass.
 */
public class OldDeliveryDetailFragment
{

}
//public class OldDeliveryDetailFragment extends BaseMainFragment
//{
//    private static final int CONTACT_REQUEST_CODE = 330;
//    public static final String DELIVERY_ARGS_PARAM = "DeliveryParam";
//    private Delivery mDelivery;
//
//    private Button btn_failed, btn_success, btn_contact_single;
//    private TextView tv_name, tv_address, tv_phone, tv_payment_method, tv_delivery_fee, tv_item_value, tv_total;
//    private LinearLayout ll_cod_detail;
//
//    private Contact mContact;
//
//    public OldDeliveryDetailFragment()
//    {
//        // Required empty public constructor
//    }
//
//    public static OldDeliveryDetailFragment newInstance(Delivery delivery)
//    {
//        OldDeliveryDetailFragment fragment = new OldDeliveryDetailFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(DELIVERY_ARGS_PARAM, delivery);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null)
//        {
//            Serializable serializableObject = getArguments().getSerializable(DELIVERY_ARGS_PARAM);
//            if (serializableObject instanceof Delivery)
//                mDelivery = (Delivery) serializableObject;
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState)
//    {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_delivery_detail, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
//    {
//        super.onViewCreated(view, savedInstanceState);
//        setView();
//        setEvent();
//        setValue();
//    }
//
//    @Override
//    public void onResume()
//    {
//        super.onResume();
//        setTitle("Waybill no " + mDelivery.getAwbNumber());
//        setNotificationMenuEnabled(false);
//        setDisplayBackEnabled(true);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Activity.RESULT_OK)
//        {
//            CharSequence code = data.getStringExtra(BaseItemSelectDialogFragment.SELECTED_ITEM_CODE_ARGS_PARAM);
//            CharSequence description = data.getStringExtra(BaseItemSelectDialogFragment.SELECTED_ITEM_DESCRIPTION_ARGS_PARAM);
//            switch (requestCode)
//            {
//                case CONTACT_REQUEST_CODE :
//                    mContact = new Contact(code.toString(), description.toString());
//                    if (mContact.isPhone())
//                        doPhoneCall();
//                    else if (mContact.isSMS())
//                        doSMS();
//                    break;
//                default: break;
//            }
//        }
//    }
//
//    private void setView()
//    {
//        btn_failed = (Button) findViewById(R.id.btn_failed);
//        btn_success = (Button) findViewById(R.id.btn_success);
//        btn_contact_single = (Button) findViewById(R.id.btn_contact_single);
//        tv_name = (TextView) findViewById(R.id.tv_name);
//        tv_address = (TextView) findViewById(R.id.tv_address);
//        tv_phone = (TextView) findViewById(R.id.tv_phone);
//        tv_payment_method = (TextView) findViewById(R.id.tv_payment_method);
//        tv_delivery_fee = (TextView) findViewById(R.id.tv_delivery_fee);
//        tv_item_value = (TextView) findViewById(R.id.tv_item_value);
//        tv_total = (TextView) findViewById(R.id.tv_total);
//        ll_cod_detail = (LinearLayout) findViewById(R.id.ll_cod_detail);
//    }
//
//    private void setEvent()
//    {
//        btn_failed.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//
//            }
//        });
//        btn_success.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//
//            }
//        });
//        btn_contact_single.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                ContactSelectDialogFragment dialog = new ContactSelectDialogFragment();
//                dialog.setTargetFragment(mFragment, CONTACT_REQUEST_CODE);
//                dialog.show(getFragmentManager(), ContactSelectDialogFragment.class.getSimpleName());
//            }
//        });
//    }
//
//    private void setValue()
//    {
//        if (mDelivery == null)
//            return;
//
//        tv_name.setText(mDelivery.getConsigneeName());
//        tv_address.setText(mDelivery.getConsigneeAddress());
//        tv_phone.setText(mDelivery.getConsigneePhone());
//        tv_payment_method.setText(mDelivery.getPaymentMethod());
//        tv_delivery_fee.setText(mDelivery.getDeliveryFeeString());
//        tv_item_value.setText(mDelivery.getItemValueString());
//        tv_total.setText(mDelivery.getCODFeeString());
//        if (mDelivery.isCOD())
//            ll_cod_detail.setVisibility(View.VISIBLE);
//        else
//            ll_cod_detail.setVisibility(View.GONE);
//    }
//
//    private void doPhoneCall()
//    {
//        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
//        {
//            showToast("Gagal terhubung, silakan coba kembali");
//            return;
//        }
//
//        String uri = "tel:" + mDelivery.getConsigneePhone().trim() ;
//        Intent intent = new Intent(Intent.ACTION_CALL);
//        intent.setData(Uri.parse(uri));
//        startActivity(intent);
//    }
//
//    private void doSMS()
//    {
//        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
//
//        smsIntent.setData(Uri.parse("smsto:"));
//        smsIntent.setType("vnd.android-dir/mms-sms");
//        smsIntent.putExtra("address"  , mDelivery.getConsigneePhone());
//
//        try
//        {
//            startActivity(smsIntent);
//        }
//        catch (android.content.ActivityNotFoundException ex)
//        {
//            showToast("SMS gagal, silakan coba kembali");
//        }
//    }
//}
