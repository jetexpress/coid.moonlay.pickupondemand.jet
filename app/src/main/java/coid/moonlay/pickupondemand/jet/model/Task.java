package coid.moonlay.pickupondemand.jet.model;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import coid.moonlay.pickupondemand.jet.JetApplication;
import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.config.AppConfig;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;

public class Task implements Parcelable
{
    public static String PAYMENT_METHOD_COD_O = "COD-O";
    public static String PAYMENT_METHOD_COD_B = "COD-B";
    public static String PAYMENT_METHOD_COD_BO = "COD-BO";

    public static String VEHICLE_BIKE = "BIKE";
    public static String VEHICLE_CAR = "CAR";

    private String code;
    private String drsCode;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String status;
    private String courierId;
    private String date;
    private int index;
    @SerializedName("paymentMethodCode")
    private String paymentMethod;
    private Double totalFee;
    private Double itemValue;
    private Double discountValue;

    private String notificationTime;
    private String vehicle;

    public Task()
    {

    }

    public String getCode()
    {
        return code;
    }

    public String getDrsCode()
    {
        if (isPickup())
            return "";
        return drsCode;
    }

    public String getName()
    {
        return name;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAddress()
    {
        return address;
    }

    public String getStatus()
    {
        return status;
    }

    public String getCourierId()
    {
        return courierId;
    }

    public String getDate()
    {
        return date;
    }

    public Long getDateInMillis()
    {
        return Utility.DateFormat.getMillisFromDateString(this.date, "yyyy-MM-dd'T'HH:mm:ss", true);
    }

    public String getFormattedDate()
    {
        return Utility.DateFormat.getDateStringFromMillis(getDateInMillis(), "EEEE, dd MMMM yyyy - HH.mm", false);
    }

    public int getIndex()
    {
        return index;
    }

    public String getPaymentMethod()
    {
        return paymentMethod;
    }

    public Double getTotalFee()
    {
        return totalFee;
    }

    public Double getItemValue()
    {
        Double itemValue = 0D;

        if (isCOD_B() || isCOD_BO())
            itemValue = this.itemValue;

        return itemValue;
    }

    public String getItemValueString()
    {
        return Utility.NumberFormatter.doubleToString(getItemValue(), 0);
    }

    public Double getDiscountValue()
    {
        if (this.discountValue == null)
            return 0D;
        return this.discountValue;
    }

    public String getNotificationTime()
    {
        return notificationTime;
    }

    public void setNotificationTime(String notificationTime)
    {
        this.notificationTime = notificationTime;
    }

    public Long getNotificationTimeInMillis()
    {
        if (notificationTime != null && !notificationTime.isEmpty())
            return Utility.DateFormat.getMillisFromDateString(notificationTime, "yyyy-MM-dd'T'HH:mm:ss", true);
        else
            return null;
    }

    public Long getCountDownStartTimeInMillis()
    {
        if (getNotificationTimeInMillis() == null)
            return -1L;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = calendar.getTime();

        long waitingTime;
        Config config = DBQuery.getSingle(Config.class);
        if (config != null)
            waitingTime = config.getWaitingTime() * 1000;
        else
            waitingTime = AppConfig.DEFAULT_PICKUP_NOTIFICATION_WAITING_TIME;

        Log.d("JET_127", "CURRENT MILLIS : " + date.getTime());
        Log.d("JET_127", "NOTIFICATION DATE + COUNTDOWN : " + String.valueOf(getNotificationTimeInMillis() + waitingTime));
        return getNotificationTimeInMillis() + waitingTime - date.getTime();
    }

    public String getVehicle()
    {
        return vehicle;
    }

    public void setVehicle(String vehicle)
    {
        this.vehicle = vehicle;
    }

    public Boolean isNotificationExpired()
    {
        return getCountDownStartTimeInMillis() <= 0;
    }

    public Boolean isPickup()
    {
        return drsCode == null || drsCode.isEmpty();
    }

    public Boolean isAssigned()
    {
        return this.status != null && this.status.equals(Pickup.STATUS_ASSIGNED);
    }

    public Boolean isRequested()
    {
        return this.status != null && this.status.equals(Pickup.STATUS_REQUESTED);
    }

    public Boolean isTripStarted()
    {
        return this.status != null && this.status.equals(Pickup.STATUS_TRIP_STARTED);
    }

    public Boolean isCancelled()
    {
        return this.status != null && this.status.equals(Pickup.STATUS_CANCELLED);
    }

    public Boolean isBike()
    {
        return this.vehicle != null && this.vehicle.equals(VEHICLE_BIKE);
    }

    public Boolean isCar()
    {
        return this.vehicle != null && this.vehicle.equals(VEHICLE_CAR);
    }

    public String getTaskType()
    {
        if (isPickup())
            return Utility.Message.get(R.string.task_type_pickup);
        else
            return Utility.Message.get(R.string.task_type_delivery);
    }

    public String getTaskTypeLabel()
    {
        return "";
//        if (isPickup())
//            return Utility.Message.get(R.string.task_type_label_pickup_number);
//        else
//            return Utility.Message.get(R.string.task_type_label_awb_number);
    }

    public Drawable getTaskTypeDrawable()
    {
        if (isPickup())
            return ContextCompat.getDrawable(JetApplication.getInstance(), R.drawable.ic_pickup_white);
        else
            return ContextCompat.getDrawable(JetApplication.getInstance(), R.drawable.ic_delivery_white);
    }

    public Double getDeliveryFee()
    {
        Double totalFee = 0D;

        if (isCOD_O() || isCOD_BO())
            totalFee = this.totalFee + getDiscountValue();

        return totalFee;
    }

    public String getDeliveryFeeString()
    {
        return Utility.NumberFormatter.doubleToString(getDeliveryFee(), 0);
    }

    public Boolean isCOD_O()
    {
        return this.paymentMethod.equals(PAYMENT_METHOD_COD_O);
    }

    public Boolean isCOD_B()
    {
        return this.paymentMethod.equals(PAYMENT_METHOD_COD_B);
    }

    public Boolean isCOD_BO()
    {
        return this.paymentMethod.equals(PAYMENT_METHOD_COD_BO);
    }

    public Boolean isCOD()
    {
        return isCOD_O() || isCOD_B() || isCOD_BO();
    }

    public Double getCODFee()
    {
        Double itemValue = 0D;
        Double totalFee = 0D;

        if (isCOD_B() || isCOD_BO())
            itemValue = this.itemValue;

        if (isCOD_O() || isCOD_BO())
            totalFee = this.totalFee + getDiscountValue();

        return itemValue + totalFee;
    }

    public String getCODFeeString()
    {
        return Utility.NumberFormatter.doubleToString(getCODFee(), 0);
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setDrsCode(String drsCode)
    {
        this.drsCode = drsCode;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public void setCourierId(String courierId)
    {
        this.courierId = courierId;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public void setPaymentMethod(String paymentMethod)
    {
        this.paymentMethod = paymentMethod;
    }

    public void setTotalFee(Double totalFee)
    {
        this.totalFee = totalFee;
    }

    public void setItemValue(Double itemValue)
    {
        this.itemValue = itemValue;
    }

    public void setDiscountValue(Double discountValue)
    {
        this.discountValue = discountValue;
    }

    public static Task createTaskFromNotification(NotificationPayload notificationPayload)
    {
        Task task = new Task();
        task.setCode(notificationPayload.getCode());
        task.setDrsCode(notificationPayload.isPickup() ? "" : notificationPayload.getCode());
        task.setName(notificationPayload.getName());
        task.setPhone("");
        task.setEmail("");
        task.setAddress(notificationPayload.getAddress());
        task.setStatus(notificationPayload.getStatus());
        task.setCourierId("");
        task.setDate("");
        task.setIndex(0);
        task.setPaymentMethod(notificationPayload.getPaymentMethod());
        task.setTotalFee(0D);
        task.setItemValue(0D);
        task.setDiscountValue(0D);
        task.setNotificationTime(notificationPayload.getNotificationTime());
        task.setVehicle(notificationPayload.getVehicleCode());
        return task;
    }

    public static Task newDummyPickupInstance(String numberString)
    {
        Task dummyPickupTask = new Task();
        dummyPickupTask.setCode("dummyPickupCode" + numberString);
        dummyPickupTask.setDrsCode("");
        dummyPickupTask.setName("dummyName" + numberString);
        dummyPickupTask.setPhone("");
        dummyPickupTask.setEmail("dummyEmail" + numberString + "@email.com");
        dummyPickupTask.setAddress("dummyAddress" + numberString);
        dummyPickupTask.setStatus("ASSIGNED");
        dummyPickupTask.setCourierId("dummyCourierId" + numberString);
        dummyPickupTask.setDate("2017-05-16T07:45:27.9404943");
        dummyPickupTask.setIndex(0);
        dummyPickupTask.setPaymentMethod("CASH");
        dummyPickupTask.setTotalFee(0D);
        dummyPickupTask.setItemValue(0D);
        dummyPickupTask.setDiscountValue(0D);
        return dummyPickupTask;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(code);
        dest.writeString(drsCode);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(status);
        dest.writeString(courierId);
        dest.writeString(date);
        dest.writeInt(index);
        dest.writeString(paymentMethod);
        dest.writeDouble(totalFee);
        dest.writeDouble(itemValue);
        dest.writeDouble(discountValue);
        dest.writeString(notificationTime);
    }

    private Task(Parcel in)
    {
        code = in.readString();
        drsCode = in.readString();
        name = in.readString();
        phone = in.readString();
        email = in.readString();
        address = in.readString();
        status = in.readString();
        courierId = in.readString();
        date = in.readString();
        index = in.readInt();
        paymentMethod = in.readString();
        totalFee = in.readDouble();
        itemValue = in.readDouble();
        discountValue = in.readDouble();
        notificationTime = in.readString();
    }

    public static final Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>()
    {
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public static Task getDummy()
    {
        Task dummyTask = new Task();

        dummyTask.setCode("AWB#123123");
        dummyTask.setDrsCode("DRS#123123");
        dummyTask.setName("Name");
        dummyTask.setPhone("123123");
        dummyTask.setEmail("uletbe@uletbe.com");
        dummyTask.setAddress("Pepohonan");
        dummyTask.setStatus("STATUS");
        dummyTask.setCourierId("kurirID#098098");
        dummyTask.setDate("20180102");
        dummyTask.setIndex(1);
        dummyTask.setPaymentMethod(PAYMENT_METHOD_COD_BO);
        dummyTask.setTotalFee(10000D);
        dummyTask.setItemValue(5000D);
        dummyTask.setDiscountValue(0D);
        dummyTask.setVehicle(VEHICLE_BIKE);
        return dummyTask;
    }
}
