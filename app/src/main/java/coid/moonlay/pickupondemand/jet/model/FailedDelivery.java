package coid.moonlay.pickupondemand.jet.model;

public class FailedDelivery
{
    private String operationCode;
    private Boolean isRetry;
    private String note;
    private Double latitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    private Double longitude;

    public String getOperationCode()
    {
        return operationCode;
    }

    public void setOperationCode(String operationCode)
    {
        this.operationCode = operationCode;
    }

    public Boolean getRetry()
    {
        return isRetry;
    }

    public void setRetry(Boolean retry)
    {
        isRetry = retry;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }
}
