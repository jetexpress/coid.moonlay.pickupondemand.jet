package coid.moonlay.pickupondemand.jet.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.base.BaseMapFragment;
import coid.moonlay.pickupondemand.jet.model.map.Direction;
import coid.moonlay.pickupondemand.jet.model.map.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends BaseMainFragment implements BaseMapFragment.IMapListener
{
    private static final String LATITUDE_ARGS_PARAM = "LatitudeParam";
    private static final String LONGITUDE_ARGS_PARAM = "LongitudeParam";
    private static final String LOCATION_DESCRIPTION_PARAM = "LocationDescriptionParam";
    private double mLat;
    private double mLng;
    private String mLocationDescription;
    private BaseMapFragment mMapFragment;

    public NavigationFragment()
    {
        // Required empty public constructor
    }

    public static NavigationFragment newInstance(double lat, double lng, String locationDescription)
    {
        NavigationFragment fragment = new NavigationFragment();
        Bundle args = new Bundle();
        args.putDouble(LATITUDE_ARGS_PARAM, lat);
        args.putDouble(LONGITUDE_ARGS_PARAM, lng);
        args.putString(LOCATION_DESCRIPTION_PARAM, locationDescription);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mLat = getArguments().getDouble(LATITUDE_ARGS_PARAM);
            mLng = getArguments().getDouble(LONGITUDE_ARGS_PARAM);
            mLocationDescription = getArguments().getString(LOCATION_DESCRIPTION_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager childFM = getChildFragmentManager();
        mMapFragment = (BaseMapFragment) childFM.findFragmentByTag(BaseMapFragment.class.getSimpleName());
        if (mMapFragment == null) {
            mMapFragment = new BaseMapFragment();
            FragmentTransaction ft = childFM.beginTransaction();
            ft.add(R.id.fl_map_container, mMapFragment, BaseMapFragment.class.getSimpleName());
            ft.commit();
            childFM.executePendingTransactions();
        }
        mMapFragment.checkLocationPermission();
        mMapFragment.setMapListener(this);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setTitle(Utility.Message.get(R.string.task_navigation));
        setNotificationMenuEnabled(false);
        setDisplayBackEnabled(true);
    }

    @Override
    public void onMapReady()
    {
        mMapFragment.getDirection(new LatLng(mLat, mLng));
    }

    @Override
    public void onMapClicked(LatLng latLng)
    {

    }

    @Override
    public void onGetDirectionSuccess(Direction direction)
    {
        List<Step> stepList = direction.getRoutes().get(0).getLegs().get(0).getSteps();
        Step lastStep = stepList.get(stepList.size() - 1);
        LatLng lastLatLng = new LatLng(lastStep.getEndLocation().getLat(), lastStep.getEndLocation().getLng());
        mMapFragment.addMarker(lastLatLng, mLocationDescription, BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    }
}
