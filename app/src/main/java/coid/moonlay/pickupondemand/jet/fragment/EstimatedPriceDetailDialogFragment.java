package coid.moonlay.pickupondemand.jet.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseDialogFragment;
import coid.moonlay.pickupondemand.jet.base.BaseHasBasicLayoutDialogFragment;
import coid.moonlay.pickupondemand.jet.model.PickupItem;
import coid.moonlay.pickupondemand.jet.model.PickupItemSimulation;
import coid.moonlay.pickupondemand.jet.request.PickupItemPriceSimulationRequest;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstimatedPriceDetailDialogFragment extends BaseHasBasicLayoutDialogFragment
{
    public static final String ESTIMATED_PRICE_PARAM = "estimatedPriceParam";
    private static final String PICKUP_ITEM_ARGS_PARAM = "pickupItemParam";
    private static final String BRANCH_CODE_ARGS_PARAM = "branchCodeParam";
    private static final String USER_ID_ARGS_PARAM = "userIdParam";
    private LinearLayout ll_content_container;
    private TextView tv_error_message, tv_delivery_fee, tv_packaging_fee, tv_insurance_fee, tv_discount, tv_total;
    private PickupItem mPickupItem;
    private String mBranchCode;
    private String mUserId;
    private PickupItemPriceSimulationRequest mPickupItemPriceSimulationRequest;

    public EstimatedPriceDetailDialogFragment()
    {
        // Required empty public constructor
    }

    public static EstimatedPriceDetailDialogFragment newInstance(PickupItem pickupItem, String branchCode, String userId)
    {
        EstimatedPriceDetailDialogFragment fragment = new EstimatedPriceDetailDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(PICKUP_ITEM_ARGS_PARAM, pickupItem);
        args.putString(BRANCH_CODE_ARGS_PARAM, branchCode);
        args.putString(USER_ID_ARGS_PARAM, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.CustomDialog);
        if (getArguments() != null)
        {
            mPickupItem = getArguments().getParcelable(PICKUP_ITEM_ARGS_PARAM);
            mBranchCode = getArguments().getString(BRANCH_CODE_ARGS_PARAM);
            mUserId = getArguments().getString(USER_ID_ARGS_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estimated_price_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
//        showProgressBar();
        setView();
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
            d.setCancelable(true);
            d.setCanceledOnTouchOutside(true);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (mPickupItem.isCompleted())
            setValue();
        else
            requestPriceSimulation();
    }

    @Override
    public void onDestroy()
    {
        if (mPickupItemPriceSimulationRequest != null)
        {
            mPickupItemPriceSimulationRequest.clear();
            mPickupItemPriceSimulationRequest = null;
        }
        super.onDestroy();
    }

    @Override
    protected View getBaseContentLayout()
    {
        return ll_content_container;
    }

    @Override
    protected void onRetry()
    {
        requestPriceSimulation();
    }

    private void setView()
    {
        ll_content_container = (LinearLayout) findViewById(R.id.ll_content_container);
        tv_error_message = (TextView) findViewById(R.id.tv_error_message);
        tv_delivery_fee = (TextView) findViewById(R.id.tv_delivery_fee);
        tv_packaging_fee = (TextView) findViewById(R.id.tv_packaging_fee);
        tv_insurance_fee = (TextView) findViewById(R.id.tv_insurance_fee);
        tv_discount = (TextView) findViewById(R.id.tv_discount);
        tv_total = (TextView) findViewById(R.id.tv_total);
    }

    private void setValue(PickupItemSimulation pickupItemSimulation)
    {
        if (pickupItemSimulation == null)
            return;

        tv_delivery_fee.setText(pickupItemSimulation.getPriceString());
        tv_packaging_fee.setText(pickupItemSimulation.getChargePackagingFeeString());
        tv_insurance_fee.setText(pickupItemSimulation.getChargeInsuranceFeeString());
        tv_discount.setText(pickupItemSimulation.getDiscountValueString());
        tv_total.setText(pickupItemSimulation.getTotalFeeString());
    }

    private void setValue()
    {
        if (mPickupItem == null)
            return;

        tv_delivery_fee.setText(mPickupItem.getFeeString());
        tv_packaging_fee.setText(mPickupItem.getPackagingFeeString());
        tv_insurance_fee.setText(mPickupItem.getInsuranceFeeString());
        tv_discount.setText(mPickupItem.getDiscountString());
        tv_total.setText(mPickupItem.getTotalFeeString());
    }

    private void requestPriceSimulation()
    {
        if (mPickupItem == null || mBranchCode == null || mBranchCode.isEmpty() || mUserId == null || mUserId.isEmpty())
        {
            showContent(false);
            return;
        }

        PickupItemSimulation pickupItemSimulation = new PickupItemSimulation(mPickupItem);
        pickupItemSimulation.setBranchCode(mBranchCode);
        pickupItemSimulation.setUserId(mUserId);
        mPickupItemPriceSimulationRequest = new PickupItemPriceSimulationRequest(mContext, pickupItemSimulation)
        {
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

            @Override
            protected void onResponseFailedOnUIThread(Response<PickupItemSimulation> response)
            {
                hideAll();
                tv_error_message.setText(Utility.Message.getResponseFailedMessage(R.string.estimated_price_request_timed_out, response));
                tv_error_message.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onSuccessOnUIThread(Response<PickupItemSimulation> response)
            {
                setValue(response.body());
                showContent();
                if (getTargetFragment() != null)
                {
                    Intent dataIntent = new Intent();
                    dataIntent.putExtra(ESTIMATED_PRICE_PARAM, response.body().getTotalFeeString());
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, dataIntent);
                }
            }
        };
        mPickupItemPriceSimulationRequest.executeAsync();
    }
}
