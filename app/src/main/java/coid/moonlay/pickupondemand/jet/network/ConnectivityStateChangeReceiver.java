package coid.moonlay.pickupondemand.jet.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.activity.MainActivity;

public class ConnectivityStateChangeReceiver extends BroadcastReceiver
{
    private static Integer mConnectivityState;

    @Override
    public void onReceive(final Context context, final Intent intent)
    {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION))
        {
            mConnectivityState = Utility.NetworkConnectivity.getConnectivityStatus(intent);
            toggleInternetWarning(context);
        }
    }

    private void toggleInternetWarning(Context context)
    {
        if (context instanceof  MainActivity)
        {
            MainActivity mainActivity;
            mainActivity = (MainActivity) context;
            if (Utility.NetworkConnectivity.isNetworkAvailable())
                mainActivity.hideNoInternetWarning();
            else
                mainActivity.showNoInternetWarning();
        }
    }

    public static Integer getConnectivityState() {
        return mConnectivityState;
    }
}