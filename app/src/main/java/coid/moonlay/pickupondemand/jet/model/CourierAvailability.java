package coid.moonlay.pickupondemand.jet.model;

public class CourierAvailability
{
    private String userId;
    private Boolean available;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public Boolean getAvailable()
    {
        return available;
    }

    public void setAvailable(Boolean available)
    {
        this.available = available;
    }
}
