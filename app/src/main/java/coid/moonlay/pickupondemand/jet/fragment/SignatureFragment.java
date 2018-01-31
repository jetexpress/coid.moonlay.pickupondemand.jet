package coid.moonlay.pickupondemand.jet.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.gcacace.signaturepad.views.SignaturePad;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.base.BaseDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignatureFragment extends BaseDialogFragment
{
    public static final String SIGNATURE_BITMAP_ARGS_PARAM = "signatureBitmapParam";

    private SignaturePad signature_pad;
    private Button btn_clear, btn_save;
    private Bitmap mSignatureBitmap;

    public SignatureFragment()
    {
        // Required empty public constructor

    }

    public static SignatureFragment newInstance(Bitmap signatureBitmap)
    {
        SignatureFragment fragment = new SignatureFragment();
        Bundle args = new Bundle();
        args.putParcelable(SIGNATURE_BITMAP_ARGS_PARAM, signatureBitmap);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, R.style.CustomDialog);
        if (getArguments() != null && getArguments().containsKey(SIGNATURE_BITMAP_ARGS_PARAM))
        {
            mSignatureBitmap = getArguments().getParcelable(SIGNATURE_BITMAP_ARGS_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signature, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setEvent();
    }

    @Override
    public void onStart() {
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

    private void setView()
    {
        signature_pad = (SignaturePad) findViewById(R.id.signature_pad);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_save = (Button) findViewById(R.id.btn_save);

        if (mSignatureBitmap != null)
        {
            signature_pad.setSignatureBitmap(mSignatureBitmap);
            setButtonEnabled(true);
        }
        else
            setButtonEnabled(false);
    }

    private void setEvent()
    {
        signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener()
        {
            @Override
            public void onStartSigning()
            {

            }

            @Override
            public void onSigned()
            {
                setButtonEnabled(true);
            }

            @Override
            public void onClear()
            {
                setButtonEnabled(false);
                mSignatureBitmap = null;
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                signature_pad.clear();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mSignatureBitmap = signature_pad.getSignatureBitmap();
                if (getTargetFragment() != null)
                {
                    Intent bitmapDataIntent = new Intent();
                    bitmapDataIntent.putExtra(SIGNATURE_BITMAP_ARGS_PARAM, mSignatureBitmap);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, bitmapDataIntent);
                    dismiss();
                }
            }
        });
    }

    private void setButtonEnabled(boolean isEnabled)
    {
        btn_save.setEnabled(isEnabled);
        btn_clear.setEnabled(isEnabled);
        if (isEnabled)
        {
            btn_save.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_bg_rounded_square_red_fill));
            btn_clear.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_bg_rounded_square_black_fill));
        }
        else
        {
            btn_save.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_bg_rounded_square_disabled_fill));
            btn_clear.setBackground(ContextCompat.getDrawable(mContext, R.drawable.custom_bg_rounded_square_disabled_fill));
        }
    }

}
