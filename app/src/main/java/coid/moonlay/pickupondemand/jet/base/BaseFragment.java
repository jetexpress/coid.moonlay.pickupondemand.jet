package coid.moonlay.pickupondemand.jet.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.Toast;

import coid.moonlay.pickupondemand.jet.Navigator;
import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;

public class BaseFragment extends Fragment
{
    public Context mContext;
    public View mView;
    public Fragment mFragment;

    private ProgressDialog mProgressDialog;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mFragment = this;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        mDrawerToggle = getBaseActivity().getDrawerToggle();
    }

    @Override
    public void onResume() {
        super.onResume();
        setDisplayBackEnabled(false);
    }

    @Override
    public void onDestroy() {
        hideLoadingDialog();
        Utility.UI.hideKeyboard(mView);
        super.onDestroy();
    }

    protected View findViewById(Integer id)
    {
        return mView.findViewById(id);
    }

    protected BaseActivity getBaseActivity()
    {
        return (BaseActivity) getActivity();
    }

    protected Navigator getNavigator()
    {
        return getBaseActivity().getNavigator();
    }

    protected void setTitle(String title)
    {
        getActivity().setTitle(title);
    }

    protected void setDisplayBackEnabled(boolean isDisplayBackEnabled)
    {
        ActionBar actionBar = getBaseActivity().getSupportActionBar();
        if (actionBar != null) {
            mDrawerToggle.setDrawerIndicatorEnabled(!isDisplayBackEnabled);
            actionBar.setDisplayHomeAsUpEnabled(isDisplayBackEnabled);
            mDrawerToggle.syncState();
            mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mDrawerToggle.isDrawerIndicatorEnabled()) {
                        getNavigator().back();
                    }
                }
            });
        }
    }

    protected void hideDrawerToggle()
    {
        mDrawerToggle.setDrawerIndicatorEnabled(false);
    }

    public void showLoadingDialog()
    {
        mProgressDialog = new ProgressDialog(mContext, R.style.ProgressDialog);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();
    }

    public void showLoadingDialog(String message)
    {
        if (message == null) message = Utility.Message.get(R.string.loading);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void hideLoadingDialog()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing())
        {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected void showToast(String toastMessage)
    {
        Toast.makeText(mContext, toastMessage, Toast.LENGTH_LONG).show();
    }

}
