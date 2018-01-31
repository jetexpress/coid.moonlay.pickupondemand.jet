package coid.moonlay.pickupondemand.jet.network;

import java.util.List;

import coid.moonlay.pickupondemand.jet.model.UpdateInfo;
import coid.moonlay.pickupondemand.jet.model.Config;
import coid.moonlay.pickupondemand.jet.model.CourierAvailability;
import coid.moonlay.pickupondemand.jet.model.CourierLocation;
import coid.moonlay.pickupondemand.jet.model.Delivery;
import coid.moonlay.pickupondemand.jet.model.DeliveryHistory;
import coid.moonlay.pickupondemand.jet.model.FailedDelivery;
import coid.moonlay.pickupondemand.jet.model.Location;
import coid.moonlay.pickupondemand.jet.model.OperationStatus;
import coid.moonlay.pickupondemand.jet.model.PackagingItem;
import coid.moonlay.pickupondemand.jet.model.Pickup;
import coid.moonlay.pickupondemand.jet.model.PickupItem;
import coid.moonlay.pickupondemand.jet.model.PickupItemSimulation;
import coid.moonlay.pickupondemand.jet.model.Product;
import coid.moonlay.pickupondemand.jet.model.QueryData;
import coid.moonlay.pickupondemand.jet.model.QueryResult;
import coid.moonlay.pickupondemand.jet.model.SuccessDelivery;
import coid.moonlay.pickupondemand.jet.model.Task;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IResourceService
{
    @GET("v2/courier/tasks/ongoing")
    Call<QueryResult<Task>> getOngoingTaskList(@Query("size") Long size, @Query("page") Long page);

    @GET("v2/courier/tasks/history")
    Call<QueryResult<Task>> getHistoryTaskList(@Query("size") Long size, @Query("page") Long page);

    @GET("v2/delivery-orders/waybills/ongoing")
    Call<QueryResult<Delivery>> getOngoingWaybills(@Query("size") Long size, @Query("page") Long page);

    @GET("v2/delivery-orders/waybills/history")
    Call<QueryResult<DeliveryHistory>> getHistoryWaybills(@Query("size") Long size, @Query("page") Long page);

    @GET("v1/configurations")
    Call<Config> getConfig();

    @GET("v1/operationStatuses")
    Call<QueryData<OperationStatus>> getOperationStatusList(@Query("size") Long size);

    @GET("v1/products/mobile-products")
    Call<QueryData<Product>> getProductList(@Query("size") Long size);

    @GET("v2/relations")
    Call<List<String>> getRelations();

    @GET("v1/packagingitems/")
    Call<QueryData<PackagingItem>> getPackagingItemList(@Query("size") Long size);

    @GET("v2/delivery-orders/{drsCode}/waybills/{awbNumber}")
    Call<Delivery> getDeliveryByCode(@Path("drsCode") String drsCode, @Path("awbNumber") String awbNumber);

    @POST("v2/delivery-orders/{drsCode}/proof-of-delivery/{awbNumber}")
    Call<Void> submitSuccessDelivery(@Path("drsCode") String drsCode, @Path("awbNumber") String awbNumber, @Body SuccessDelivery successDelivery);

    @POST("v2/delivery-orders/{drsCode}/proof-of-delivery/{awbNumber}")
    Call<Void> submitFailedDelivery(@Path("drsCode") String drsCode, @Path("awbNumber") String awbNumber, @Body FailedDelivery failedDelivery);

    /** **/

    @GET("v2/pickup-requests/{code}")
    Call<Pickup> getPickupByCode(@Path("code") String code);

    @POST("v2/pickup-requests/{code}/complete")
    Call<Pickup> completePickup(@Path("code") String code);

    @POST("v2/pickup-requests/{code}/assign")
    Call<Pickup> assignPickup(@Path("code") String code);

    @POST("v2/pickup-requests/{code}/start-trip")
    Call<Pickup> startTrip(@Path("code") String code);

    @POST("v2/pickup-requests/{code}/arrive")
    Call<Pickup> hasArrived(@Path("code") String code);

    @POST("v2/pickup-requests/{code}/cancel-trip")
    Call<Pickup> cancelTrip(@Path("code") String code);

    @POST("v2/pickup-requests/{code}/items/{itemCode}/unlock")
    Call<PickupItem> unlockPickupItemByBookingCode(@Path("code") String code, @Path("itemCode") String itemCode);

    @POST("v2/pickup-requests/{pickupCode}/items/unlock-by-unlock-code/{unlockCode}")
    Call<PickupItem> unlockPickupItemByUnlockCode(@Path("pickupCode") String code, @Path("unlockCode") String unlockCode);

    @POST("v2/pickup-requests/items/unlock-by-shipping-label/{shippingLabelId}")
    Call<PickupItem> unlockPickupItemUsingQRCode(@Path("shippingLabelId") String shippingLabelId);

    @POST("v2/pickup-requests/{code}/items/{itemCode}/waybill")
    Call<PickupItem> createWaybill(@Path("code") String code, @Path("itemCode") String itemCode, @Body PickupItem pickupItem);

    @POST("v2/courier/available")
    Call<CourierAvailability> setCourierAvailable();

    @POST("v2/courier/unavailable")
    Call<CourierAvailability> setCourierUnavailable();

    @FormUrlEncoded
    @POST("v2/loglocations")
    Call<CourierLocation> sendCourierLocationLog(@Field("Activity") String activity, @Field("Latitude") Double latitude, @Field("Longitude") Double longitude);

    @GET("v2/location/{keyword}")
    Call<List<Location>> getAutoCompleteLocation(@Path("keyword") String keyword);

    @FormUrlEncoded
    @POST("v2/pickup-requests/{code}/quick-complete")
    Call<Pickup> completeQuickPickup(@Path("code") String code, @Field("actualPickupItemCount") Long actualPickupItemCount);

    @POST("v2/pickup-requests/price-simulation")
    Call<PickupItemSimulation> simulatePrice(@Body PickupItemSimulation pickupItemSimulation);

    @GET("v2/pickup-requests-extra/courier-version/android/{version}")
    Call<UpdateInfo> getUpdateInfo(@Path("version") int version);
}