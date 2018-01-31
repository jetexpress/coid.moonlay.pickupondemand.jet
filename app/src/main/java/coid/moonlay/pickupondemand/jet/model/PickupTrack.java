package coid.moonlay.pickupondemand.jet.model;

public class PickupTrack
{
    private int id;
    private String pickupRequestCode;
    private String status;
    private String trackDate;
    private String note;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPickupRequestCode()
    {
        return pickupRequestCode;
    }

    public void setPickupRequestCode(String pickupRequestCode)
    {
        this.pickupRequestCode = pickupRequestCode;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getTrackDate()
    {
        return trackDate;
    }

    public void setTrackDate(String trackDate)
    {
        this.trackDate = trackDate;
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
