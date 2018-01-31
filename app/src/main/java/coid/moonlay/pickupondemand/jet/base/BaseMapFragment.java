package coid.moonlay.pickupondemand.jet.base;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import coid.moonlay.pickupondemand.jet.model.map.Direction;
import coid.moonlay.pickupondemand.jet.model.map.Step;
import coid.moonlay.pickupondemand.jet.request.GoogleMapDirectionRequest;
import retrofit2.Response;

public class BaseMapFragment
        extends SupportMapFragment
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback, GoogleMap.OnMapClickListener
{
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;
    private Location mOriginLocation;
    private IMapListener mMapListener;
    private Polyline mCurrentPolyline;
    private Context mContext;

    @Override
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        mContext = getContext();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        try
        {
            mOriginLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mOriginLocation == null)
                mOriginLocation = getDefaultLocation();
        }
        catch (SecurityException ex)
        {
            mOriginLocation = getDefaultLocation();
        }

        getMapAsync(this);
    }

    @Override
    public void onConnectionSuspended(int i)
    {
        Log.d("MAP", "SUSPENDED");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {
        Log.d("MAP", "FAILED");
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.setOnMapClickListener(this);
        if (mOriginLocation != null)
        {
            LatLng latLng = new LatLng(mOriginLocation.getLatitude(), mOriginLocation.getLongitude());

            addMarker(latLng, "Current Position", BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            moveCamera(latLng, 15f);
        }

        if (mMapListener != null)
            mMapListener.onMapReady();
    }

    @Override
    public void onMapClick(LatLng latLng)
    {
        if (mMapListener != null)
            mMapListener.onMapClicked(latLng);
    }

    private synchronized void buildGoogleApiClient()
    {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                //TODO:
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                //(just doing it here for now, note that with this code, no explanation is shown)
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }
        else
        {
            if (mGoogleApiClient == null)
            {
                buildGoogleApiClient();
            }
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED)
                    {

                        if (mGoogleApiClient == null)
                        {
                            buildGoogleApiClient();
                        }
                    }
                }
                else
                {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public void getDirection(LatLng destinationLatLng)
    {
        String origin = String.valueOf(mOriginLocation.getLatitude()) + "," + String.valueOf(mOriginLocation.getLongitude());
        String destination = String.valueOf(destinationLatLng.latitude) + "," + String.valueOf(destinationLatLng.longitude);
        Log.d("URL", "https://maps.googleapis.com/maps/api/directions/json?origin= " + origin + "&destination=" + destination);
        GoogleMapDirectionRequest googleMapDirectionRequest = new GoogleMapDirectionRequest(mContext, origin, destination)
        {
            @Override
            protected void onSuccessOnUIThread(Response<Direction> response)
            {
                super.onSuccessOnUIThread(response);
                onGetDirectionSuccess(response.body());
            }
        };
        googleMapDirectionRequest.executeAsync();
    }

    protected void onGetDirectionSuccess(Direction direction)
    {
        drawDirectionLine(direction);
        moveCameraToOriginPosition(18f);
        if (mMapListener != null)
            mMapListener.onGetDirectionSuccess(direction);
    }

    public void drawDirectionLine(Direction direction)
    {
        if (mCurrentPolyline != null)
            mCurrentPolyline.remove();

        PolylineOptions rectLine = new PolylineOptions().width(10).color(
                Color.BLUE);

        List<Step> stepList = direction.getRoutes().get(0).getLegs().get(0).getSteps();
        for (Step step : stepList)
        {
            LatLng latLng = new LatLng(step.getStartLocation().getLat(), step.getStartLocation().getLng());
            rectLine.add(latLng);

            ArrayList<LatLng> arr = step.getPolyline().getDecodedPolyline();
            for (int j = 0; j < arr.size(); j++) {
                latLng = new LatLng(arr.get(j).latitude, arr.get(j).longitude);
                rectLine.add(latLng);
            }

            latLng = new LatLng(step.getEndLocation().getLat(), step.getEndLocation().getLng());
            rectLine.add(latLng);
        }

        mCurrentPolyline = mGoogleMap.addPolyline(rectLine);
    }

    public void moveCameraToOriginPosition(Float zoomDepth)
    {
        LatLng latLng = new LatLng(mOriginLocation.getLatitude(), mOriginLocation.getLongitude());
        moveCamera(latLng, zoomDepth);
    }

    public void moveCamera(LatLng latLng, Float zoomDepth)
    {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomDepth));
    }

    private Location getDefaultLocation()
    {
        LatLng latLng = new LatLng(-6.204394, 106.780494);
        return getLocation(latLng);
    }

    private Location getLocation(LatLng latLng)
    {
        Location l = new Location("");
        l.setLatitude(latLng.latitude);
        l.setLongitude(latLng.longitude);
        return  l;
    }

    public void addMarker(LatLng latLng, String title, BitmapDescriptor markerIcon)
    {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(title);
        markerOptions.icon(markerIcon);

        mGoogleMap.addMarker(markerOptions);
    }

    public interface IMapListener
    {
        void onMapReady();
        void onMapClicked(LatLng latLng);
        void onGetDirectionSuccess(Direction direction);
    }

    public void setMapListener(IMapListener iMapListener)
    {
        mMapListener = iMapListener;
    }
}