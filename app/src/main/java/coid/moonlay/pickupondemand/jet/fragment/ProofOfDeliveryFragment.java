package coid.moonlay.pickupondemand.jet.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseItemSelectDialogFragment;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.config.AppConfig;
import coid.moonlay.pickupondemand.jet.model.Relation;
import coid.moonlay.pickupondemand.jet.model.SuccessDelivery;
import coid.moonlay.pickupondemand.jet.model.Task;
import coid.moonlay.pickupondemand.jet.request.DeliverySuccessRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProofOfDeliveryFragment extends BaseMainFragment
{
    private static final int RELATION_REQUEST_CODE = 235;
    private static final int SIGNATURE_REQUEST_CODE = 236;
    public static final String TASK_ARGS_PARAM = "TaskParam";

    private TextInputLayout input_layout_recipient, input_layout_relation, input_layout_signature;
    private RelativeLayout rl_signature_container;
    private TextInputEditText et_recipient, et_relation;
    private ImageView img_signature;
    private EditText et_note;
    private Button btn_finish;

    private Bitmap mBitmapSignature;
    private Relation mRelation;
    private Task mTask;
    private Double mLatitude;
    private Double mLongitude;

    public ProofOfDeliveryFragment()
    {
        // Required empty public constructor
    }

    public static ProofOfDeliveryFragment newInstance(Task task)
    {
        ProofOfDeliveryFragment fragment = new ProofOfDeliveryFragment();
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
        return inflater.inflate(R.layout.fragment_proof_of_delivery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setEvent();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Location location = Utility.Location.getCurrentLocationByFused(getBaseActivity().getGoogleApiClient());
        if (location != null)
        {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
        }
        else
        {
            mLatitude = 0D;
            mLongitude = 0D;
        }

    }

    @Override
    public void onResume()
    {
        super.onResume();
        setTitle(Utility.Message.get(R.string.proof_of_delivery));
        setNotificationMenuEnabled(false);
        setDisplayBackEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            CharSequence code;
            CharSequence description;
            switch (requestCode)
            {
                case RELATION_REQUEST_CODE :
                    code = data.getStringExtra(BaseItemSelectDialogFragment.SELECTED_ITEM_CODE_ARGS_PARAM);
                    description = data.getStringExtra(BaseItemSelectDialogFragment.SELECTED_ITEM_DESCRIPTION_ARGS_PARAM);
                    mRelation = new Relation();
                    mRelation.setName(description.toString());
                    et_relation.setText(mRelation.getName());
                    break;
                case SIGNATURE_REQUEST_CODE :
                    if (data != null && data.hasExtra(SignatureFragment.SIGNATURE_BITMAP_ARGS_PARAM))
                    {
                        mBitmapSignature = data.getParcelableExtra(SignatureFragment.SIGNATURE_BITMAP_ARGS_PARAM);
                        mBitmapSignature = Utility.Image.resize(mBitmapSignature, AppConfig.IMAGE_PICKER_MIN_WIDTH, AppConfig.IMAGE_PICKER_MIN_HEIGHT);
                        input_layout_signature.setErrorEnabled(false);
                        img_signature.setImageBitmap(mBitmapSignature);
                    }
                    break;
                default: break;
            }
        }
    }

    private void setView()
    {
        rl_signature_container = (RelativeLayout) findViewById(R.id.rl_signature_container);
        input_layout_recipient = (TextInputLayout) findViewById(R.id.input_layout_recipient);
        input_layout_relation = (TextInputLayout) findViewById(R.id.input_layout_relation);
        input_layout_signature = (TextInputLayout) findViewById(R.id.input_layout_signature);
        et_recipient = (TextInputEditText) findViewById(R.id.et_recipient);
        et_relation = (TextInputEditText) findViewById(R.id.et_relation);
        img_signature = (ImageView) findViewById(R.id.img_signature);
        et_note = (EditText) findViewById(R.id.et_note);
        btn_finish = (Button) findViewById(R.id.btn_finish);
//        /**ULETBE DUMMY TASK*/
//        btn_finish.setVisibility(View.GONE);
//        /**ULETBE DUMMY TASK*/
    }

    private void setEvent()
    {
        et_relation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (getFragmentManager() != null)
                {
                    RelationSelectDialogFragment dialog = new RelationSelectDialogFragment();
                    dialog.setTargetFragment(mFragment, RELATION_REQUEST_CODE);
                    dialog.show(getFragmentManager(), RelationSelectDialogFragment.class.getSimpleName());
                }
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utility.UI.hideKeyboard(mView);
                submit();
            }
        });
        rl_signature_container.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (getFragmentManager() != null)
                {
                    SignatureFragment signatureFragment;
                    if (mBitmapSignature != null)
                        signatureFragment = SignatureFragment.newInstance(mBitmapSignature);
                    else
                        signatureFragment = new SignatureFragment();

                    signatureFragment.setTargetFragment(ProofOfDeliveryFragment.this, SIGNATURE_REQUEST_CODE);
                    signatureFragment.show(getFragmentManager(), ProofOfDeliveryFragment.this.getClass().getSimpleName());
                }
            }
        });
        et_recipient.addTextChangedListener(Utility.Validation.getValidateEmptyTextWatcher(input_layout_recipient, et_recipient));
        et_relation.addTextChangedListener(Utility.Validation.getValidateEmptyTextWatcher(input_layout_relation, et_relation));
    }

    private Boolean isValid()
    {
        Integer errorCount = 0;

        if (mBitmapSignature == null)
        {
            errorCount++;
            input_layout_signature.setError(Utility.Message.get(R.string.proof_of_delivery_signature_required));
        }

        if (Utility.Validation.isEditTextEmpty(input_layout_relation, et_relation))
            errorCount++;
        if (Utility.Validation.isEditTextEmpty(input_layout_recipient, et_recipient))
            errorCount++;

        return errorCount <= 0;
    }

    private void submit()
    {
        if (!isValid())
            return;

        SuccessDelivery successDelivery = new SuccessDelivery();
        successDelivery.setReceiverName(et_recipient.getText().toString().trim());
        successDelivery.setRelation(et_relation.getText().toString().trim());
        successDelivery.setLatitude(mLatitude);
        successDelivery.setLongitude(mLongitude);
        successDelivery.setBase64ReceiverSignature(Utility.Image.bitmapToBase64String(mBitmapSignature));
        successDelivery.setNote(et_note.getText().toString().trim());

        DeliverySuccessRequest deliverySuccessRequest = new DeliverySuccessRequest(mContext, mTask.getDrsCode(), mTask.getCode(), successDelivery);
        deliverySuccessRequest.executeAsync();
    }
}
