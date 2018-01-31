package coid.moonlay.pickupondemand.jet.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;

import coid.moonlay.pickupondemand.jet.JetApplication;
import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseFragment;
import coid.moonlay.pickupondemand.jet.config.ApiConfig;
import coid.moonlay.pickupondemand.jet.request.LoginExternalRequest;
import coid.moonlay.pickupondemand.jet.request.LoginRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment
{
    private static final int GOOGLE_SIGN_IN_RESULT_CODE = 127;
    private TextInputLayout input_layout_username, input_layout_password;
    private TextInputEditText et_username, et_password;
    private Button btn_login, btn_facebook_login, btn_google_login;
    private TextView tv_forgot;
    private GoogleApiClient mGoogleApiClient;
    private CallbackManager mFacebookCallbackManager;

    public LoginFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setupFacebookLogin();
        setupGoogleLogin();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setEvent();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN_RESULT_CODE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess() && result.getSignInAccount() != null)
            {
                String accessToken = result.getSignInAccount().getIdToken();
                loginGoogle(accessToken);
            }
        }
    }

    @Override
    public void onStop()
    {
        JetApplication.getInstance().stopPickupNotificationSound();
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        JetApplication.getInstance().stopPickupNotificationSound();
        super.onDestroy();
    }

    private void setView()
    {
        input_layout_username = (TextInputLayout) findViewById(R.id.input_layout_username);
        input_layout_password = (TextInputLayout) findViewById(R.id.input_layout_password);
        et_username = (TextInputEditText) findViewById(R.id.et_username);
        et_password = (TextInputEditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_facebook_login = (Button) findViewById(R.id.btn_facebook_login);
        btn_google_login = (Button) findViewById(R.id.btn_google_login);
        tv_forgot = (TextView) findViewById(R.id.tv_forgot);
    }

    private void setEvent()
    {
        btn_facebook_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginManager.getInstance().logInWithReadPermissions(mFragment, Arrays.asList("email", "public_profile"));
            }
        });

        btn_google_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN_RESULT_CODE);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                login();
            }
        });

        tv_forgot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                forgotPassword();
            }
        });

//        Button btn_play = (Button) findViewById(R.id.btn_play);
//        btn_play.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                JetApplication.getInstance().playPickupNotificationSound();
//            }
//        });
//
//        Button btn_stop = (Button) findViewById(R.id.btn_stop);
//        btn_stop.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                JetApplication.getInstance().stopPickupNotificationSound();
//            }
//        });
    }

    private void login()
    {
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        LoginRequest loginRequest = new LoginRequest(mContext, username, password);
        loginRequest.executeAsync();
    }

    private void loginFacebook(String accessToken)
    {
        LoginExternalRequest loginExternalRequest = new LoginExternalRequest(mContext, accessToken, LoginExternalRequest.PROVIDER_FACEBOOK);
        loginExternalRequest.executeAsync();
    }

    private void loginGoogle(String accessToken)
    {
        LoginExternalRequest loginExternalRequest = new LoginExternalRequest(mContext, accessToken, LoginExternalRequest.PROVIDER_GOOGLE);
        loginExternalRequest.executeAsync();
    }

    private void forgotPassword()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ApiConfig.FORGOT_PASSWORD_URL));
        startActivity(intent);
    }

    private void setupFacebookLogin()
    {
        mFacebookCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                String facebookAccessToken = loginResult.getAccessToken().getToken();
                loginFacebook(facebookAccessToken);
            }

            @Override
            public void onCancel(){}

            @Override
            public void onError(FacebookException error)
            {
                showToast(Utility.Message.get(R.string.login_facebook_failed) + ", " + error.toString());
            }
        });
    }

    private void setupGoogleLogin()
    {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder()
                .requestIdToken(Utility.Message.get(R.string.google_sign_in_client_id))
                .build();

        FragmentActivity fa = null;
        if (mContext instanceof FragmentActivity)
            fa = (FragmentActivity) mContext;

        if (fa != null)
        {
            // Build a GoogleApiClient with access to the Google Sign-In API and the
            // options specified by gso.
            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    .enableAutoManage(fa, null)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }
}
