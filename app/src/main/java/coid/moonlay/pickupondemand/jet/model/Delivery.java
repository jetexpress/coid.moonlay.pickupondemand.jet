package coid.moonlay.pickupondemand.jet.model;

public class Delivery
{
    private String awbNumber;
    private String consigneeName;
    private String consigneePhone;
    private String consigneeZipcode;
    private String consigneeEmail;
    private String consigneeAddress;
    private String deliveryOrderDate;
    private String operationStatus;
    private String operationStatusName;
    private WaybillHandover waybillHandover;


    public String getAwbNumber()
    {
        return awbNumber;
    }

    public void setAwbNumber(String awbNumber)
    {
        this.awbNumber = awbNumber;
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

    public String getConsigneeZipcode()
    {
        return consigneeZipcode;
    }

    public void setConsigneeZipcode(String consigneeZipcode)
    {
        this.consigneeZipcode = consigneeZipcode;
    }

    public String getConsigneeEmail()
    {
        return consigneeEmail;
    }

    public void setConsigneeEmail(String consigneeEmail)
    {
        this.consigneeEmail = consigneeEmail;
    }

    public String getConsigneeAddress()
    {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress)
    {
        this.consigneeAddress = consigneeAddress;
    }

    public String getDeliveryOrderDate()
    {
        return deliveryOrderDate;
    }

    public void setDeliveryOrderDate(String deliveryOrderDate)
    {
        this.deliveryOrderDate = deliveryOrderDate;
    }

    public String getOperationStatus()
    {
        return operationStatus;
    }

    public void setOperationStatus(String operationStatus)
    {
        this.operationStatus = operationStatus;
    }

    public String getOperationStatusName()
    {
        return operationStatusName;
    }

    public void setOperationStatusName(String operationStatusName)
    {
        this.operationStatusName = operationStatusName;
    }

    public WaybillHandover getWaybillHandover()
    {
        return waybillHandover;
    }

    public void setWaybillHandover(WaybillHandover waybillHandover)
    {
        this.waybillHandover = waybillHandover;
    }
}
