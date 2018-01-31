package coid.moonlay.pickupondemand.jet;

import android.content.Intent;
import android.util.Log;

import com.activeandroid.app.Application;
import com.onesignal.OneSignal;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import coid.moonlay.pickupondemand.jet.notification.NotificationOpenedHandler;
import coid.moonlay.pickupondemand.jet.notification.NotificationReceivedHandler;
import coid.moonlay.pickupondemand.jet.notification.NotificationSoundPlayService;
import coid.moonlay.pickupondemand.jet.notification.NotificationSoundStopReceiver;

public class JetApplication extends Application
{
    private static JetApplication instance;
    private static ThreadPoolExecutor mPool;
    public static NotificationReceivedHandler notificationReceivedHandler;
    public static NotificationOpenedHandler notificationOpenedHandler;

    public JetApplication() {
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        notificationReceivedHandler = new NotificationReceivedHandler();
        notificationOpenedHandler = new NotificationOpenedHandler();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationReceivedHandler(notificationReceivedHandler)
                .setNotificationOpenedHandler(notificationOpenedHandler)
                .init();
        OneSignal.enableSound(true);
        mPool =  new ThreadPoolExecutor(5, Integer.MAX_VALUE, 1, TimeUnit.MINUTES, new SynchronousQueue<Runnable>());
    }

    public static JetApplication getInstance()
    {
        return instance;
    }

    public static ThreadPoolExecutor getThreadPoolExecutor()
    {
        return mPool;
    }

    public void playPickupNotificationSound()
    {
        Intent playSoundIntent = new Intent(this, NotificationSoundPlayService.class);
        Log.d("JET_127", "PLAY SOUND");
        startService(playSoundIntent);
    }

    public void stopPickupNotificationSound()
    {
        Intent stopSoundIntent = new Intent(this, NotificationSoundStopReceiver.class);
        Log.d("JET_127", "STOP SOUND");
        sendBroadcast(stopSoundIntent);
    }
//    public void playPickupNotificationSound()
//    {
//        mPlayer = MediaPlayer.create(this, R.raw.jet_notification);
//        mPlayer.setLooping(true);
//        mPlayer.start();
//    }
//
//    public void stopPickupNotificationSound()
//    {
//        if (mPlayer != null)
//        {
//            mPlayer.setLooping(false);
//            mPlayer.stop();
//            mPlayer.release();
//            mPlayer = null;
//        }
//    }
}