package coid.moonlay.pickupondemand.jet.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import coid.moonlay.pickupondemand.jet.Utility;

import static coid.moonlay.pickupondemand.jet.model.Task.PAYMENT_METHOD_COD_B;
import static coid.moonlay.pickupondemand.jet.model.Task.PAYMENT_METHOD_COD_BO;

public class Pickup implements Parcelable
{
    public static final String STATUS_DRAFTED = "DRAFTED";
    public static final String STATUS_REQUESTED = "REQUESTED";
    public static final String STATUS_ASSIGNED = "ASSIGNED";
    public static final String STATUS_TRIP_STARTED = "TRIP STARTED";
    public static final String STATUS_ARRIVED = "ARRIVED";
    public static final String STATUS_TRIP_CANCELLED = "TRIP_CANCELLED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_COMPLETED = "COMPLETED";

    private Long id;
    private String code;
    private String jetIDCode;
    private String name;
    private String address;
    private String addressDetail;
    private String phone;
    private String email;
    private String pic;
    private String position;
    private String vehicleCode;
    private String vehicleName;
    private String city;
    private String province;
    private String branchCode;
    private String branchName;
    private String description;
    private String status;
    private String paymentMethodCode;
    private String paymentMethodName;
    private Double latitude;
    private Double longitude;
    @SerializedName("pickupRequestItems")
    private List<PickupItem> pickupItemList;
    @SerializedName("pickupRequestTracks")
    private List<PickupTrack> pickupTracks;
    private String imageBase64;
    private Double rating;
    private Long quickPickupItemCount;
    private Long actualPickupItemCount;
    private Courier courier;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getJetIDCode()
    {
        return jetIDCode;
    }

