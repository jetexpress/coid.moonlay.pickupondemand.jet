package coid.moonlay.pickupondemand.jet.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.base.BaseActivity;
import coid.moonlay.pickupondemand.jet.fragment.LoginFragment;

public class LoginActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getNavigator().showDefaultFragment();
    }

    @Override
    public void onBackPressed()
    {
        getNavigator().back();
    }

    @Override
    public ActionBarDrawerToggle getDrawerToggle()
    {
        return null;
    }

    @Override
    public Integer getFragmentContainerId()
    {
        return R.id.login_fragment_container;
    }

    @Override
    public Fragment getDefaultFragment()
    {
        return new LoginFragment();
    }
}
