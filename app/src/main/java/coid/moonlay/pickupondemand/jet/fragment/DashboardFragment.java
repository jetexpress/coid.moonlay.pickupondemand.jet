package coid.moonlay.pickupondemand.jet.fragment;


import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.activity.MainActivity;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.config.AppConfig;
import coid.moonlay.pickupondemand.jet.custom.ConfirmationDialog;
import coid.moonlay.pickupondemand.jet.model.CourierAvailability;
import coid.moonlay.pickupondemand.jet.model.CourierProfile;
import coid.moonlay.pickupondemand.jet.model.UserProfile;
import coid.moonlay.pickupondemand.jet.model.db.ModelLoader;
import coid.moonlay.pickupondemand.jet.request.CourierAvailableRequest;
import coid.moonlay.pickupondemand.jet.request.CourierProfileRequest;
import coid.moonlay.pickupondemand.jet.request.CourierUnavailableRequest;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends BaseMainFragment
{
    private CircleImageView circle_img_user_photo;
    private TextView tv_username;
    private Button btn_courier_availability;
    private ProgressBar progress_bar_task_status, progress_bar_courier_availability;
    private LinearLayout ll_task_status_container;

    private UserProfile mUserProfile;
    private CourierProfile mCourierProfile;
    private CourierProfileRequest mCourierProfileRequest;
    private CourierAvailableRequest mCourierAvailableRequest;
    private CourierUnavailableRequest mCourierUnavailableRequest;

    public DashboardFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mCourierProfile = new CourierProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setEvent();
        showCourierAvailabilityProgressBar();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setTitle(Utility.Message.get(R.string.dashboard));
        setNotificationMenuEnabled(true);
        if (getLoaderManager().getLoader(AppConfig.LoaderId.USER_PROFILE_LOADER_ID) != null)
            getLoaderManager().restartLoader(AppConfig.LoaderId.USER_PROFILE_LOADER_ID, null, userLoader);
        else
            getLoaderManager().initLoader(AppConfig.LoaderId.USER_PROFILE_LOADER_ID, null, userLoader);
    }

    @Override
    public void onDestroy()
    {
        if (mCourierProfileRequest != null)
        {
            mCourierProfileRequest.clear();
            mCourierProfileRequest = null;
        }
        if (mCourierAvailableRequest != null)
        {
            mCourierAvailableRequest.clear();
            mCourierAvailableRequest = null;
        }
        if (mCourierUnavailableRequest != null)
        {
            mCourierUnavailableRequest.clear();
            mCourierUnavailableRequest = null;
        }
        super.onDestroy();
    }

    private void setView()
    {
        circle_img_user_photo = (CircleImageView) findViewById(R.id.circle_img_user_photo);
        tv_username = (TextView) findViewById(R.id.tv_username);
        btn_courier_availability = (Button) findViewById(R.id.btn_courier_availability);
        progress_bar_task_status = (ProgressBar) findViewById(R.id.progress_bar_task_status);
        progress_bar_courier_availability = (ProgressBar) findViewById(R.id.progress_bar_courier_availability);
        ll_task_status_container = (LinearLayout) findViewById(R.id.ll_task_status_container);
    }

    private void setValue()
    {
        tv_username.setText(mUserProfile.getUsername());
    }

    private void setEvent()
    {
        btn_courier_availability.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mCourierProfile.isAvailable())
                {
                    String title = Utility.Message.get(R.string.dashboard_go_offline);
                    String message = Utility.Message.get(R.string.dashboard_offline_confirmation);
                    ConfirmationDialog offlineDialog = new ConfirmationDialog(mContext, title, message)
                    {
                        @Override
                        public void onOKClicked()
                        {
                            requestGoOffline();
                        }
                    };
                    offlineDialog.show();
                }
                else
                {
                    String title = Utility.Message.get(R.string.dashboard_go_online);
                    String message = Utility.Message.get(R.string.dashboard_online_confirmation);
                    ConfirmationDialog onlineDialog = new ConfirmationDialog(mContext, title, message)
                    {
                        @Override
                        public void onOKClicked()
                        {
                            requestGoOnline();
                        }
                    };
                    onlineDialog.show();
                }
            }
        });
    }

    private void setCourierAvailabilityState(boolean isCourierAvailable)
    {
        Drawable backgroundDrawable;
        String text;
        if (isCourierAvailable)
        {
            backgroundDrawable = ContextCompat.getDrawable(mContext, R.drawable.custom_bg_rounded_square_red_fill);
            text = Utility.Message.get(R.string.dashboard_go_offline);
            showOnlineNotification();
        }
        else
        {
            backgroundDrawable = ContextCompat.getDrawable(mContext, R.drawable.custom_bg_rounded_square_black_fill);
            text = Utility.Message.get(R.string.dashboard_go_online);
            cancelOnlineNotification();
        }
        btn_courier_availability.setBackground(backgroundDrawable);
        btn_courier_availability.setText(text);
        showCourierAvailabilityButton();
    }

    private void requestCourierProfile()
    {
        mCourierProfileRequest = new CourierProfileRequest(mContext)
        {
            @Override
            protected void onStartOnUIThread()
            {
                showCourierAvailabilityProgressBar();
            }

            @Override
            protected void onSuccessOnUIThread(Response<CourierProfile> response)
            {
                super.onSuccessOnUIThread(response);
                mCourierProfile = response.body();
                setCourierAvailabilityState(mCourierProfile.isAvailable());
            }
        };
        mCourierProfileRequest.executeAsync();
    }

    private void requestGoOnline()
    {
        mCourierAvailableRequest = new CourierAvailableRequest(mContext)
        {
            @Override
            protected void onStartOnUIThread()
            {
                showCourierAvailabilityProgressBar();
            }

            @Override
            protected void onSuccessOnUIThread(Response<CourierAvailability> response)
            {
                super.onSuccessOnUIThread(response);
                mCourierProfile.setAvailable(response.body().getAvailable());
                setCourierAvailabilityState(mCourierProfile.isAvailable());
                Utility.OneSignal.sendTags(mCourierProfile);
            }
        };
        mCourierAvailableRequest.executeAsync();
    }

    private void requestGoOffline()
    {
        mCourierUnavailableRequest = new CourierUnavailableRequest(mContext)
        {
            @Override
            protected void onStartOnUIThread()
            {
                showCourierAvailabilityProgressBar();
            }

            @Override
            protected void onSuccessOnUIThread(Response<CourierAvailability> response)
            {
                super.onSuccessOnUIThread(response);
                mCourierProfile.setAvailable(response.body().getAvailable());
                setCourierAvailabilityState(mCourierProfile.isAvailable());
                Utility.OneSignal.sendTags(mCourierProfile);
            }
        };
        mCourierUnavailableRequest.executeAsync();
    }

    public void showTaskStatus()
    {
        ll_task_status_container.setVisibility(View.VISIBLE);
        progress_bar_task_status.setVisibility(View.GONE);
    }

    public void showTaskProgress()
    {
        ll_task_status_container.setVisibility(View.INVISIBLE);
        progress_bar_task_status.setVisibility(View.VISIBLE);
    }

    private void showCourierAvailabilityProgressBar()
    {
        progress_bar_courier_availability.setVisibility(View.VISIBLE);
        btn_courier_availability.setVisibility(View.GONE);
    }

    private void showCourierAvailabilityButton()
    {
        progress_bar_courier_availability.setVisibility(View.GONE);
        btn_courier_availability.setVisibility(View.VISIBLE);
    }

    public void hideTaskStatus()
    {
        ll_task_status_container.setVisibility(View.INVISIBLE);
        progress_bar_task_status.setVisibility(View.GONE);
    }

    private void showOnlineNotification()
    {
        Notification.Builder builder = new Notification.Builder(mContext)
                .setContentTitle(Utility.Message.get(R.string.app_name))
                .setContentText(Utility.Message.get(R.string.dashboard_online_message))
                .setSmallIcon(R.drawable.ic_online)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        Notification n = builder.build();
        n.flags |= Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        NotificationManager notificationManager;
        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MainActivity.ONLINE_STATUS_INDICATOR_NOTIFICATION_ID, n);
    }

    private void cancelOnlineNotification()
    {
        NotificationManager notificationManager;
        notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(MainActivity.ONLINE_STATUS_INDICATOR_NOTIFICATION_ID);
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
            {
                mUserProfile = data.get(0);
                setValue();
                requestCourierProfile();
            }
        }

        @Override
        public void onLoaderReset(Loader<List<UserProfile>> loader)
        {

        }
    };
}