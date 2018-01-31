package coid.moonlay.pickupondemand.jet.model;

public class SuccessDelivery
{
    private String receiverName;
    private String relation;
    private String base64ReceiverSignature;
    private String note;
    private Double latitude;
    private Double longitude;

    public String getReceiverName()
    {
        return receiverName;
    }

    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }

    public String getRelation()
    {
        return relation;
    }

    public void setRelation(String relation)
    {
        this.relation = relation;
    }

    public String getBase64ReceiverSignature()
    {
        return base64ReceiverSignature;
    }

    public void setBase64ReceiverSignature(String base64ReceiverSignature)
    {
        this.base64ReceiverSignature = base64ReceiverSignature;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
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
}
