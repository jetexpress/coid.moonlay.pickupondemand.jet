package coid.moonlay.pickupondemand.jet.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseItemSelectDialogFragment;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.custom.ConfirmationDialog;
import coid.moonlay.pickupondemand.jet.model.Location;
import coid.moonlay.pickupondemand.jet.model.PackagingItem;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.model.PickupItem;
import coid.moonlay.pickupondemand.jet.model.Product;
import coid.moonlay.pickupondemand.jet.request.PickupCreateWaybillRequest;
import coid.moonlay.pickupondemand.jet.request.PickupItemUnlockRequestByUnlockCode;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickupItemDetailFragment extends BaseMainFragment
{
    private static final String PICKUP_ITEM_ARGS_PARAM = "PickupItemParam";
    public  static final String PICKUP_ARGS_PARAM = "PickupParam";
    public  static final String READ_ONLY_ARGS_PARAM = "ReadOnlyParam";
    private static final int LOCATION_REQUEST_CODE = 300;
    private static final int PRODUCT_REQUEST_CODE = 301;
    private static final int PACKAGING_REQUEST_CODE = 302;
    private static final int ESTIMATED_PRICE_REQUEST_CODE = 303;
    private Pickup mPickup;
    private PickupItem mPickupItem;
    private Location mSelectedLocation;
    private Product mSelectedProduct;
    private PackagingItem mSelectedPackagingItem;
    private Boolean mIsReadOnly;

    private PickupItemUnlockRequestByUnlockCode mPickupItemUnlockRequestByUnlockCode;

    private RelativeLayout rl_four_digit_pickup_item_code_container;
    private LinearLayout ll_bottom_container, ll_estimated_price_container;
    private ScrollView sv_content_container;
    private Button btn_submit_four_digit_pickup_item_code, btn_submit;
    private TextView tv_name, tv_address_detail, tv_address, tv_volume_label, tv_volume, tv_fee;
    private EditText et_four_last_digit_pickup_item_code;
    private TextInputLayout input_layout_consignee_location, input_layout_weight, input_layout_length, input_layout_width, input_layout_height,
            input_layout_product, input_layout_packaging_item, input_layout_item_value, input_layout_description;
    private TextInputEditText et_consignee_location, et_weight, et_length, et_width, et_height, et_product, et_packaging_item, et_item_value, et_description;
    private CheckBox checkbox_insured;

    private PickupCreateWaybillRequest mPickupCreateWaybillRequest;

    public PickupItemDetailFragment()
    {
        // Required empty public constructor
    }

    public static PickupItemDetailFragment newInstance(PickupItem pickupItem, Pickup pickup, Boolean isReadOnly)
    {
        PickupItemDetailFragment fragment = new PickupItemDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(PICKUP_ARGS_PARAM, pickup);
        args.putParcelable(PICKUP_ITEM_ARGS_PARAM, pickupItem);
        args.putBoolean(READ_ONLY_ARGS_PARAM, isReadOnly);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mPickupItem = getArguments().getParcelable(PICKUP_ITEM_ARGS_PARAM);
            mPickup = getArguments().getParcelable(PICKUP_ARGS_PARAM);
            mIsReadOnly = getArguments().getBoolean(READ_ONLY_ARGS_PARAM);
            mSelectedLocation = new Location();
            mSelectedLocation.setCode(mPickupItem.getLocationCode());
            mSelectedLocation.setDisplay(mPickupItem.getLocationName());
            mSelectedProduct = new Product();
            mSelectedProduct.setCode(mPickupItem.getProductCode());
            mSelectedProduct.setName(mPickupItem.getProductName());
            mSelectedPackagingItem = new PackagingItem();
            mSelectedPackagingItem.setCode(mPickupItem.getPackagingItemCode());
            mSelectedPackagingItem.setName(mPickupItem.getPackagingItemName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pickup_item_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setValue();
        setUIState();
        setEvent();
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
                case LOCATION_REQUEST_CODE:
                    et_consignee_location.setText(description);
                    mSelectedLocation = new Location();
                    mSelectedLocation.setCode(code.toString());
                    mSelectedLocation.setDisplay(description.toString());
                    break;
                case PRODUCT_REQUEST_CODE:
                    et_product.setText(description);
                    mSelectedProduct = new Product();
                    mSelectedProduct.setCode(code.toString());
                    mSelectedProduct.setName(description.toString());
                    break;
                case PACKAGING_REQUEST_CODE:
                    et_packaging_item.setText(description);
                    mSelectedPackagingItem = new PackagingItem();
                    mSelectedPackagingItem.setCode(code.toString());
                    mSelectedPackagingItem.setName(description.toString());
                    break;
                case ESTIMATED_PRICE_REQUEST_CODE:
                    String estimationPrice = data.getStringExtra(EstimatedPriceDetailDialogFragment.ESTIMATED_PRICE_PARAM);
                    tv_fee.setText(estimationPrice);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setUIState();
        setNotificationMenuEnabled(false);
        setDisplayBackEnabled(true);
    }

    @Override
    public void onDestroy()
    {
        if (mPickupCreateWaybillRequest != null)
        {
            mPickupCreateWaybillRequest.clear();
            mPickupCreateWaybillRequest = null;
        }
        if (mPickupItemUnlockRequestByUnlockCode != null)
        {
            mPickupItemUnlockRequestByUnlockCode.cancel();
            mPickupItemUnlockRequestByUnlockCode = null;
        }
        super.onDestroy();
    }

    private void setView()
    {
        rl_four_digit_pickup_item_code_container = (RelativeLayout) findViewById(R.id.rl_four_digit_pickup_item_code_container);
        ll_bottom_container = (LinearLayout) findViewById(R.id.ll_bottom_container);
        ll_estimated_price_container = (LinearLayout) findViewById(R.id.ll_estimated_price_container);
        sv_content_container = (ScrollView) findViewById(R.id.sv_content_container);
        et_four_last_digit_pickup_item_code = (EditText) findViewById(R.id.et_four_last_digit_pickup_item_code);
        btn_submit_four_digit_pickup_item_code = (Button) findViewById(R.id.btn_submit_four_digit_pickup_item_code);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_address_detail = (TextView) findViewById(R.id.tv_address_detail);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_volume_label = (TextView) findViewById(R.id.tv_volume_label);
        tv_volume = (TextView) findViewById(R.id.tv_volume);
        input_layout_consignee_location = (TextInputLayout) findViewById(R.id.input_layout_consignee_location);
        input_layout_weight = (TextInputLayout) findViewById(R.id.input_layout_weight);
        input_layout_length = (TextInputLayout) findViewById(R.id.input_layout_length);
        input_layout_width = (TextInputLayout) findViewById(R.id.input_layout_width);
        input_layout_height = (TextInputLayout) findViewById(R.id.input_layout_height);
        input_layout_product = (TextInputLayout) findViewById(R.id.input_layout_product);
        input_layout_packaging_item = (TextInputLayout) findViewById(R.id.input_layout_packaging_item);
        input_layout_item_value = (TextInputLayout) findViewById(R.id.input_layout_item_value);
        input_layout_description = (TextInputLayout) findViewById(R.id.input_layout_description);
        et_consignee_location = (TextInputEditText) findViewById(R.id.et_consignee_location);
        et_weight = (TextInputEditText) findViewById(R.id.et_weight);
        et_length = (TextInputEditText) findViewById(R.id.et_length);
        et_width = (TextInputEditText) findViewById(R.id.et_width);
        et_height = (TextInputEditText) findViewById(R.id.et_height);
        et_product = (TextInputEditText) findViewById(R.id.et_product);
        et_packaging_item = (TextInputEditText) findViewById(R.id.et_packaging_item);
        et_item_value = (TextInputEditText) findViewById(R.id.et_item_value);
        et_description = (TextInputEditText) findViewById(R.id.et_description);
        checkbox_insured = (CheckBox) findViewById(R.id.checkbox_insured);
        tv_fee = (TextView) findViewById(R.id.tv_fee);
    }

    private void setValue()
    {
        tv_name.setText(mPickupItem.getConsigneeName());
        tv_address_detail.setText(mPickupItem.getConsigneeAddressDetail());
        tv_address.setText(mPickupItem.getConsigneeAddress() != null && !mPickupItem.getConsigneeAddress().isEmpty() ? mPickupItem.getConsigneeAddress() : mPickupItem.getConsigneeAddressDetail());
        et_consignee_location.setText(mPickupItem.getLocationName());
        et_weight.setText(mPickupItem.getWeightString());
        et_length.setText(mPickupItem.getLengthString());
        et_width.setText(mPickupItem.getWidthString());
        et_height.setText(mPickupItem.getHeightString());
        et_product.setText(mSelectedProduct.getName());
        et_packaging_item.setText(mSelectedPackagingItem.getName());
        checkbox_insured.setChecked(mPickupItem.getInsured());
        et_item_value.setText(mPickupItem.getItemValueString());
        et_description.setText(mPickupItem.getDescription());
        tv_fee.setText(mPickupItem.getTotalFeeString());

        String volumeLabel = Utility.Message.get(R.string.pickup_item_volume) + " (cm3)";
        SpannableString spannableVolumeLabel = new SpannableString(volumeLabel);
        spannableVolumeLabel.setSpan(new SuperscriptSpan(), volumeLabel.length() - 2, volumeLabel.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableVolumeLabel.setSpan(new RelativeSizeSpan(0.5f), volumeLabel.length() - 2, volumeLabel.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_volume_label.setText(spannableVolumeLabel);

        calculateVolume();
    }

    private void setEvent()
    {
        ll_estimated_price_container.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mPickupItem == null || mPickup == null || mPickup.getBranchCode() == null || mPickup.getBranchCode().isEmpty())
                    return;
                if (Utility.Validation.isEditTextEmpty(input_layout_consignee_location, et_consignee_location))
                    return;
                if (mSelectedLocation == null)
                    return;

                PickupItem pickupItemForSimulation = getPickupItemForSimulation();
                EstimatedPriceDetailDialogFragment dialog = EstimatedPriceDetailDialogFragment.newInstance(pickupItemForSimulation, mPickup.getBranchCode(), mPickup.getJetIDCode());
                dialog.setTargetFragment(PickupItemDetailFragment.this, ESTIMATED_PRICE_REQUEST_CODE);
                getNavigator().showDialogFragment(dialog, EstimatedPriceDetailDialogFragment.class.getSimpleName());
            }
        });

        if (isReadOnly())
            return;

        btn_submit_four_digit_pickup_item_code.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                unlockPickupItem();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (!isValid())
                    return;

                String title = mPickupItem.getCode();
                String message = Utility.Message.get(R.string.waybill_create_confirmation_message);
                ConfirmationDialog submitDialog = new ConfirmationDialog(mContext, title, message)
                {
                    @Override
                    public void onOKClicked()
                    {
                        prepareSubmit();
                        submit();
                    }
                };
                submitDialog.show();
            }
        });

        TextWatcher volumeTextWatcher = new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){}
            @Override
            public void afterTextChanged(Editable s)
            {
                calculateVolume();
            }
        };
        et_length.addTextChangedListener(volumeTextWatcher);
        et_width.addTextChangedListener(volumeTextWatcher);
        et_height.addTextChangedListener(volumeTextWatcher);

        et_consignee_location.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AutoCompleteLocationDialogFragment dialog = new AutoCompleteLocationDialogFragment();
                dialog.setTargetFragment(mFragment, LOCATION_REQUEST_CODE);
                dialog.show(getFragmentManager(), AutoCompleteLocationDialogFragment.class.getSimpleName());
            }
        });
        et_product.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ProductItemSelectDialogFragment dialog = new ProductItemSelectDialogFragment();
                dialog.setTargetFragment(mFragment, PRODUCT_REQUEST_CODE);
                dialog.show(getFragmentManager(), ProductItemSelectDialogFragment.class.getSimpleName());
            }
        });
        et_packaging_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PackagingItemSelectDialogFragment dialog = new PackagingItemSelectDialogFragment();
                dialog.setTargetFragment(mFragment, PACKAGING_REQUEST_CODE);
                dialog.show(getFragmentManager(), PackagingItemSelectDialogFragment.class.getSimpleName());
            }
        });
        checkbox_insured.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked || mPickup.isCOD_BO() || mPickup.isCOD_B())
                    input_layout_item_value.setVisibility(View.VISIBLE);
                else
                    input_layout_item_value.setVisibility(View.GONE);
            }
        });
    }

    private void setUIState()
    {
        String title = Utility.Message.get(R.string.pickup_item_unlock_code) + " - ";
        if (mPickupItem.isUnlocked() || mPickupItem.isCompleted())
        {
            title = mPickupItem.getCode();
            showPickupItemDetail();
        }
        else
        {
            title += mPickupItem.getLockedCode();
            showUnlockLayout();
        }
        setTitle(title);

        if (isReadOnly())
        {
            et_consignee_location.setEnabled(false);
            et_weight.setEnabled(false);
            et_product.setEnabled(false);
            et_packaging_item.setEnabled(false);
            et_four_last_digit_pickup_item_code.setEnabled(false);
            et_height.setEnabled(false);
            et_length.setEnabled(false);
            et_width.setEnabled(false);
            et_item_value.setEnabled(false);
            et_description.setEnabled(false);
            checkbox_insured.setEnabled(false);
            btn_submit.setVisibility(View.GONE);
            showPickupItemDetail();
        }

        if (mPickupItem.getInsured() || mPickup.isCOD_BO() || mPickup.isCOD_B())
            input_layout_item_value.setVisibility(View.VISIBLE);
        else
            input_layout_item_value.setVisibility(View.GONE);
    }

    private void unlockPickupItem()
    {
        String lastFourDigit = et_four_last_digit_pickup_item_code.getText().toString().trim();
        String unlockCode = mPickupItem.getPrefixLockedCode() + lastFourDigit;
        if (!mPickupItem.getUnlockCode().equalsIgnoreCase(unlockCode))
        {
            showToast(Utility.Message.get(R.string.failed_to_unlock_pickup_item));
            return;
        }

        mPickupItemUnlockRequestByUnlockCode = new PickupItemUnlockRequestByUnlockCode(mContext, mPickup.getCode(), unlockCode.toUpperCase())
        {
            @Override
            protected void onSuccessOnUIThread(Response<PickupItem> response)
            {
                super.onSuccessOnUIThread(response);
                mPickupItem.setStatus(response.body().getStatus());
                if (getTargetFragment() != null && getTargetFragment() instanceof IPickupItemDetailListener)
                {
                    IPickupItemDetailListener pickupItemDetailListener = (IPickupItemDetailListener) getTargetFragment();
                    pickupItemDetailListener.onItemUnlocked(mPickupItem);
                }
                setUIState();
            }
        };
        mPickupItemUnlockRequestByUnlockCode.executeAsync();

//        /** uletbe*/
//        mPickupItem.setStatus(PickupItem.STATUS_UNLOCKED);
//        if (getTargetFragment() != null && getTargetFragment() instanceof IPickupItemDetailListener)
//        {
//            IPickupItemDetailListener pickupItemDetailListener = (IPickupItemDetailListener) getTargetFragment();
//            pickupItemDetailListener.onItemUnlocked(mPickupItem);
//        }
//        setUIState();
//        /** uletbe*/
    }

    private PickupItem getPickupItemForSimulation()
    {
        PickupItem pickupItemForSimulation = new PickupItem();
        pickupItemForSimulation.update(mPickupItem);
        Double weight = Utility.NumberFormatter.stringToDouble(et_weight.getText().toString().trim());
        Double length = Utility.NumberFormatter.stringToDouble(et_length.getText().toString().trim());
        Double width = Utility.NumberFormatter.stringToDouble(et_width.getText().toString().trim());
        Double height = Utility.NumberFormatter.stringToDouble(et_height.getText().toString().trim());

        pickupItemForSimulation.setWeight(weight);
        pickupItemForSimulation.setLength(length);
        pickupItemForSimulation.setWidth(width);
        pickupItemForSimulation.setHeight(height);
        pickupItemForSimulation.setLocationCode(mSelectedLocation.getCode());
        pickupItemForSimulation.setProductCode(mSelectedProduct.getCode());
        pickupItemForSimulation.setPackagingItemCode(mSelectedPackagingItem.getCode());

        if (checkbox_insured.isChecked() || mPickup.isCOD_BO() || mPickup.isCOD_B())
        {
            Double itemValue = Utility.NumberFormatter.stringToDouble(et_item_value.getText().toString().trim());
            pickupItemForSimulation.setInsured(checkbox_insured.isChecked());
            pickupItemForSimulation.setItemValue(itemValue);
        }

        return pickupItemForSimulation;
    }

    private void prepareSubmit()
    {
        Double weight = Utility.NumberFormatter.stringToDouble(et_weight.getText().toString().trim());
        Double length = Utility.NumberFormatter.stringToDouble(et_length.getText().toString().trim());
        Double width = Utility.NumberFormatter.stringToDouble(et_width.getText().toString().trim());
        Double height = Utility.NumberFormatter.stringToDouble(et_height.getText().toString().trim());
        String description = et_description.getText().toString().trim();

        if (mPickupItem.getConsigneeAddress() == null || mPickupItem.getConsigneeAddress().isEmpty())
            mPickupItem.setConsigneeAddress(mPickupItem.getConsigneeAddressDetail());
        if (mPickupItem.getDropshipperAddress() == null || mPickupItem.getDropshipperAddress().isEmpty())
            mPickupItem.setDropshipperAddress(mPickup.getAddress());
        mPickupItem.setWeight(weight);
        mPickupItem.setLength(length);
        mPickupItem.setWidth(width);
        mPickupItem.setHeight(height);
        mPickupItem.setLocationCode(mSelectedLocation.getCode());
        mPickupItem.setLocationName(mSelectedLocation.getDisplay());
        mPickupItem.setProductCode(mSelectedProduct.getCode());
        mPickupItem.setProductName(mSelectedProduct.getName());
        mPickupItem.setPackagingItemCode(mSelectedPackagingItem.getCode());
        mPickupItem.setPackagingItemName(mSelectedPackagingItem.hasPackaging() ? mSelectedPackagingItem.getName() : "");
        mPickupItem.setDescription(description);

        if (checkbox_insured.isChecked() || mPickup.isCOD_BO() || mPickup.isCOD_B())
        {
            Double itemValue = Utility.NumberFormatter.stringToDouble(et_item_value.getText().toString().trim());
            mPickupItem.setInsured(checkbox_insured.isChecked());
            mPickupItem.setItemValue(itemValue);
        }
    }

    private void submit()
    {
        Log.d("JET_127", "SUBMIT, pickupItem : " + (new Gson()).toJson(mPickupItem));
        mPickupCreateWaybillRequest = new PickupCreateWaybillRequest(mContext, mPickup.getCode(), mPickupItem)
        {
            @Override
            protected void onSuccessOnUIThread(Response<PickupItem> response)
            {
                super.onSuccessOnUIThread(response);
                mPickupItem.update(response.body());
                if (getTargetFragment() != null && getTargetFragment() instanceof IPickupItemDetailListener)
                {
                    IPickupItemDetailListener pickupItemDetailListener = (IPickupItemDetailListener) getTargetFragment();
                    pickupItemDetailListener.onWaybillCreated(mPickupItem);
                }
                showToast(Utility.Message.get(R.string.success));
                getNavigator().back();
            }
        };
        mPickupCreateWaybillRequest.executeAsync();

//        /** uletbe */
//        mPickupItem.setStatus(PickupItem.STATUS_COMPLETED);
//        if (getTargetFragment() != null && getTargetFragment() instanceof IPickupItemDetailListener)
//        {
//            IPickupItemDetailListener pickupItemDetailListener = (IPickupItemDetailListener) getTargetFragment();
//            pickupItemDetailListener.onWaybillCreated(mPickupItem);
//        }
//        showToast(Utility.Message.get(R.string.success));
//        getNavigator().back();
//        /** uletbe */
    }

    private boolean isValid()
    {
        int errorCount = 0;

        if (Utility.Validation.isEditTextEmpty(input_layout_description, et_description))
            errorCount++;

        if (checkbox_insured.isChecked() || mPickup.isCOD_BO() || mPickup.isCOD_B())
        {
            if (Utility.Validation.isNominalInvalid(input_layout_item_value, et_item_value, 0D))
                errorCount++;
        }

//        if (mSelectedPackagingItem == null || mSelectedPackagingItem.getCode() == null || mSelectedPackagingItem.getCode().isEmpty())
//            et_packaging_item.setText("");
//        if (mSelectedProduct == null || mSelectedProduct.getCode() == null || mSelectedProduct.getCode().isEmpty())
//            et_product.setText("");
//        if (mSelectedLocation == null || mSelectedLocation.getCode() == null || mSelectedLocation.getCode().isEmpty())
//            et_consignee_location.setText("");

        if (Utility.Validation.isEditTextEmpty(input_layout_packaging_item, et_packaging_item))
            errorCount++;
        if (Utility.Validation.isEditTextEmpty(input_layout_product, et_product))
            errorCount++;
        if (Utility.Validation.isNominalInvalid(input_layout_weight, et_weight, 0D))
            errorCount++;
        if (Utility.Validation.isNominalInvalid(input_layout_length, et_length, 0D))
            errorCount++;
        if (Utility.Validation.isNominalInvalid(input_layout_width, et_width, 0D))
            errorCount++;
        if (Utility.Validation.isNominalInvalid(input_layout_height, et_height, 0D))
            errorCount++;
        if (Utility.Validation.isEditTextEmpty(input_layout_consignee_location, et_consignee_location))
            errorCount++;

        return errorCount <= 0;
    }

    private void showUnlockLayout()
    {
        rl_four_digit_pickup_item_code_container.setVisibility(View.VISIBLE);
        sv_content_container.setVisibility(View.GONE);
        ll_bottom_container.setVisibility(View.GONE);
    }

    private void showPickupItemDetail()
    {
        Utility.Animation.slideOutToLeft(rl_four_digit_pickup_item_code_container);
        Utility.Animation.slideInFromRight(sv_content_container);
        Utility.Animation.slideInFromRight(ll_bottom_container);
    }

    private void calculateVolume()
    {
        Double volumeLong = Utility.NumberFormatter.stringToDouble(et_length.getText().toString().trim());
        Double volumeWidth = Utility.NumberFormatter.stringToDouble(et_width.getText().toString().trim());
        Double volumeHeight = Utility.NumberFormatter.stringToDouble(et_height.getText().toString().trim());

        if (volumeLong != null && volumeWidth != null && volumeHeight != null)
        {
            Double volume = volumeLong * volumeWidth * volumeHeight;
            String volumeString = Utility.NumberFormatter.doubleToString(volume, 0);
//            String volumeString = String.valueOf(volume);
            tv_volume.setText(volumeString);
        }
        else
            tv_volume.setText(Utility.Message.get(R.string.invalid_volume));
    }

    private boolean isReadOnly()
    {
        return mIsReadOnly == null || mIsReadOnly;
    }

    public interface IPickupItemDetailListener
    {
        void onItemUnlocked(PickupItem pickupItem);
        void onWaybillCreated(PickupItem pickupItem);
    }
}
