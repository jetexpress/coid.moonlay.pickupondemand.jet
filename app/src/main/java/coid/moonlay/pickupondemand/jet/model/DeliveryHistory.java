package coid.moonlay.pickupondemand.jet.model;

import java.io.Serializable;

public class DeliveryHistory extends Delivery implements Serializable
{
    private String operationStatus;
    private String operationStatusName;
    private WaybillHandover waybillHandover;

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
