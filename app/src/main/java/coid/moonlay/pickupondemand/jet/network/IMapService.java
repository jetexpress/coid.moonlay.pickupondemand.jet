package coid.moonlay.pickupondemand.jet.network;

import com.google.android.gms.maps.model.LatLng;

import coid.moonlay.pickupondemand.jet.model.map.Direction;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMapService
{
    @GET("directions/json")
    Call<Direction> getDirectionByLatLng(@Query("origin")LatLng origin, @Query("destination")LatLng destination);

    @GET("directions/json?origin={originLatitude},{originLongitude}&destination={destinationLatitude},{destinationLongitude}")
    Call<Direction> getDirectionByPath(@Path("originLatitude") Double originLatitude,
                                       @Path("originLongitude") Double originLongitude,
                                       @Path("destinationLatitude") Double destinationLatitude,
                                       @Path("destinationLongitude") Double destinationLongitude);

    @GET("directions/json")
    Call<Direction> getDirection(@Query("origin")String origin, @Query("destination")String destination);
}
