package coid.moonlay.pickupondemand.jet.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationSoundStopReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Intent service = new Intent(context, NotificationSoundPlayService.class);
        context.stopService(service);
    }
}
