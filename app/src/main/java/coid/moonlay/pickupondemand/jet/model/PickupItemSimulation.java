package coid.moonlay.pickupondemand.jet.model;

import android.os.Parcelable;

import coid.moonlay.pickupondemand.jet.Utility;

public class PickupItemSimulation extends PickupItem implements Parcelable
{
    private String branchCode;

    private String consigneeLocationCode;
    private Double chargePackaging;
    private Double chargeInsurance;
    private Double discountValue;
    private Double price;
    private Double chargeAdditional;
    private Double latitude;
    private Double longitude;
    private String userId;

    public PickupItemSimulation()
    {

    }

    public PickupItemSimulation(PickupItem pickupItem)
    {
//        update(pickupItem);
        setWeight(pickupItem.getWeight() != null && pickupItem.getWeight() > 0 ? pickupItem.getWeight() : 1);
        setLength(pickupItem.getLength() != null && pickupItem.getLength() > 0 ? pickupItem.getLength() : 1);
        setWidth(pickupItem.getWidth() != null && pickupItem.getWidth() > 0 ? pickupItem.getWidth() : 1);
        setHeight(pickupItem.getHeight() != null && pickupItem.getHeight() > 0 ? pickupItem.getHeight() : 1);
        setProductCode(pickupItem.getProductCode() != null  ? pickupItem.getProductCode() : Product.CODE_REGULAR);
        setPackagingItemCode(pickupItem.getPackagingItemCode() != null ? pickupItem.getPackagingItemCode() : "");
        setInsured(pickupItem.getInsured() != null ? pickupItem.getInsured() : false);
        setItemValue(pickupItem.getItemValue() != null ? pickupItem.getItemValue() : 0);
        setConsigneeLocationCode(pickupItem.getLocationCode());
    }

    public String getBranchCode()
    {
        return branchCode;
    }

    public void setBranchCode(String branchCode)
    {
        this.branchCode = branchCode;
    }

    public String getConsigneeLocationCode()
    {
        return consigneeLocationCode;
    }

    public void setConsigneeLocationCode(String consigneeLocationCode)
    {
        this.consigneeLocationCode = consigneeLocationCode;
    }

    public Double getChargePackaging()
    {
        return chargePackaging;
    }

    public String getChargePackagingFeeString()
    {
        return Utility.NumberFormatter.doubleToString(getChargePackaging(), 0);
    }

    public void setChargePackaging(Double chargePackaging)
    {
        this.chargePackaging = chargePackaging;
    }

    public Double getChargeInsurance()
    {
        return chargeInsurance;
    }

    public String getChargeInsuranceFeeString()
    {
        return Utility.NumberFormatter.doubleToString(getChargeInsurance(), 0);
    }

    public void setChargeInsurance(Double chargeInsurance)
    {
        this.chargeInsurance = chargeInsurance;
    }

    public Double getDiscountValue()
    {
        return discountValue;
    }

    public String getDiscountValueString()
    {
        return Utility.NumberFormatter.doubleToString(getDiscountValue(), 0);
    }

    public void setDiscountValue(Double discountValue)
    {
        this.discountValue = discountValue;
    }

    public Double getPrice()
    {
        return price;
    }

    public String getPriceString()
    {
        return Utility.NumberFormatter.doubleToString(getPrice(), 0);
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Double getChargeAdditional()
    {
        return chargeAdditional;
    }

    public String getChargeAdditionalString()
    {
        return Utility.NumberFormatter.doubleToString(getPrice(), 0);
    }

    public void setChargeAdditional(Double chargeAdditional)
    {
        this.chargeAdditional = chargeAdditional;
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

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public void update(PickupItem pickupItem)
    {
        setWeight(pickupItem.getWeight());
        setLength(pickupItem.getLength());
        setWidth(pickupItem.getWidth());
        setHeight(pickupItem.getHeight());
        setProductCode(pickupItem.getProductCode());
        setPackagingItemCode(pickupItem.getPackagingItemCode());
        setInsured(pickupItem.getInsured());
        setItemValue(pickupItem.getItemValue());
        setConsigneeLatitude(pickupItem.getConsigneeLatitude());
        setConsigneeLongitude(pickupItem.getConsigneeLongitude());
        setTotalFee(pickupItem.getTotalFee());
    }
}