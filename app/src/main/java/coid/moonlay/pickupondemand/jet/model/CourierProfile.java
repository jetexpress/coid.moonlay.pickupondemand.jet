package coid.moonlay.pickupondemand.jet.model;

public class CourierProfile
{
    private boolean available;
    private String branchCode;
    private String kioskCode;
    private String kioskType;
    private Boolean hasTripStarted;

    public boolean isAvailable()
    {
        return available;
    }

    public void setAvailable(boolean available)
    {
        this.available = available;
    }

    public String getBranchCode()
    {
        return branchCode;
    }

    public void setBranchCode(String branchCode)
    {
        this.branchCode = branchCode;
    }

    public String getKioskCode()
    {
        return kioskCode;
    }

    public void setKioskCode(String kioskCode)
    {
        this.kioskCode = kioskCode;
    }

    public String getKioskType()
    {
        return kioskType;
    }

    public void setKioskType(String kioskType)
    {
        this.kioskType = kioskType;
    }

    public Boolean getHasTripStarted()
    {
        return hasTripStarted;
    }

    public void setHasTripStarted(Boolean hasTripStarted)
    {
        this.hasTripStarted = hasTripStarted;
    }
}
