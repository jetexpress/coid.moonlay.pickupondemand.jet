package coid.moonlay.pickupondemand.jet.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.model.UserProfile;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.request.ChangePasswordRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends BaseMainFragment
{
    private TextInputLayout input_layout_old_password, input_layout_new_password, input_layout_confirm_password;
    private TextInputEditText et_old_password, et_new_password, et_confirm_password;
    private Button btn_change_password;

    public ChangePasswordFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setEvent();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setTitle(Utility.Message.get(R.string.change_password));
        setDisplayBackEnabled(true);
        setNotificationMenuEnabled(false);
    }

    private void setView()
    {
        input_layout_old_password = (TextInputLayout) findViewById(R.id.input_layout_old_password);
        input_layout_new_password = (TextInputLayout) findViewById(R.id.input_layout_new_password);
        input_layout_confirm_password = (TextInputLayout) findViewById(R.id.input_layout_confirm_password);
        et_old_password = (TextInputEditText) findViewById(R.id.et_old_password);
        et_new_password = (TextInputEditText) findViewById(R.id.et_new_password);
        et_confirm_password = (TextInputEditText) findViewById(R.id.et_confirm_password);
        btn_change_password = (Button) findViewById(R.id.btn_change_password);
    }

    private void setEvent()
    {
        btn_change_password.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changePassword();
            }
        });
    }

    private void changePassword()
    {
        if (!isValid())
            return;

        UserProfile userProfile = DBQuery.getSingle(UserProfile.class);
        String oldPassword = et_old_password.getText().toString().trim();
        String newPassword = et_new_password.getText().toString().trim();
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest(mContext, userProfile.getUsername(), oldPassword, newPassword);
        changePasswordRequest.executeAsync();
    }

    private Boolean isValid()
    {
        String oldPassword = et_old_password.getText().toString().trim();
        String newPassword = et_new_password.getText().toString().trim();
        String confirmPassword = et_confirm_password.getText().toString().trim();

        if (oldPassword.isEmpty())
        {
            showToast(Utility.Message.get(R.string.old_password_required));
            return false;
        }

        if (newPassword.isEmpty())
        {
            showToast(Utility.Message.get(R.string.new_password_required));
            return false;
        }

        if (confirmPassword.isEmpty())
        {
            showToast(Utility.Message.get(R.string.confirm_password_required));
            return false;
        }

        if (!newPassword.equals(confirmPassword))
        {
            showToast(Utility.Message.get(R.string.confirm_password_not_match));
            return false;
        }

        return true;
    }
}
