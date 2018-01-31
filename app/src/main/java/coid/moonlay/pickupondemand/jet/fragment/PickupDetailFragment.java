package coid.moonlay.pickupondemand.jet.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.custom.ConfirmationDialog;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.model.Task;
import coid.moonlay.pickupondemand.jet.request.PickupCancelRequest;
import coid.moonlay.pickupondemand.jet.request.PickupHasArrivedRequest;
import coid.moonlay.pickupondemand.jet.request.PickupRequest;
import coid.moonlay.pickupondemand.jet.request.PickupStartTripRequest;
import retrofit2.Response;

public class PickupDetailFragment extends TaskDetailFragment
{
    private final static String TASK_ARGS_PARAM = "TaskParam";
    private Task mTask;
    private Pickup mPickup;
    private PickupRequest mPickupRequest;
    private PickupStartTripRequest mPickupStartTripRequest;
    private PickupHasArrivedRequest mPickupHasArrivedRequest;
    private PickupCancelRequest mPickupCancelRequest;

    public PickupDetailFragment()
    {

    }

    public static PickupDetailFragment newInstance(Task task)
    {
        PickupDetailFragment fragment = new PickupDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(TASK_ARGS_PARAM, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mTask = getArguments().getParcelable(TASK_ARGS_PARAM);
            if (mTask == null)
                return;

            mPickupRequest =  new PickupRequest(mContext, mTask.getCode())
            {
                @Override
                protected void onSuccessOnUIThread(Response<Pickup> response)
                {
                    showContent();
                    mPickup = response.body();
                    setValue();
                    setUIState();
                }

                @Override
                protected void showLoadingDialog()
                {
                    showProgressBar();
                }

                @Override
                protected void hideLoadingDialog()
                {
                    showRetry(R.string.request_timed_out);
                }
            };
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        showButtonGroup();
        setLabel(Utility.Message.get(R.string.pickup_detail_label));
        setValue();
        setUIState();
        if (mPickupRequest != null)
            mPickupRequest.executeAsync();
    }

    @Override
    public void onStop()
    {
        if (mPickupRequest != null)
        {
            mPickupRequest.cancel();
            mPickupRequest = null;
        }
        super.onStop();
    }

    @Override
    public void onDestroy()
    {
        if (mPickupRequest != null)
        {
            mPickupRequest.clear();
            mPickupRequest = null;
        }
        if (mPickupStartTripRequest != null)
        {
            mPickupStartTripRequest.clear();
            mPickupStartTripRequest = null;
        }
        if (mPickupHasArrivedRequest != null)
        {
            mPickupHasArrivedRequest.clear();
            mPickupHasArrivedRequest = null;
        }
        if (mPickupCancelRequest != null)
        {
            mPickupCancelRequest.clear();
            mPickupCancelRequest = null;
        }

        super.onDestroy();
    }

    @Override
    protected void onRetry()
    {
        super.onRetry();
        if (mPickupRequest != null)
            mPickupRequest.executeAsync();
    }

    @NonNull
    @Override
    protected Task getTask()
    {
        return mTask;
    }

    @Override
    protected void onCancelClicked()
    {
        if (mPickup.isTripStarted())
            cancelTrip();
        else
            rejectPickup();
    }

    @Override
    protected void onNavigationClicked()
    {
//        String uri = "https://www.google.com/maps/dir/?api=1&destination=" + mPickup.getLatLngString() + "&dir_action=navigate";
//        String uri = "geo:" + mPickup.getLatLngString();
//        String uri = "waze://?ll= " + mPickup.getLatLngString() + "&navigate=yes";
        String uri = "google.navigation:q=" + mPickup.getLatLngString();
        Intent mapIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivity(mapIntent);
        else
        {
            Intent marketIntent = new Intent(Intent.ACTION_VIEW);
            marketIntent.setData(Uri.parse("market://details?id=com.google.android.apps.maps"));
            startActivity(marketIntent);
        }
//        getNavigator().showFragment(NavigationFragment.newInstance(mPickup.getLatitude(), mPickup.getLongitude(), mPickup.getName() + " - " + mPickup.getAddress()));
    }

    @Override
    protected void onCreatingWaybillClicked()
    {
        getNavigator().showFragment(CreateWaybillFragment.newInstance(mPickup));
    }

    @Override
    protected void onHasArrivedClicked()
    {
        mPickupHasArrivedRequest = new PickupHasArrivedRequest(mContext, mTask.getCode())
        {
            @Override
            protected void onSuccessOnUIThread(Response<Pickup> response)
            {
                super.onSuccessOnUIThread(response);
                mPickup = response.body();
                updateTask(mPickup);
                setUIState();
            }
        };
        mPickupHasArrivedRequest.executeAsync();
    }

    @Override
    protected void onStartTripClicked()
    {
        mPickupStartTripRequest = new PickupStartTripRequest(mContext, mTask.getCode())
        {
            @Override
            protected void onSuccessOnUIThread(Response<Pickup> response)
            {
                super.onSuccessOnUIThread(response);
                mPickup = response.body();
                updateTask(mPickup);
                setUIState();
            }
        };
        mPickupStartTripRequest.executeAsync();
    }

    private void setValue()
    {
        if (mPickup == null)
            return;

        updateTask(mPickup);
        showPIC(mPickup.getPic());
        setAddressDetail(mPickup.getAddressDetail());
        setPhone(mPickup.getPhone());
        showWeight(mPickup.getTotalWeight());
        setTotal(mPickup.getTotalFeeString());
    }

    private void setUIState()
    {
        if(mPickup == null)
            return;

        if (mPickup.isTripStarted())
        {
            showHasArrivedButton();
            showButtonGroup();
            updateCancelButtonLabel(Utility.Message.get(R.string.task_cancel));
        }
        else if (mPickup.hasArrived())
        {
            if(mPickup.getQuickPickupItemCount() > 0)
            {
                setTextCompleteLabel(Utility.Message.get(R.string.pickup_take_item));
            }else{
                setTextCompleteLabel(Utility.Message.get(R.string.pickup_create_waybill));
            }
            showCreateWaybillButton();
            showSingleContactButton();
        }
        else
        {
            showStartTripButton();
            showButtonGroup();
            updateCancelButtonLabel(Utility.Message.get(R.string.pickup_reject));
        }

        if (mPickup.hasLocation())
            showNavigationButton();
        else
            hideNavigationButton();
    }

    private void cancelTrip()
    {
        String title = Utility.Message.get(R.string.pickup_cancel_trip_title);
        String message = Utility.Message.get(R.string.pickup_cancel_trip_confirmation);
        ConfirmationDialog cancelDialog = new ConfirmationDialog(mContext, title, message)
        {
            @Override
            public void onOKClicked()
            {
                mPickupCancelRequest = new PickupCancelRequest(mContext, mTask.getCode())
                {
                    @Override
                    protected void onSuccessOnUIThread(Response<Pickup> response)
                    {
                        mPickup = response.body();
                        updateTask(mPickup);
                        setUIState();
                        super.onSuccessOnUIThread(response);
                    }
                };
                mPickupCancelRequest.executeAsync();
            }
        };
        cancelDialog.show();
    }

    private void rejectPickup()
    {
        String title = Utility.Message.get(R.string.pickup_reject_pickup_title);
        String message = Utility.Message.get(R.string.pickup_reject_pickup_confirmation);
        ConfirmationDialog rejectDialog = new ConfirmationDialog(mContext, title, message)
        {
            @Override
            public void onOKClicked()
            {
                mPickupCancelRequest = new PickupCancelRequest(mContext, mTask.getCode())
                {
                    @Override
                    protected void onSuccessOnUIThread(Response<Pickup> response)
                    {
                        getNavigator().popToDefaultFragment();
                        getNavigator().showFragment(new TaskFragment());
                        super.onSuccessOnUIThread(response);
                    }
                };
                mPickupCancelRequest.executeAsync();
            }
        };
        rejectDialog.show();
    }

    private void updateTask(Pickup pickup)
    {
        mTask.setStatus(pickup.getStatus());
        mTask.setPhone(pickup.getPhone());
        if (getTargetFragment() != null && getTargetFragment() instanceof IPickupDetailListener)
        {
            IPickupDetailListener pickupDetailListener = (IPickupDetailListener) getTargetFragment();
            pickupDetailListener.onStatusChanged(mTask);
        }
    }

    public interface IPickupDetailListener
    {
        void onStatusChanged(Task task);
    }
}
