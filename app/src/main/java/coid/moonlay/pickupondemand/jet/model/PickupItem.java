package coid.moonlay.pickupondemand.jet.model;

import android.os.Parcel;
import android.os.Parcelable;

import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;

public class PickupItem implements Parcelable
{
    public static final String STATUS_LOCKED = "LOCKED";
    public static final String STATUS_UNLOCKED = "UNLOCKED";
    public static final String STATUS_COMPLETED = "COMPLETED";

    private Long id;
    private String code;
    private String consigneeJetIDCode;
    private String consigneeName;
    private String consigneePhone;
    private String consigneeAddress;
    private String consigneeAddressDetail;
    private Double consigneeLatitude;
    private Double consigneeLongitude;
    private String dropshipperName;
    private String dropshipperPhone;
    private String dropshipperAddress;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private String productCode;
    private String productName;
    private String packagingItemCode;
    private String packagingItemName;
    private Boolean isInsured;
    private String description;
    private Double itemValue;
    private String status;
    private String locationCode;
    private String locationName;
    private Double fee;
    private String imageBase64;
    private Double insuranceFee;
    private Double packagingFee;
    private Double discount;
    private Double totalFee;
    private String shippingLabelId;
    private String unlockCode;
    private String waybillNumber;

    public PickupItem()
    {

    }

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

    public String getConsigneeJetIDCode()
    {
        return consigneeJetIDCode;
    }

    public void setConsigneeJetIDCode(String consigneeJetIDCode)
    {
        this.consigneeJetIDCode = consigneeJetIDCode;
    }

    public String getConsigneeName()
    {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName)
    {
        this.consigneeName = consigneeName;
    }

    public String getConsigneePhone()
    {
        return consigneePhone;
    }

    public void setConsigneePhone(String consigneePhone)
    {
        this.consigneePhone = consigneePhone;
    }

    public String getConsigneeAddress()
    {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress)
    {
        this.consigneeAddress = consigneeAddress;
    }

    public String getConsigneeAddressDetail()
    {
        return consigneeAddressDetail;
    }

    public void setConsigneeAddressDetail(String consigneeAddressDetail)
    {
        this.consigneeAddressDetail = consigneeAddressDetail;
    }

    public Double getConsigneeLatitude()
    {
        return consigneeLatitude;
    }

    public void setConsigneeLatitude(Double consigneeLatitude)
    {
        this.consigneeLatitude = consigneeLatitude;
    }

    public Double getConsigneeLongitude()
    {
        return consigneeLongitude;
    }

    public void setConsigneeLongitude(Double consigneeLongitude)
    {
        this.consigneeLongitude = consigneeLongitude;
    }

    public String getDropshipperName()
    {
        return dropshipperName;
    }

    public void setDropshipperName(String dropshipperName)
    {
        this.dropshipperName = dropshipperName;
    }

    public String getDropshipperPhone()
    {
        return dropshipperPhone;
    }

    public void setDropshipperPhone(String dropshipperPhone)
    {
        this.dropshipperPhone = dropshipperPhone;
    }

    public String getDropshipperAddress()
    {
        return dropshipperAddress;
    }

    public void setDropshipperAddress(String dropshipperAddress)
    {
        this.dropshipperAddress = dropshipperAddress;
    }

    public Double getWeight()
    {
        return weight;
    }

    public String getWeightString()
    {
        return Utility.NumberFormatter.doubleToString(this.weight, 2);
    }

    public void setWeight(Double weight)
    {
        this.weight = weight;
    }

    public Double getLength()
    {
        return length;
    }

    public String getLengthString()
    {
        return Utility.NumberFormatter.doubleToString(this.length, 0);
    }

    public void setLength(Double length)
    {
        this.length = length;
    }

    public Double getWidth()
    {
        return width;
    }

    public String getWidthString()
    {
        return Utility.NumberFormatter.doubleToString(this.width, 0);
    }

    public void setWidth(Double width)
    {
        this.width = width;
    }

    public Double getHeight()
    {
        return height;
    }

    public String getHeightString()
    {
        return Utility.NumberFormatter.doubleToString(this.height, 0);
    }

    public void setHeight(Double height)
    {
        this.height = height;
    }

    public String getProductCode()
    {
        return productCode;
    }

    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getPackagingItemCode()
    {
        return packagingItemCode;
    }

    public void setPackagingItemCode(String packagingItemCode)
    {
        this.packagingItemCode = packagingItemCode;
    }

    public String getPackagingItemName()
    {
        return packagingItemName;
    }

    public void setPackagingItemName(String packagingItemName)
    {
        this.packagingItemName = packagingItemName;
    }

    public Boolean getInsured()
    {
        return isInsured;
    }

