package coid.moonlay.pickupondemand.jet.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import coid.moonlay.pickupondemand.jet.BuildConfig;
import coid.moonlay.pickupondemand.jet.JetApplication;
import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseActivity;
import coid.moonlay.pickupondemand.jet.config.AppConfig;
import coid.moonlay.pickupondemand.jet.custom.ConfirmationDialog;
import coid.moonlay.pickupondemand.jet.fragment.ChangePasswordFragment;
import coid.moonlay.pickupondemand.jet.fragment.DashboardFragment;
import coid.moonlay.pickupondemand.jet.fragment.PickupDetailFragment;
import coid.moonlay.pickupondemand.jet.fragment.TaskConfirmationDialogFragment;
import coid.moonlay.pickupondemand.jet.fragment.TaskFragment;
import coid.moonlay.pickupondemand.jet.fragment.UpdateRequiredDialogFragment;
import coid.moonlay.pickupondemand.jet.model.Login;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.model.Task;
import coid.moonlay.pickupondemand.jet.model.UpdateInfo;
import coid.moonlay.pickupondemand.jet.model.UserProfile;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;
import coid.moonlay.pickupondemand.jet.model.db.ModelLoader;
import coid.moonlay.pickupondemand.jet.network.DataSync;
import coid.moonlay.pickupondemand.jet.notification.NotificationReceivedHandler;
import coid.moonlay.pickupondemand.jet.request.PickupAssignRequest;
import coid.moonlay.pickupondemand.jet.request.SendLocationLogRequest;
import coid.moonlay.pickupondemand.jet.request.UpdateInfoRequest;
import retrofit2.Response;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        BaseActivity.ILocationPermissionListener,
        TaskConfirmationDialogFragment.ITaskConfirmationOnClickListener,
        NotificationReceivedHandler.INotificationReceivedListener
{
    public static final String EXTRA_PARAM = "MainActivityExtraParam";
    public static final int ONLINE_STATUS_INDICATOR_NOTIFICATION_ID = 1270;

    private FusedLocationProviderClient mFusedLocationClient;
    private ActionBarDrawerToggle mDrawerToggle;
    private View mNavigationViewHeader;
    private LinearLayout ll_no_internet_container;
    private RelativeLayout rl_no_internet_close_container;
    private ImageView img_no_internet_close;
    private SendLocationLogRequest mSendLocationLogRequest;
    private UpdateInfoRequest mUpdateInfoRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Login login = DBQuery.getSingle(Login.class);
        if (Login.isValid(login))
        {
            DataSync.start();
            setContentView(R.layout.activity_main);
            setView();
            showCurrentFragment();
            JetApplication.notificationReceivedHandler.setOnReceivedListener(this);

            /** ULETBE HIDE TUTORIAL */
//            if (isFirstTimeOpened())
//            {
//                Intent tutorialIntent = new Intent(this, TutorialActivity.class);
//                startActivity(tutorialIntent);
//            }
            /** ULETBE HIDE TUTORIAL */

//            /** uletbe*/
//            Task testTask = new Task();
//            testTask.setStatus(Pickup.STATUS_CANCELLED);
//            testTask.setCode("testCode");
//            showTaskInfo(testTask);
//            /** uletbe*/
        }
        else
        {
            finish();
            getNavigator().navigateToActivity(LoginActivity.class);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if (getSupportLoaderManager().getLoader(AppConfig.LoaderId.USER_PROFILE_LOADER_ID) != null)
            getSupportLoaderManager().restartLoader(AppConfig.LoaderId.USER_PROFILE_LOADER_ID, null, userLoader);
        else
            getSupportLoaderManager().initLoader(AppConfig.LoaderId.USER_PROFILE_LOADER_ID, null, userLoader);
        setLocationPermissionListener(this);
        checkLocationPermission();
    }

    @Override
    protected void onPostResume()
    {
        super.onPostResume();
        checkUpdateRequired();
        /** ULETBE HIDE TUTORIAL */
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        if (navigationView != null)
//        {
//            Menu navigationMenu = navigationView.getMenu();
//            if (navigationMenu != null)
//            {
//                MenuItem pickupHelpMenuItem = navigationMenu.findItem(R.id.nav_help);
//                if (pickupHelpMenuItem != null)
//                {
//                    if(isFirstTimeOpened())
//                        pickupHelpMenuItem.setVisible(false);
//                    else
//                        pickupHelpMenuItem.setVisible(true);
//                }
//            }
//        }
        /** ULETBE HIDE TUTORIAL */
    }

    @Override
    protected void onDestroy()
    {
        NotificationManager notificationManager;
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(ONLINE_STATUS_INDICATOR_NOTIFICATION_ID);

        JetApplication.getInstance().stopPickupNotificationSound();
        JetApplication.notificationReceivedHandler.clearListener();

        cancelSendLocationLog();
        clearLocationPermissionListener();
        super.onDestroy();
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        } else
        {
            getNavigator().back();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        mMenu = menu;
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_notification)
//        {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard)
        {
            getNavigator().popToDefaultFragment();
        }
        else if (id == R.id.nav_task_list)
        {
            getNavigator().popToDefaultFragment();
            getNavigator().showFragment(new TaskFragment());
        }
        /** ULETBE HIDE TUTORIAL */
//        else if (id == R.id.nav_help)
//        {
//            Intent tutorialIntent = new Intent(MainActivity.this, TutorialActivity.class);
//            startActivity(tutorialIntent);
//        }
        /** ULETBE HIDE TUTORIAL */

//        else if (id == R.id.nav_proof)
//        {
//            getNavigator().showFragment(new ProofOfDeliveryFragment());
//        }
//        else if (id == R.id.nav_outlet_location)
//        {
//            getNavigator().showFragment(new OutletLocationFragment());
//        }
        else if (id == R.id.nav_logout)
        {
            DBQuery.truncate(Login.class);
            DBQuery.truncate(UserProfile.class);
            Utility.OneSignal.deleteTags();
            finish();
            getNavigator().navigateToActivity(LoginActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public ActionBarDrawerToggle getDrawerToggle()
    {
        return mDrawerToggle;
    }

    @Override
    public Integer getFragmentContainerId()
    {
        return R.id.main_fragment_container;
    }

    @Override
    public Fragment getDefaultFragment()
    {
        return new DashboardFragment();
    }

    @Override
    public void onAcceptTaskFromNotification(final Task task)
    {
        PickupAssignRequest pickupAssignRequest = new PickupAssignRequest(this, task.getCode())
        {
            @Override
            protected void onSuccessOnUIThread(Response<Pickup> response)
            {
                super.onSuccessOnUIThread(response);
                showToast(Utility.Message.get(R.string.notification_task_received));
                getNavigator().popToDefaultFragment();
                getNavigator().showFragment(PickupDetailFragment.newInstance(task));
            }
        };
        pickupAssignRequest.executeAsync();
    }

    @Override
    public void onNotificationReceived(final Task task)
    {
        runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                showTaskInfo(task);
            }
        });
    }

    @Override
    public void onLocationPermissionGranted()
    {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onLocationPermissionDenied()
    {
        finish();
    }

    private void setView()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        mNavigationViewHeader = navigationView.getHeaderView(0);

        ll_no_internet_container = (LinearLayout) findViewById(R.id.ll_no_internet_container);
        View.OnClickListener closeNoInternetLayoutOnClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                hideNoInternetWarning();
            }
        };
        rl_no_internet_close_container = (RelativeLayout) findViewById(R.id.rl_no_internet_close_container);
        rl_no_internet_close_container.setOnClickListener(closeNoInternetLayoutOnClickListener);
        img_no_internet_close = (ImageView) findViewById(R.id.img_no_internet_close);
        img_no_internet_close.setOnClickListener(closeNoInternetLayoutOnClickListener);
    }

    private void setValue(UserProfile userProfile)
    {
        TextView tv_username = (TextView) mNavigationViewHeader.findViewById(R.id.tv_username);
        TextView tv_email = (TextView) mNavigationViewHeader.findViewById(R.id.tv_email);
        tv_username.setText(userProfile.getFullName());
        tv_email.setText(userProfile.getEmail());

        View.OnClickListener changePasswordListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getNavigator().showFragment(new ChangePasswordFragment());
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        };

        tv_username.setOnClickListener(changePasswordListener);
        tv_email.setOnClickListener(changePasswordListener);
    }

    /** ULETBE HIDE TUTORIAL */
