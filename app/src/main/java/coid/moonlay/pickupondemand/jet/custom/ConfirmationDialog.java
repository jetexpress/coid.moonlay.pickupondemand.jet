package coid.moonlay.pickupondemand.jet.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.widget.Button;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;

public abstract class ConfirmationDialog extends AlertDialog
{
    private String mTitle;
    private String mMessage;

    public ConfirmationDialog(Context context, String title, String message)
    {
        super(context);
        mTitle = title;
        mMessage = message;
        initialize(context);
    }

    private void initialize(Context context)
    {
        setButton(BUTTON_POSITIVE, Utility.Message.get(R.string.yes), new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                onOKClicked();
            }
        });
        setButton(BUTTON_NEGATIVE, Utility.Message.get(R.string.cancel), new OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                onCancelClicked();
            }
        });
        setTitle(mTitle);
        setMessage(mMessage);
        setCancelable(true);
        setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialog)
            {
                Button buttonPositive = getButton(DialogInterface.BUTTON_POSITIVE);
                buttonPositive.setTextColor(Color.BLACK);
                Button buttonNegative = getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonNegative.setTextColor(Color.BLACK);
            }
        });
    }

    public void onCancelClicked()
    {
        dismiss();
    }
    public abstract void onOKClicked();
}