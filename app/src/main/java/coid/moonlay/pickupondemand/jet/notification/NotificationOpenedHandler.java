package coid.moonlay.pickupondemand.jet.notification;

import android.content.Intent;

import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import coid.moonlay.pickupondemand.jet.JetApplication;
import coid.moonlay.pickupondemand.jet.activity.MainActivity;
import coid.moonlay.pickupondemand.jet.model.NotificationPayload;
import coid.moonlay.pickupondemand.jet.model.Task;

public class NotificationOpenedHandler implements OneSignal.NotificationOpenedHandler
{
    @Override
    public void notificationOpened(OSNotificationOpenResult result)
    {
        JetApplication.getInstance().stopPickupNotificationSound();
        JSONObject data = result.notification.payload.additionalData;
        if (data == null)
            return;

        NotificationPayload notificationPayload = NotificationPayload.createFromDataString(data.toString());
        Task task = Task.createTaskFromNotification(notificationPayload);

        if (!task.isRequested() && !task.isCancelled() && !task.isAssigned())
            return;

        Intent intent = new Intent(JetApplication.getInstance(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(MainActivity.EXTRA_PARAM, task);
        JetApplication.getInstance().startActivity(intent);
    }
}