    public void setInsured(Boolean insured)
    {
        isInsured = insured;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Double getItemValue()
    {
        if (itemValue == null)
            return 0D;
        return itemValue;
    }

    public String getItemValueString()
    {
        return Utility.NumberFormatter.doubleToString(getItemValue(), 0);
    }

    public void setItemValue(Double itemValue)
    {
        this.itemValue = itemValue;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getLocationCode()
    {
        return locationCode;
    }

    public void setLocationCode(String locationCode)
    {
        this.locationCode = locationCode;
    }

    public String getLocationName()
    {
        return locationName;
    }

    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    public Double getFee()
    {
        if (fee == null)
            return 0D;
        return fee;
    }

    public String getFeeString()
    {
        return Utility.NumberFormatter.doubleToString(getFee(), 0);
    }

    public void setFee(Double fee)
    {
        this.fee = fee;
    }

    public String getImageBase64()
    {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64)
    {
        this.imageBase64 = imageBase64;
    }

    public Double getInsuranceFee()
    {
        if (insuranceFee == null)
            return 0D;
        return insuranceFee;
    }

    public String getInsuranceFeeString()
    {
        return Utility.NumberFormatter.doubleToString(getInsuranceFee(), 0);
    }

    public void setInsuranceFee(Double insuranceFee)
    {
        this.insuranceFee = insuranceFee;
    }

    public Double getPackagingFee()
    {
        if (packagingFee == null)
            return 0D;
        return packagingFee;
    }

    public String getPackagingFeeString()
    {
        return Utility.NumberFormatter.doubleToString(getPackagingFee(), 0);
    }

    public void setPackagingFee(Double packagingFee)
    {
        this.packagingFee = packagingFee;
    }

    public Double getDiscount()
    {
        if (discount == null)
            return 0D;
        return discount;
    }

    public String getDiscountString()
    {
        return Utility.NumberFormatter.doubleToString(getDiscount(), 0);
    }

    public void setDiscount(Double discount)
    {
        this.discount = discount;
    }

    public Double getTotalFee()
    {
        if (totalFee == null)
            return 0D;
        return totalFee;
    }

    public String getTotalFeeString()
    {
        return Utility.NumberFormatter.doubleToString(getTotalFee(), 0);
    }

    public void setTotalFee(Double totalFee)
    {
        this.totalFee = totalFee;
    }

    public String getShippingLabelId()
    {
        return shippingLabelId;
    }

    public void setShippingLabelId(String shippingLabelId)
    {
        this.shippingLabelId = shippingLabelId;
    }

    public String getUnlockCode()
    {
        return unlockCode;
    }

    public void setUnlockCode(String unlockCode)
    {
        this.unlockCode = unlockCode;
    }

    public String getWaybillNumber()
    {
        return waybillNumber;
    }

    public void setWaybillNumber(String waybillNumber)
    {
        this.waybillNumber = waybillNumber;
    }

    public String getPrefixLockedCode()
    {
        if (this.unlockCode != null && this.unlockCode.length() >= 6)
            return this.unlockCode.substring(0, this.unlockCode.length() - 4);

        return "";
    }

    public String getLockedCode()
    {
        if (getPrefixLockedCode().isEmpty())
            return Utility.Message.get(R.string.pickup_item_invalid_unlock_code);
        return getPrefixLockedCode() + "****";
    }

    public String getCombinedCode()
    {
        if (this.waybillNumber != null && !this.waybillNumber.isEmpty())
            return this.code + " | " + this.waybillNumber;
        return this.code;
    }

    public boolean isLocked()
    {
        return this.status.equals(STATUS_LOCKED);
    }

    public boolean isUnlocked()
    {
        return this.status.equals(STATUS_UNLOCKED);
    }

    public boolean isCompleted()
    {
        return this.status.equals(STATUS_COMPLETED);
    }

    public boolean isStatusValid()
    {
        return isCompleted() || isUnlocked() || isLocked();
    }

    public boolean isLocationValid()
    {
        return this.locationCode != null && !this.locationCode.isEmpty() && this.locationName != null && !this.locationName.isEmpty();
    }

    public static PickupItem createFromQRCode(PickupQRCode pickupQRCode)
    {
        PickupItem pickupItem = new PickupItem();

        pickupItem.setShippingLabelId(pickupQRCode.getUid());
        pickupItem.setDropshipperName(pickupQRCode.getsName());
        pickupItem.setDropshipperPhone(pickupQRCode.getsPhone());
        pickupItem.setConsigneeName(pickupQRCode.getcName());
        pickupItem.setConsigneePhone(pickupQRCode.getcPhone());
        pickupItem.setConsigneeAddressDetail(pickupQRCode.getcAddress());
        pickupItem.setProductCode(pickupQRCode.getcProduct());
        pickupItem.setDescription(pickupQRCode.getDesc());

        return pickupItem;
    }

    public void update(PickupItem pickupItem)
    {
        this.id = pickupItem.getId();
        this.code = pickupItem.getCode();
        this.consigneeJetIDCode = pickupItem.getConsigneeJetIDCode();
        this.consigneeName = pickupItem.getConsigneeName();
        this.consigneePhone = pickupItem.getConsigneePhone();
        this.consigneeAddress = pickupItem.getConsigneeAddress();
        this.consigneeAddressDetail = pickupItem.getConsigneeAddressDetail();
        this.consigneeLatitude = pickupItem.getConsigneeLatitude();
        this.consigneeLongitude = pickupItem.getConsigneeLongitude();
        this.dropshipperName = pickupItem.getDropshipperName();
        this.dropshipperPhone = pickupItem.getDropshipperPhone();
        this.dropshipperAddress = pickupItem.getDropshipperAddress();
        this.weight = pickupItem.getWeight();
        this.length = pickupItem.getLength();
        this.width = pickupItem.getWidth();
        this.height = pickupItem.getHeight();
        this.productCode = pickupItem.getProductCode();
        this.productName = pickupItem.getProductName();
        this.packagingItemCode = pickupItem.getPackagingItemCode();
        this.packagingItemName = pickupItem.getPackagingItemName();
        this.isInsured = pickupItem.getInsured();
        this.description = pickupItem.getDescription();
        this.itemValue = pickupItem.getItemValue();
        this.status = pickupItem.getStatus();
        this.locationCode = pickupItem.getLocationCode();
        this.locationName = pickupItem.getLocationName();
        this.fee = pickupItem.getFee();
        this.imageBase64 = pickupItem.getImageBase64();
        this.insuranceFee = pickupItem.getInsuranceFee();
        this.packagingFee = pickupItem.getPackagingFee();
        this.discount = pickupItem.discount;
        this.totalFee = pickupItem.getTotalFee();
        this.shippingLabelId = pickupItem.getShippingLabelId();
        this.unlockCode = pickupItem.getUnlockCode();
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
        dest.writeString(consigneeJetIDCode);
        dest.writeString(consigneeName);
        dest.writeString(consigneePhone);
        dest.writeString(consigneeAddress);
        dest.writeString(consigneeAddressDetail);
        dest.writeDouble(consigneeLatitude);
        dest.writeDouble(consigneeLongitude);
        dest.writeString(dropshipperName);
        dest.writeString(dropshipperPhone);
        dest.writeString(dropshipperAddress);
        dest.writeDouble(weight);
        dest.writeDouble(length);
        dest.writeDouble(width);
        dest.writeDouble(height);
        dest.writeString(productCode);
        dest.writeString(productName);
        dest.writeString(packagingItemCode);
        dest.writeString(packagingItemName);
        dest.writeInt(isInsured ? 1 : 0);
        dest.writeString(description);
        dest.writeDouble(itemValue);
        dest.writeString(status);
        dest.writeString(locationCode);
        dest.writeString(locationName);
        dest.writeDouble(fee);
        dest.writeString(imageBase64);
        dest.writeDouble(insuranceFee);
        dest.writeDouble(packagingFee);
        dest.writeDouble(discount);
        dest.writeDouble(totalFee);
        dest.writeString(shippingLabelId);
        dest.writeString(unlockCode);
    }

    private PickupItem(Parcel in)
    {
        id = in.readLong();
        code = in.readString();
        consigneeJetIDCode = in.readString();
        consigneeName = in.readString();
        consigneePhone = in.readString();
        consigneeAddress = in.readString();
        consigneeAddressDetail = in.readString();
        consigneeLatitude = in.readDouble();
        consigneeLongitude = in.readDouble();
        dropshipperName = in.readString();
        dropshipperPhone = in.readString();
        dropshipperAddress = in.readString();
        weight = in.readDouble();
        length = in.readDouble();
        width = in.readDouble();
        height = in.readDouble();
        productCode = in.readString();
        productName = in.readString();
        packagingItemCode = in.readString();
        packagingItemName = in.readString();
        isInsured = in.readInt() != 0;
        description = in.readString();
        itemValue = in.readDouble();
        status = in.readString();
        locationCode = in.readString();
        locationName = in.readString();
        fee = in.readDouble();
        imageBase64 = in.readString();
        insuranceFee = in.readDouble();
        packagingFee = in.readDouble();
        discount = in.readDouble();
        totalFee = in.readDouble();
        shippingLabelId = in.readString();
        unlockCode = in.readString();
    }

    public static final Parcelable.Creator<PickupItem> CREATOR = new Parcelable.Creator<PickupItem>()
    {
        public PickupItem createFromParcel(Parcel in) {
            return new PickupItem(in);
        }
        public PickupItem[] newArray(int size) {
            return new PickupItem[size];
        }
    };
}
