package coid.moonlay.pickupondemand.jet.notification;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import coid.moonlay.pickupondemand.jet.JetApplication;
import coid.moonlay.pickupondemand.jet.model.NotificationPayload;
import coid.moonlay.pickupondemand.jet.model.Task;

public class NotificationReceivedHandler implements OneSignal.NotificationReceivedHandler
{
    private INotificationReceivedListener mNotificationReceivedListener;

    @Override
    public void notificationReceived(final OSNotification notification)
    {
        JSONObject data = notification.payload.additionalData;

        if (data == null)
        {
//            OneSignal.cancelNotification(notification.androidNotificationId);
            Log.d("JET_127", "DATA NULL");
            return;
        }

        Log.d("JET_127", "DATA : " + data.toString());

        NotificationPayload notificationPayload = NotificationPayload.createFromDataString(data.toString());
        if (notificationPayload == null || !notificationPayload.isCourier())
        {
//            OneSignal.cancelNotification(notification.androidNotificationId);
            Log.d("JET_127", "NOTIFICATION NOT FOR COURIER");
            return;
        }

        Task task = Task.createTaskFromNotification(notificationPayload);

        Log.d("JET_127", "NOTIFICATION TIME IN MILLIS : " + task.getNotificationTimeInMillis());
        Log.d("JET_127", "NOTIFICATION TIME IN MILLIS COUNTDOWN : " + task.getCountDownStartTimeInMillis());

        if (task.isNotificationExpired())
        {
            Log.d("JET_127", "NOTIFICATION EXPIRED");
            OneSignal.cancelNotification(notification.androidNotificationId);
            return;
        }

        if (!task.isRequested() && !task.isCancelled())
        {
            if (!task.isAssigned())
                OneSignal.cancelNotification(notification.androidNotificationId);

            return;
        }

        Handler mainHandler = new Handler(Looper.getMainLooper());
        Log.d("JET_127", "CREATE HANDLER");
        mainHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Log.d("JET_127", "DELAY CANCEL");
                cancelNotification(notification);
            }
        }, task.getCountDownStartTimeInMillis());

        JetApplication.getInstance().playPickupNotificationSound();


        if (notification.isAppInFocus)
        {
            Log.d("JET_127", "APP IN FOCUS");
            cancelNotification(notification);

            if (mNotificationReceivedListener != null)
                mNotificationReceivedListener.onNotificationReceived(task);
        }
    }

    public void cancelNotification(final OSNotification notification)
    {
        OneSignal.cancelNotification(notification.androidNotificationId);
        JetApplication.getInstance().stopPickupNotificationSound();
    }

    public void setOnReceivedListener(INotificationReceivedListener notificationReceivedListener)
    {
        mNotificationReceivedListener = notificationReceivedListener;
    }

    public void clearListener()
    {
        mNotificationReceivedListener = null;
    }

    public interface INotificationReceivedListener
    {
        void onNotificationReceived(Task task);
    }
}