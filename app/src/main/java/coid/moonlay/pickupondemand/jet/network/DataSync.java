package coid.moonlay.pickupondemand.jet.network;

import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.request.ConfigRequest;
import coid.moonlay.pickupondemand.jet.request.OperationStatusRequest;
import coid.moonlay.pickupondemand.jet.request.PackagingItemRequest;
import coid.moonlay.pickupondemand.jet.request.ProductRequest;
import coid.moonlay.pickupondemand.jet.request.RelationRequest;
import coid.moonlay.pickupondemand.jet.request.UserProfileRequest;

public class DataSync
{
    public static final String LOG_TAG = "DATA_SYNC";

    public static void start()
    {
        Runnable runnable = new Runnable()
        {
            public void run()
            {
                syncDataFromServer();
            }
        };
        Utility.Executor.execute(runnable);
    }

    private static void syncDataFromServer()
    {
        requestUserProfile();
        requestConfig();
        requestOperationStatus();
        requestRelation();
        requestProduct();
        requestPackagingItemList();
    }

    private static void requestUserProfile()
    {
        UserProfileRequest userProfileRequest = new UserProfileRequest();
        userProfileRequest.execute();
    }

    private static void requestConfig()
    {
        ConfigRequest configRequest = new ConfigRequest();
        configRequest.execute();
    }

    private static void requestOperationStatus()
    {
        OperationStatusRequest operationStatusRequest = new OperationStatusRequest();
        operationStatusRequest.execute();
    }

    private static void requestRelation()
    {
        RelationRequest relationRequest = new RelationRequest();
        relationRequest.execute();
    }

    private static void requestProduct()
    {
        ProductRequest productRequest = new ProductRequest();
        productRequest.execute();
    }

    private static void requestPackagingItemList()
    {
        PackagingItemRequest packagingItemRequest = new PackagingItemRequest();
        packagingItemRequest.execute();
    }
}