    public void setJetIDCode(String jetIDCode)
    {
        this.jetIDCode = jetIDCode;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddressDetail()
    {
        if (addressDetail == null || addressDetail.isEmpty())
            return "-";
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail)
    {
        this.addressDetail = addressDetail;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPic()
    {
        if (pic == null || pic.isEmpty())
            return "-";
        return pic;
    }

    public void setPic(String pic)
    {
        this.pic = pic;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getVehicleCode()
    {
        return vehicleCode;
    }

    public void setVehicleCode(String vehicleCode)
    {
        this.vehicleCode = vehicleCode;
    }

    public String getVehicleName()
    {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName)
    {
        this.vehicleName = vehicleName;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public String getBranchCode()
    {
        return branchCode;
    }

    public void setBranchCode(String branchCode)
    {
        this.branchCode = branchCode;
    }

    public String getBranchName()
    {
        return branchName;
    }

    public void setBranchName(String branchName)
    {
        this.branchName = branchName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getPaymentMethodCode()
    {
        return paymentMethodCode;
    }

    public void setPaymentMethodCode(String paymentMethodCode)
    {
        this.paymentMethodCode = paymentMethodCode;
    }

    public String getPaymentMethodName()
    {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName)
    {
        this.paymentMethodName = paymentMethodName;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public String getLatLngString()
    {
        return String.valueOf(latitude) + "," + String.valueOf(longitude);
    }

    public List<PickupItem> getPickupItemList()
    {
        return pickupItemList;
    }

    public void setPickupItemList(List<PickupItem> pickupItemList)
    {
        this.pickupItemList = pickupItemList;
    }

    public List<PickupTrack> getPickupTracks()
    {
        return pickupTracks;
    }

    public void setPickupTracks(List<PickupTrack> pickupTracks)
    {
        this.pickupTracks = pickupTracks;
    }

    public String getImageBase64()
    {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64)
    {
        this.imageBase64 = imageBase64;
    }

    public Double getRating()
    {
        return rating;
    }

    public void setRating(Double rating)
    {
        this.rating = rating;
    }

    public Long getQuickPickupItemCount()
    {
        if (quickPickupItemCount == null)
            return 0L;
        return quickPickupItemCount;
    }

    public void setQuickPickupItemCount(Long quickPickupItemCount)
    {
        this.quickPickupItemCount = quickPickupItemCount;
    }

    public Long getActualPickupItemCount()
    {
        return actualPickupItemCount;
    }

    public void setActualPickupItemCount(Long actualPickupItemCount)
    {
        this.actualPickupItemCount = actualPickupItemCount;
    }

    public Courier getCourier()
    {
        return courier;
    }

    public void setCourier(Courier courier)
    {
        this.courier = courier;
    }

    public Double getTotalWeight()
    {
        Double totalWeight = 0D;
        for (PickupItem pickupItem : pickupItemList)
        {
            totalWeight += pickupItem.getWeight();
        }
        return totalWeight;
    }

    public Double getTotalFee()
    {
        Double totalFee = 0D;
        if (pickupItemList != null && pickupItemList.size() > 0)
        {
            for (PickupItem pickupItem : pickupItemList)
            {
                totalFee += pickupItem.getTotalFee();
            }
        }
        return totalFee;
    }

    public String getTotalFeeString()
    {
        return Utility.NumberFormatter.doubleToString(getTotalFee(), 0);
    }

    public boolean hasPickupItems()
    {
        return pickupItemList != null && pickupItemList.size() > 0;
    }

    public boolean isQuickPickup()
    {
        return !hasPickupItems();
    }

    public boolean isAllItemsUnlocked()
    {
        for (PickupItem pickupItem : pickupItemList)
        {
            if (!pickupItem.isUnlocked() && !pickupItem.isCompleted())
                return false;
        }

        return true;
    }

    public boolean isAllItemsCompleted()
    {
        for (PickupItem pickupItem : pickupItemList)
        {
            if (!pickupItem.isCompleted())
                return false;
        }

        return true;
    }

    public boolean hasLocation()
    {
        return this.latitude != 0 || this.longitude != 0;
    }

    public boolean isTripStarted()
    {
        return this.status.equals(STATUS_TRIP_STARTED);
    }

    public boolean hasArrived()
    {
        return this.status.equals(STATUS_ARRIVED);
    }

    public Boolean isCOD_B()
    {
        return this.paymentMethodCode.equals(PAYMENT_METHOD_COD_B);
    }

    public Boolean isCOD_BO()
    {
        return this.paymentMethodCode.equals(PAYMENT_METHOD_COD_BO);
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeLong(id);
        dest.writeString(code);
        dest.writeString(jetIDCode);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(addressDetail);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(pic);
        dest.writeString(position);
        dest.writeString(vehicleCode);
        dest.writeString(vehicleName);
        dest.writeString(city);
        dest.writeString(province);
        dest.writeString(branchCode);
        dest.writeString(branchName);
        dest.writeString(description);
        dest.writeString(status);
        dest.writeString(paymentMethodCode);
        dest.writeString(paymentMethodName);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeTypedList(pickupItemList);
    }

    private Pickup(Parcel in)
    {
        id = in.readLong();
        code = in.readString();
        jetIDCode = in.readString();
        name = in.readString();
        address = in.readString();
        addressDetail = in.readString();
        phone = in.readString();
        email = in.readString();
        pic = in.readString();
        position = in.readString();
        vehicleCode = in.readString();
        vehicleName = in.readString();
        city = in.readString();
        province = in.readString();
        branchCode = in.readString();
        branchName = in.readString();
        description = in.readString();
        status = in.readString();
        paymentMethodCode = in.readString();
        paymentMethodName = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        in.readTypedList(pickupItemList, PickupItem.CREATOR);
    }

    public static final Parcelable.Creator<Pickup> CREATOR = new Parcelable.Creator<Pickup>()
    {
        public Pickup createFromParcel(Parcel in) {
            return new Pickup(in);
        }
        public Pickup[] newArray(int size) {
            return new Pickup[size];
        }
    };
}
