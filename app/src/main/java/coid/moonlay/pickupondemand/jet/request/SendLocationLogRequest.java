package coid.moonlay.pickupondemand.jet.request;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.activity.MainActivity;
import coid.moonlay.pickupondemand.jet.base.BaseNetworkRequest;
import coid.moonlay.pickupondemand.jet.model.CourierLocation;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.network.RetrofitProvider;
import retrofit2.Call;

public class SendLocationLogRequest extends BaseNetworkRequest<CourierLocation>
{
    private Handler mHandler;
    private Runnable mIntervalRequestRunnable;
    private Long mIntervalMillis;
    private Double mLatitude;
    private Double mLongitude;

    public SendLocationLogRequest(Context context)
    {
        super(context);
    }

    @Override
    protected Call<CourierLocation> getCall()
    {
        return RetrofitProvider.getAuthorizedResourcesService().sendCourierLocationLog(Pickup.STATUS_TRIP_STARTED, mLatitude, mLongitude);
    }

    @Override
    protected void showLoadingDialog()
    {

    }

    @Override
    protected void hideLoadingDialog()
    {

    }

    public void executeInterval(Long intervalMillis)
    {
        Log.d("JET_127", "LOCATION LOG, START");

        if (mHandler == null)
            mHandler = new Handler(Looper.getMainLooper());

        if (mIntervalRequestRunnable == null)
            mIntervalRequestRunnable = getIntervalRequestRunnable();

        mIntervalMillis = intervalMillis;
        mHandler.post(mIntervalRequestRunnable);
    }

    public void stopInterval()
    {
        Log.d("JET_127", "LOCATION LOG, STOP");
        cancel();
        mLatitude = null;
        mLongitude = null;
        if (mHandler != null)
        {
            if (mIntervalRequestRunnable != null)
                mHandler.removeCallbacks(mIntervalRequestRunnable);
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        mIntervalRequestRunnable = null;
    }

    private Runnable getIntervalRequestRunnable()
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                Log.d("JET_127", "LOCATION LOG, GET LOCATION");
                if (getContext() instanceof MainActivity)
                {
                    Location location = Utility.Location.getCurrentLocationByFused(((MainActivity)getContext()).getGoogleApiClient());
                    if (location != null)
                    {
                        mLatitude = location.getLatitude();
                        mLongitude = location.getLongitude();
                    } else
                    {
                        mLatitude = null;
                        mLongitude = null;
                    }

                    if (mLatitude != null && mLongitude != null)
                    {
                        Log.d("JET_127", "LOCATION LOG, LAT : " + String.valueOf(mLatitude) + ", LNG : " + String.valueOf(mLongitude));
                        executeAsync();
                    } else
                        Log.d("JET_127", "LOCATION LOG, LAT LNG NULL");
                }

                Log.d("JET_127", "LOCATION LOG, NEXT LOCATION LOG IN " + String.valueOf(mIntervalMillis) + " MILLISECONDS");
                mHandler.postDelayed(this, mIntervalMillis);
            }
        };
    }
}