package coid.moonlay.pickupondemand.jet.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class NotificationPayload
{
    public static final String TYPE_PICKUP = "Pickup Request";
    public static final String ROLE_COURIER = "Courier";
    public static final String TYPE_PRS = "Pickup Runsheet";

    private String status;
    private String address;
    private String vehicleName;
    private String name;
    private String notificationTime;
    @SerializedName("DetailAddress")
    private String detailAddress;
    private String vehicleCode;
    private String role;
    private String code;
    private String type;
    private String scheduleDate;
    private String paymentMethod;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getVehicleName()
    {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName)
    {
        this.vehicleName = vehicleName;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getNotificationTime()
    {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime)
    {
        this.notificationTime = notificationTime;
    }

    public String getDetailAddress()
    {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress)
    {
        this.detailAddress = detailAddress;
    }

    public String getVehicleCode()
    {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode)
    {
        this.vehicleCode = vehicleCode;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getType()
    {
        return type;
    }

    public String getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(String scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPickup()
    {
        return this.type.equals(TYPE_PICKUP);
    }

    public boolean isCourier()
    {
        return this.role.equals(ROLE_COURIER);
    }

    public static NotificationPayload createFromDataString(String dataString)
    {
        try
        {
            Gson gson = new Gson();
            return gson.fromJson(dataString, NotificationPayload.class);
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}