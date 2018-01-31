package coid.moonlay.pickupondemand.jet.notification;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import coid.moonlay.pickupondemand.jet.R;

public class NotificationSoundPlayService extends Service
{
    private MediaPlayer mPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Uri uri = Uri.parse("android.resource://coid.moonlay.pickupondemand.jet/" + R.raw.jet_notification);
//        mPlayer = MediaPlayer.create(this, R.raw.jet_notification); // since prepare() is called automatically in this method, you cannot change the audio stream type (see setAudioStreamType(int)), audio session ID (see setAudioSessionId(int)) or audio attributes (see setAudioAttributes(AudioAttributes) of the
        mPlayer = new MediaPlayer(); // call prepare() manually later
        if (Build.VERSION.SDK_INT >= 21)
        {
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            mPlayer.setAudioAttributes(aa);
            Log.d("JET_127", "MPLAYER SET AUDIO ATTRBUTES TYPE ");
        }
        else
        {
            mPlayer.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
            Log.d("JET_127", "MPLAYER SET AUDIO STREAM TYPE ");
        }

        mPlayer.setLooping(true);
        try
        {
            mPlayer.setDataSource(this, uri);
        }
        catch (Exception ex)
        {
            Log.d("JET_127", "MPLAYER DATASOURCE ERROR " + ex.getMessage());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (mPlayer != null)
        {
            try
            {
                mPlayer.prepare();
            }
            catch (Exception ex)
            {
                Log.d("JET_127", "MPLAYER PREPARE ERROR " + ex.getMessage());
            }
            mPlayer.start();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        if (mPlayer != null)
        {
            mPlayer.setLooping(false);
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        super.onDestroy();
    }
}
