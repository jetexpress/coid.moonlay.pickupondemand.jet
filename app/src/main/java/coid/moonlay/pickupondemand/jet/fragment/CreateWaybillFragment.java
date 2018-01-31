package coid.moonlay.pickupondemand.jet.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.adapter.PickupItemAdapter;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.custom.ConfirmationDialog;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.model.PickupItem;
import coid.moonlay.pickupondemand.jet.model.PickupQRCode;
import coid.moonlay.pickupondemand.jet.request.PickupCompleteRequest;
import coid.moonlay.pickupondemand.jet.request.PickupItemUnlockRequestByUnlockCode;
import coid.moonlay.pickupondemand.jet.request.PickupItemUnlockUsingQRCode;
import coid.moonlay.pickupondemand.jet.request.PickupQuickCompleteRequest;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateWaybillFragment extends BaseMainFragment implements PickupItemAdapter.IOnItemClickListener, PickupItemDetailFragment.IPickupItemDetailListener
{
    private static final String PICKUP_ARGS_PARAM = "PickupParam";
    private Pickup mPickup;
    private PickupItemAdapter mPickupItemAdapter;
    private PickupCompleteRequest mPickupCompleteRequest;
    private PickupQuickCompleteRequest mPickupQuickCompleteRequest;
    private PickupItemUnlockRequestByUnlockCode mPickupItemUnlockRequestByUnlockCode;
    private PickupItemUnlockUsingQRCode mPickupItemUnlockUsingQRCode;

    private Button btn_submit;
    private TextView tv_name, tv_address, tv_quick_pickup_item_count_label, tv_quick_pickup_item_count,
            tv_pickup_item_list_label, tv_payment_method, tv_total_fee, tv_unlock_pickup_item_using_qr_code, tv_unlock_manual;
    private TextInputLayout input_layout_actual_pickup_item_count;
    private TextInputEditText et_actual_pickup_item_count;
    private EditText et_unlock_code;
    private LinearLayout ll_unlock_pickup_content_container, ll_pickup_item_list_container;

    public CreateWaybillFragment()
    {
        // Required empty public constructor
    }

    public static CreateWaybillFragment newInstance(Pickup pickup)
    {
        CreateWaybillFragment fragment = new CreateWaybillFragment();
        Bundle args = new Bundle();
        args.putParcelable(PICKUP_ARGS_PARAM, pickup);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mPickup = getArguments().getParcelable(PICKUP_ARGS_PARAM);
            if (mPickup != null && !mPickup.isQuickPickup())
                mPickupItemAdapter = new PickupItemAdapter(mContext, mPickup.getPickupItemList(), this);
        }

//        /** uletbe */
//        if (mPickup != null && !mPickup.isQuickPickup())
//        {
//            String unlockCodeLog = "";
//            for (PickupItem pickupItem : mPickup.getPickupItemList())
//            {
//                pickupItem.setStatus(PickupItem.STATUS_LOCKED);
//                if (unlockCodeLog.isEmpty())
//                    unlockCodeLog = pickupItem.getUnlockCode();
//                else
//                    unlockCodeLog += ", " + pickupItem.getUnlockCode();
//            }
//            Log.d("JET_127", "UNLOCK CODE COLLECTION : " + unlockCodeLog);
//        }
//        /** uletbe */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_waybill, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        if (mPickup == null)
            return;

        setView();
        setValue();
        setEvent();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mPickup.isQuickPickup())
            setTitle(Utility.Message.get(R.string.pickup_take_item));
        else
            setTitle(Utility.Message.get(R.string.pickup_create_waybill));

        setNotificationMenuEnabled(false);
        setDisplayBackEnabled(true);
        setUIState();
    }

    @Override
    public void onStop()
    {
        if (mPickupCompleteRequest != null)
            mPickupCompleteRequest.cancel();
        if (mPickupQuickCompleteRequest != null)
            mPickupQuickCompleteRequest.cancel();
        if (mPickupItemUnlockUsingQRCode != null)
            mPickupItemUnlockUsingQRCode.cancel();
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        if (mPickupCompleteRequest != null)
        {
            mPickupCompleteRequest.clear();
            mPickupCompleteRequest = null;
        }
        if (mPickupQuickCompleteRequest != null)
        {
            mPickupQuickCompleteRequest.clear();
            mPickupQuickCompleteRequest = null;
        }
        if (mPickupItemUnlockUsingQRCode != null)
        {
            mPickupItemUnlockUsingQRCode.clear();
            mPickupItemUnlockUsingQRCode = null;
        }
        if (mPickupItemUnlockRequestByUnlockCode != null)
        {
            mPickupItemUnlockRequestByUnlockCode.clear();
            mPickupItemUnlockRequestByUnlockCode = null;
        }
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null && result.getContents() != null)
        {
            PickupQRCode pickupQRCode = PickupQRCode.createFromDataString(result.getContents());
            if (pickupQRCode == null)
            {
                showToast(Utility.Message.get(R.string.pickup_item_invalid_qr_code));
                return;
            }

            PickupItem pickupItemFromQRCode = PickupItem.createFromQRCode(pickupQRCode);

            for (int i = 0; i < mPickupItemAdapter.getCount(); i++)
            {
                PickupItem pickupItem = mPickupItemAdapter.getItem(i);
                if (pickupItem != null && pickupItem.getShippingLabelId().equals(pickupItemFromQRCode.getShippingLabelId()))
                {
                    requestUnlockQRCode(pickupItem);
                    return;
                }
            }

            showToast(Utility.Message.get(R.string.pickup_item_invalid_qr_code));
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBookingItemClick(Integer position)
    {
        PickupItem pickupItem = mPickupItemAdapter.getItem(position);
        if (pickupItem == null)
            return;

        PickupItemDetailFragment pickupItemDetailFragment = PickupItemDetailFragment.newInstance(pickupItem, mPickup, pickupItem.isCompleted());
        pickupItemDetailFragment.setTargetFragment(mFragment, 0);
        getNavigator().showFragment(pickupItemDetailFragment);
    }

    @Override
    public void onItemUnlocked(PickupItem pickupItem)
    {
//        mPickupItemAdapter.updatePickupItemView(pickupItem, ll_pickup_item_list_container);
        mPickupItemAdapter.notifyDataSetChanged();
        updateTotalFee();
        setUIState();
    }

    @Override
    public void onWaybillCreated(PickupItem pickupItem)
    {
//        mPickupItemAdapter.updatePickupItemView(pickupItem, ll_pickup_item_list_container);
        mPickupItemAdapter.notifyDataSetChanged();
        updateTotalFee();
        setUIState();
    }

    private void setView()
    {
        btn_submit = (Button) findViewById(R.id.btn_submit);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_quick_pickup_item_count_label = (TextView) findViewById(R.id.tv_quick_pickup_item_count_label);
        tv_quick_pickup_item_count = (TextView) findViewById(R.id.tv_quick_pickup_item_count);
        input_layout_actual_pickup_item_count = (TextInputLayout) findViewById(R.id.input_layout_actual_pickup_item_count);
        et_actual_pickup_item_count = (TextInputEditText) findViewById(R.id.et_actual_pickup_item_count);
        tv_pickup_item_list_label = (TextView) findViewById(R.id.tv_pickup_item_list_label);
        ll_unlock_pickup_content_container = (LinearLayout) findViewById(R.id.ll_unlock_pickup_content_container);
        ll_pickup_item_list_container = (LinearLayout) findViewById(R.id.ll_pickup_item_list_container);
        tv_payment_method = (TextView) findViewById(R.id.tv_payment_method);
        tv_total_fee = (TextView) findViewById(R.id.tv_total_fee);
        tv_unlock_pickup_item_using_qr_code = (TextView) findViewById(R.id.tv_unlock_pickup_item_using_qr_code);
        et_unlock_code = (EditText) findViewById(R.id.et_unlock_code);
        tv_unlock_manual = (TextView) findViewById(R.id.tv_unlock_manual);
    }

    private void setValue()
    {
        if (mPickup == null)
            return;

        tv_name.setText(mPickup.getName());
        tv_address.setText(mPickup.getAddress());
        tv_payment_method.setText(mPickup.getPaymentMethodCode());

        if (mPickup.isQuickPickup())
            tv_quick_pickup_item_count.setText(String.valueOf(mPickup.getQuickPickupItemCount()));
        else
        {
            String pickupItemListLabel = Utility.Message.get(R.string.pickup_list_of_pickup_item) + " (" + String.valueOf(mPickup.getPickupItemList().size()) + ")";
            tv_pickup_item_list_label.setText(pickupItemListLabel);

            for (int i = 0; i < mPickup.getPickupItemList().size(); i++)
            {
                mPickupItemAdapter.addPickupItemView(i, ll_pickup_item_list_container);
            }
        }

        updateTotalFee();
    }

    private void setEvent()
    {
        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                submit();
            }
        });

        tv_unlock_pickup_item_using_qr_code.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(CreateWaybillFragment.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt(Utility.Message.get(R.string.pickup_item_qr_code_info));
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });

        tv_unlock_manual.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (int i = 0; i < mPickupItemAdapter.getCount(); i++)
                {
                    PickupItem pickupItem = mPickupItemAdapter.getItem(i);
                    String unlockCode = et_unlock_code.getText().toString().trim();
                    if (pickupItem != null && pickupItem.getUnlockCode().equalsIgnoreCase(unlockCode))
                    {
                        requestUnlockManual(pickupItem);
                        return;
                    }
                }

                showToast(Utility.Message.get(R.string.failed_to_unlock_pickup_item));
            }
        });
    }

    private void setUIState()
    {
        if (mPickup.isQuickPickup())
        {
            tv_quick_pickup_item_count_label.setVisibility(View.VISIBLE);
            tv_quick_pickup_item_count.setVisibility(View.VISIBLE);
            input_layout_actual_pickup_item_count.setVisibility(View.VISIBLE);
            et_actual_pickup_item_count.setVisibility(View.VISIBLE);
            ll_unlock_pickup_content_container.setVisibility(View.GONE);
            ll_pickup_item_list_container.setVisibility(View.GONE);
        }
        else
        {
            tv_quick_pickup_item_count_label.setVisibility(View.GONE);
            tv_quick_pickup_item_count.setVisibility(View.GONE);
            input_layout_actual_pickup_item_count.setVisibility(View.GONE);
            et_actual_pickup_item_count.setVisibility(View.GONE);
            ll_pickup_item_list_container.setVisibility(View.VISIBLE);

            if (mPickup.isAllItemsUnlocked())
                ll_unlock_pickup_content_container.setVisibility(View.GONE);
            else
                ll_unlock_pickup_content_container.setVisibility(View.VISIBLE);
        }

    }

    private void updateTotalFee()
    {
        tv_total_fee.setText(mPickup.getTotalFeeString());
    }

    private void submit()
    {
        if (mPickup.isQuickPickup())
        {
            if (!Utility.Validation.isNominalInvalid(input_layout_actual_pickup_item_count, et_actual_pickup_item_count, 0D))
                quickCompleteRequest();
        }
        else
        {
            if (mPickup.isAllItemsCompleted())
                completeRequest();
            else
            {
                String title = Utility.Message.get(R.string.pickup_complete_with_incomplete_pickup_item_title);
                String message = Utility.Message.get(R.string.pickup_complete_with_incomplete_pickup_item_confirmation);
                ConfirmationDialog submitDialog = new ConfirmationDialog(mContext, title, message)
                {
                    @Override
                    public void onOKClicked()
                    {
                        completeRequest();
                    }
                };
                submitDialog.show();
            }
        }
    }

    private void completeRequest()
    {
        mPickupCompleteRequest = new PickupCompleteRequest(mContext, mPickup.getCode());
        mPickupCompleteRequest.executeAsync();
    }

    private void quickCompleteRequest()
    {
        Long actualPickupItemCount = Long.valueOf(et_actual_pickup_item_count.getText().toString().trim());

        mPickupQuickCompleteRequest = new PickupQuickCompleteRequest(mContext, mPickup.getCode(), actualPickupItemCount);
        mPickupQuickCompleteRequest.executeAsync();
    }

    private void requestUnlockQRCode(final PickupItem pickupItem)
    {
        mPickupItemUnlockUsingQRCode = new PickupItemUnlockUsingQRCode(mContext, pickupItem.getShippingLabelId())
        {
            @Override
            protected void onSuccessOnUIThread(Response<PickupItem> response)
            {
                super.onSuccessOnUIThread(response);
                pickupItem.setStatus(response.body().getStatus());
                onItemUnlocked(pickupItem);
                PickupItemDetailFragment pickupItemDetailFragment = PickupItemDetailFragment.newInstance(pickupItem, mPickup, pickupItem.isCompleted());
                pickupItemDetailFragment.setTargetFragment(mFragment, 0);
                getNavigator().showFragment(pickupItemDetailFragment);
            }
        };
        mPickupItemUnlockUsingQRCode.executeAsync();

//        /** uletbe */
//        pickupItem.setStatus(PickupItem.STATUS_UNLOCKED);
//        onItemUnlocked(pickupItem);
//        PickupItemDetailFragment pickupItemDetailFragment = PickupItemDetailFragment.newInstance(pickupItem, mPickup, pickupItem.isCompleted());
//        pickupItemDetailFragment.setTargetFragment(mFragment, 0);
//        getNavigator().showFragment(pickupItemDetailFragment);
//        /** uletbe */
    }

    private void requestUnlockManual(final PickupItem pickupItem)
    {
        mPickupItemUnlockRequestByUnlockCode = new PickupItemUnlockRequestByUnlockCode(mContext, mPickup.getCode(), pickupItem.getUnlockCode())
        {
            @Override
            protected void onSuccessOnUIThread(Response<PickupItem> response)
            {
                super.onSuccessOnUIThread(response);
                pickupItem.setStatus(response.body().getStatus());
                onItemUnlocked(pickupItem);
                et_unlock_code.setText("");
                PickupItemDetailFragment pickupItemDetailFragment = PickupItemDetailFragment.newInstance(pickupItem, mPickup, pickupItem.isCompleted());
                pickupItemDetailFragment.setTargetFragment(mFragment, 0);
                getNavigator().showFragment(pickupItemDetailFragment);
            }
        };
        mPickupItemUnlockRequestByUnlockCode.executeAsync();

//        /** uletbe */
//        pickupItem.setStatus(PickupItem.STATUS_UNLOCKED);
//        onItemUnlocked(pickupItem);
//        et_unlock_code.setText("");
//        PickupItemDetailFragment pickupItemDetailFragment = PickupItemDetailFragment.newInstance(pickupItem, mPickup, pickupItem.isCompleted());
//        pickupItemDetailFragment.setTargetFragment(mFragment, 0);
//        getNavigator().showFragment(pickupItemDetailFragment);
//        /** uletbe */
    }
}