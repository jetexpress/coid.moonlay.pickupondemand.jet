package coid.moonlay.pickupondemand.jet.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseItemSelectDialogFragment;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.config.AppConfig;
import coid.moonlay.pickupondemand.jet.model.Relation;
import coid.moonlay.pickupondemand.jet.model.SuccessPRS;
import coid.moonlay.pickupondemand.jet.model.Task;
import coid.moonlay.pickupondemand.jet.request.PrsSuccessRequest;

/**
 * Created by jordan.leonardi on 3/5/2018.
 */

public class PrsCompleteFragment extends BaseMainFragment {

    private static final int SIGNATURE_REQUEST_CODE = 236;
    private static final int REQUEST_IMAGE_CAPTURE = 240;
    public static final String TASK_ARGS_PARAM = "TaskParam";
    private Task mTask;
    private TextInputLayout input_layout_signature_prs;
    private RelativeLayout rl_signature_container_prs, rl_photo_prs_complete;
    private TextView tv_name, tv_address, tv_schedule;
    private EditText et_jetid_totalpickup_complete;
    private ImageView img_signature_prs, img_photo_prs;
    private Button btn_finish_prs,btn_upload_prs;
    private Bitmap mBitmapSignature;
    private Bitmap mPhotoPrs;
    private String scheduleFormatted;
    private Double mLatitude;
    private Double mLongitude;

    public PrsCompleteFragment()
    {
        // Required empty public constructor
    }
    public static PrsCompleteFragment newInstance(Task task)
    {
        PrsCompleteFragment fragment = new PrsCompleteFragment();
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
        return inflater.inflate(R.layout.fragment_complete_prs, container, false);
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
        setTitle(Utility.Message.get(R.string.pickup_run_sheet));
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
                case SIGNATURE_REQUEST_CODE :
                    if (data != null && data.hasExtra(SignatureFragment.SIGNATURE_BITMAP_ARGS_PARAM))
                    {
                        mBitmapSignature = data.getParcelableExtra(SignatureFragment.SIGNATURE_BITMAP_ARGS_PARAM);
                        mBitmapSignature = Utility.Image.resize(mBitmapSignature, AppConfig.IMAGE_PICKER_MIN_WIDTH, AppConfig.IMAGE_PICKER_MIN_HEIGHT);
                        input_layout_signature_prs.setErrorEnabled(false);
                        img_signature_prs.setImageBitmap(mBitmapSignature);
                    }
                    break;
                case REQUEST_IMAGE_CAPTURE :
                    Bundle extras = data.getExtras();
                    mPhotoPrs = (Bitmap) extras.get("data");
                    mPhotoPrs = Utility.Image.resize(mPhotoPrs, AppConfig.IMAGE_PICKER_MIN_WIDTH, AppConfig.IMAGE_PICKER_MIN_HEIGHT);
                    input_layout_signature_prs.setErrorEnabled(false);
                    img_photo_prs.setImageBitmap(mPhotoPrs);
                default: break;
            }
        }
    }

    private void setValue(){
        tv_name.setText(mTask.getName());
        tv_address.setText(mTask.getAddress());
        if(mTask.getScheduleDate().length()> 10){
            scheduleFormatted = mTask.getScheduleDate().substring(0, mTask.getScheduleDate().length()-8);
        }
        else {
            scheduleFormatted = "";
        }
        tv_schedule.setText(scheduleFormatted);
    }

    private void setView()
    {
        rl_signature_container_prs = (RelativeLayout) findViewById(R.id.rl_signature_container_prs);
        rl_photo_prs_complete = (RelativeLayout) findViewById(R.id.rl_photo_prs_container);
        input_layout_signature_prs = (TextInputLayout) findViewById(R.id.input_layout_signature_prs);
        img_signature_prs = (ImageView) findViewById(R.id.img_signature_prs);
        img_photo_prs = (ImageView)findViewById(R.id.img_photo_prs);
        btn_finish_prs = (Button) findViewById(R.id.btn_finish_prs);
        btn_upload_prs = (Button)findViewById(R.id.btn_upload_prs);
        tv_name = (TextView)findViewById(R.id.tv_jetid_complete);
        tv_address = (TextView)findViewById(R.id.tv_jetid_address_complete);
        tv_schedule = (TextView)findViewById(R.id.tv_jetid_schedule_complete);
        et_jetid_totalpickup_complete = (EditText) findViewById(R.id.et_jetid_totalpickup_complete);
//        /**ULETBE DUMMY TASK*/
//        btn_finish.setVisibility(View.GONE);
//        /**ULETBE DUMMY TASK*/
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

    private void setEvent()
    {

        btn_finish_prs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Utility.UI.hideKeyboard(mView);
                submit();
            }
        });
        rl_signature_container_prs.setOnClickListener(new View.OnClickListener()
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

                    signatureFragment.setTargetFragment(PrsCompleteFragment.this, SIGNATURE_REQUEST_CODE);
                    signatureFragment.show(getFragmentManager(), PrsCompleteFragment.this.getClass().getSimpleName());
                }
            }
        });

        btn_upload_prs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

    }

    private boolean isValid(){
        Integer errorCount = 0;

        if(mBitmapSignature == null){
            errorCount++;
        }
        if(et_jetid_totalpickup_complete.getText() == null){
            errorCount++;
        }

        return errorCount<=0;
    }

    private void submit(){
        if(!isValid()) {
            Toast.makeText(getContext(), "Mohon isi Field yang kosong", Toast.LENGTH_LONG).show();
        }else{
            SuccessPRS successPrs = new SuccessPRS();
            successPrs.setLatitude(mLatitude);
            successPrs.setLongitude(mLongitude);
            successPrs.setQuickPickupItemCount(et_jetid_totalpickup_complete.getText().toString());
            successPrs.setImageBase64(Utility.Image.bitmapToBase64String(mPhotoPrs));
            successPrs.setSignatureBase64(Utility.Image.bitmapToBase64String(mBitmapSignature));
            successPrs.setJetIDCode(mTask.getJetIdCode());

            PrsSuccessRequest prsSuccessRequest = new PrsSuccessRequest(mContext, mTask.getCrsItemId(), successPrs);
            prsSuccessRequest.executeAsync();
        }
    }
}
