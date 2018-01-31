package coid.moonlay.pickupondemand.jet.config;

public class AppConfig
{
    public static final String JET_SHARED_PREFERENCES = "JETPref";
    public static final String FIRST_TIME_OPENED_PARAM_KEY = "FirstTimeKey";
    public static final int IMAGE_PICKER_MIN_WIDTH = 400;
    public static final int IMAGE_PICKER_MIN_HEIGHT = 300;
    public static final long DEFAULT_PICKUP_NOTIFICATION_WAITING_TIME = 30000L;
    public static final long LOCATION_LOG_INTERVAL_IN_MILLIS = 30000L;

    public static class LoaderId
    {
        public static final int USER_PROFILE_LOADER_ID = 0;
        public static final int OUTLET_LOCATION_LOADER_ID = 1;
        public static final int OPERATION_STATUS_LOADER_ID = 2;
        public static final int RELATION_LOADER_ID = 3;
        public static final int PRODUCT_LOADER_ID = 4;
        public static final int PACKAGING_ITEM_LOADER_ID = 5;
    }
}