//    private Boolean isFirstTimeOpened()
//    {
//        SharedPreferences pref = getSharedPreferences(AppConfig.JET_SHARED_PREFERENCES, MODE_PRIVATE);
//        return pref.getBoolean(AppConfig.FIRST_TIME_OPENED_PARAM_KEY, true);
//    }
    /** ULETBE HIDE TUTORIAL */

    public void showCurrentFragment()
    {
        getNavigator().showDefaultFragment();
        if (getIntent() != null)
        {
            Task task = getIntent().getParcelableExtra(EXTRA_PARAM);
            showTaskInfo(task);
        }
    }

    private void showTaskInfo(Task task)
    {
        if (task != null)
        {
            if (task.isRequested())
            {
                TaskConfirmationDialogFragment dialog = TaskConfirmationDialogFragment.newInstance(task);
                dialog.show(getSupportFragmentManager(), TaskConfirmationDialogFragment.class.getSimpleName());
            }
            else if (task.isAssigned())
            {
                getNavigator().popToDefaultFragment();
                getNavigator().showFragment(new TaskFragment());
            }
            else if (task.isCancelled())
            {
                getNavigator().popToDefaultFragment();
                getNavigator().showFragment(new TaskFragment());

                String title = Utility.Message.get(R.string.pickup_cancel_trip_title);
                String message = task.getCode() + " " + Utility.Message.get(R.string.pickup_cancelled_by_customer);
                final ConfirmationDialog taskCancelledDialog = new ConfirmationDialog(this, title, message)
                {
                    @Override
                    public void onOKClicked()
                    {
                        dismiss();
                    }
                };
                taskCancelledDialog.setCancelable(false);
                taskCancelledDialog.setOnShowListener(new DialogInterface.OnShowListener()
                {
                    @Override
                    public void onShow(DialogInterface dialog)
                    {
                        Button buttonPositive = taskCancelledDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        buttonPositive.setTextColor(Color.BLACK);
                        buttonPositive.setText(Utility.Message.get(R.string.ok));
                        Button buttonNegative = taskCancelledDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                        buttonNegative.setEnabled(false);
                        buttonNegative.setVisibility(View.GONE);
                    }
                });
                taskCancelledDialog.show();
            }
        }
    }

    public void showNoInternetWarning()
    {
        if (ll_no_internet_container != null)
            ll_no_internet_container.setVisibility(View.VISIBLE);
    }

    public void hideNoInternetWarning()
    {
        if (ll_no_internet_container != null)
            ll_no_internet_container.setVisibility(View.GONE);
    }

    private void checkUpdateRequired()
    {
        mUpdateInfoRequest = new UpdateInfoRequest(this, BuildConfig.VERSION_CODE)
        {
            @Override
            protected void onSuccessOnUIThread(Response<UpdateInfo> response)
            {
                if (response.body().getMessage() != null && !response.body().getMessage().isEmpty())
                {
                    UpdateRequiredDialogFragment dialog = UpdateRequiredDialogFragment.newInstance(response.body().isForceUpdate(), response.body().getMessage());
                    dialog.show(getSupportFragmentManager(), UpdateRequiredDialogFragment.class.getSimpleName());
                }
            }
        };
        mUpdateInfoRequest.executeAsync();
    }

    public void requestSendLocationLog()
    {
        mSendLocationLogRequest = new SendLocationLogRequest(mContext);

        mSendLocationLogRequest.stopInterval();
        mSendLocationLogRequest.executeInterval(AppConfig.LOCATION_LOG_INTERVAL_IN_MILLIS);
    }

    public void cancelSendLocationLog()
    {
        if (mSendLocationLogRequest != null)
        {
            mSendLocationLogRequest.stopInterval();
            mSendLocationLogRequest.clear();
            mSendLocationLogRequest = null;
        }
    }

    LoaderManager.LoaderCallbacks<List<UserProfile>> userLoader = new LoaderManager.LoaderCallbacks<List<UserProfile>>()
    {
        @Override
        public Loader<List<UserProfile>> onCreateLoader(int id, Bundle args)
        {
            return new ModelLoader<>(mContext, UserProfile.class, false);
        }

        @Override
        public void onLoadFinished(Loader<List<UserProfile>> loader, List<UserProfile> data)
        {
            if (data.size() > 0)
                setValue(data.get(0));
        }

        @Override
        public void onLoaderReset(Loader<List<UserProfile>> loader)
        {

        }
    };
}
