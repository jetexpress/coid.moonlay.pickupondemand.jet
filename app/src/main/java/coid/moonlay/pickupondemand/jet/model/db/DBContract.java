package coid.moonlay.pickupondemand.jet.model.db;

public class DBContract
{
    public static class ConfigEntry
    {
        public static final String TABLE_NAME = "Config";
        public static final String COLUMN_CONFIG_ID = "ConfigId";
        public static final String COLUMN_WAITING_TIME = "WaitingTime";
    }

    public static class LoginEntry
    {
        public static final String TABLE_NAME = "Login";
        public static final String COLUMN_ACCESS_TOKEN = "AccessToken";
        public static final String COLUMN_TOKEN_TYPE = "TokenType";
        public static final String COLUMN_EXPIRES_IN = "ExpiresIn";
        public static final String COLUMN_REFRESH_TOKEN = "RefreshToken";
    }

    public static class UserProfileEntry
    {
        public static final String TABLE_NAME = "UserProfile";
        public static final String COLUMN_USERNAME = "Username";
        public static final String COLUMN_FULL_NAME = "FullName";
        public static final String COLUMN_DATE_OF_BIRTH = "DateOfBirth";
        public static final String COLUMN_DATE_OF_BIRTH_IN_MILLIS = "DateOfBirthInMillis";
        public static final String COLUMN_PHONE_NUMBER = "PhoneNumber";
        public static final String COLUMN_ADDRESS = "Address";
        public static final String COLUMN_EMAIL = "Email";
        public static final String COLUMN_COURIER_AVAILABILITY = "CourierAvailability";
    }

    public static class OutletLocationEntry
    {
        public static final String TABLE_NAME = "OutletLocation";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_ADDRESS = "Address";
        public static final String COLUMN_DISTANCE = "Distance";
        public static final String COLUMN_LATITUDE = "Latitude";
        public static final String COLUMN_LONGITUDE = "Longitude";
    }

    public static class OperationStatusEntry
    {
        public static final String TABLE_NAME = "OperationStatus";
        public static final String COLUMN_CODE = "Code";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_IS_FAIL = "IsFail";
    }

    public static class RelationEntry
    {
        public static final String TABLE_NAME = "Relation";
        public static final String COLUMN_NAME = "Name";
    }

    public static class ProductEntry
    {
        public static final String TABLE_NAME = "Product";
        public static final String COLUMN_CODE = "Code";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_LABEL = "Label";
    }

    public static class PackagingItemEntry
    {
        public static final String TABLE_NAME = "PackagingItem";
        public static final String COLUMN_CODE = "Code";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_DESCRIPTION = "Description";
    }
}
