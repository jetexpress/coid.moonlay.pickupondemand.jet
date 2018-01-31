package coid.moonlay.pickupondemand.jet.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.adapter.OutletLocationAdapter;
import coid.moonlay.pickupondemand.jet.base.BaseMainFragment;
import coid.moonlay.pickupondemand.jet.base.BaseMapFragment;
import coid.moonlay.pickupondemand.jet.model.OutletLocation;
import coid.moonlay.pickupondemand.jet.model.map.Direction;
import coid.moonlay.pickupondemand.jet.model.map.Step;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutletLocationFragment extends BaseMainFragment implements BaseMapFragment.IMapListener
{
    private static final int OUTLET_LOCATION_SEARCH_REQUEST_CODE = 300;

    private BaseMapFragment mMapFragment;
    private OutletLocationAdapter mOutletLocationAdapter;
    private List<OutletLocation> mOutletLocationList;
    private Boolean mIsAnimating;
    private Boolean mIsExpanded;

    private RelativeLayout rl_search_container;
    private LinearLayout ll_bottom_container;
    private ImageView img_expand_collapse;
    private ListView list_view_outlet_location;
    private TextView tv_area;
    private FrameLayout fl_map_container;

    private OutletLocation mSelectedOutletLocation;

    public OutletLocationFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mIsAnimating = false;
        mIsExpanded = true;
        OutletLocation.getMockOutletLocationList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outlet_location, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        setView();
        setValue();
        setEvent();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setTitle(Utility.Message.get(R.string.outlet_location));
        setNotificationMenuEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case OUTLET_LOCATION_SEARCH_REQUEST_CODE :
                    Long outletLocationId = data.getLongExtra(OutletLocationSearchDialogFragment.SELECTED_OUTLET_LOCATION_ID_ARGS_PARAM, 0);
                    mSelectedOutletLocation = OutletLocation.load(OutletLocation.class, outletLocationId);
                    mMapFragment.getDirection(mSelectedOutletLocation.getLatLng());
                    List<OutletLocation> outletLocationList = new ArrayList<>();
                    outletLocationList.add(mSelectedOutletLocation);
                    mOutletLocationAdapter.updateFilteredList(outletLocationList, null);
                    break;
                default: break;
            }
        }
    }

    @Override
    public void onMapReady()
    {
        if (mOutletLocationList != null)
        {
            for (OutletLocation outletLocation : mOutletLocationList)
            {
                mMapFragment.addMarker(outletLocation.getLatLng(), outletLocation.getAddress(), BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }
        }

        ll_bottom_container.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapClicked(LatLng latLng)
    {
        if (mIsExpanded)
            hideOutletLocationListView();
    }

    @Override
    public void onGetDirectionSuccess(Direction direction)
    {
        if (mIsExpanded)
            hideOutletLocationListView();

        List<Step> stepList = direction.getRoutes().get(0).getLegs().get(0).getSteps();
        Step lastStep = stepList.get(stepList.size() - 1);
        LatLng lastLatLng = new LatLng(lastStep.getEndLocation().getLat(), lastStep.getEndLocation().getLng());
        mMapFragment.addMarker(lastLatLng, mSelectedOutletLocation.getName() + " - " + mSelectedOutletLocation.getAddress(), BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    }

    private void setView()
    {
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

        rl_search_container = (RelativeLayout) findViewById(R.id.rl_search_container);
        fl_map_container = (FrameLayout) findViewById(R.id.fl_map_container);
        ll_bottom_container = (LinearLayout) findViewById(R.id.ll_bottom_container);
        img_expand_collapse = (ImageView) findViewById(R.id.img_expand_collapse);
        list_view_outlet_location = (ListView) findViewById(R.id.list_view_outlet_location);
        tv_area = (TextView) findViewById(R.id.tv_area);
    }

    private void setValue()
    {
        tv_area.setText("");
        ll_bottom_container.setVisibility(View.GONE);

        mOutletLocationList = OutletLocation.getMockNearestThreeOutletLocation();
        mOutletLocationAdapter = new OutletLocationAdapter(mContext, mOutletLocationList);
        list_view_outlet_location.setAdapter(mOutletLocationAdapter);
    }

    private void setEvent()
    {
        mMapFragment.setMapListener(this);

        rl_search_container.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                OutletLocationSearchDialogFragment dialog = new OutletLocationSearchDialogFragment();
                dialog.setTargetFragment(mFragment, OUTLET_LOCATION_SEARCH_REQUEST_CODE);
                dialog.show(getFragmentManager(), OutletLocationSearchDialogFragment.class.getSimpleName());
            }
        });
        img_expand_collapse.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mIsExpanded)
                    hideOutletLocationListView();
                else
                    showOutletLocationListView();
            }
        });
        list_view_outlet_location.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                mSelectedOutletLocation = mOutletLocationAdapter.getItem(position);
                if (mSelectedOutletLocation != null)
                    mMapFragment.getDirection(mSelectedOutletLocation.getLatLng());
                else
                    showToast(Utility.Message.get(R.string.failed_to_get_direction));
            }
        });
    }

    public void showOutletLocationListView()
    {
        if (!mIsAnimating)
        {
            mIsAnimating = true;
            ll_bottom_container.animate()
                    .translationY(0)
                    .setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            super.onAnimationEnd(animation);
                            mIsAnimating = false;
                            mIsExpanded = true;
                            img_expand_collapse.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_expand));
                        }
                    });
        }
    }

    public void hideOutletLocationListView()
    {
        if (!mIsAnimating)
        {
            mIsAnimating = true;
            ll_bottom_container.animate()
                    .translationY(ll_bottom_container.getHeight() - img_expand_collapse.getHeight())
                    .setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            super.onAnimationEnd(animation);
                            mIsAnimating = false;
                            mIsExpanded = false;
                            img_expand_collapse.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_collapse));
                        }
                    });
        }
    }
}