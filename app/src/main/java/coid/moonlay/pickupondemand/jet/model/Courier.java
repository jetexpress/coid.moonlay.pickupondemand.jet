package coid.moonlay.pickupondemand.jet.model;

public class Courier
{
    private Long id;
    private String userId;
    private String username;
    private String fullname;
    private String dateOfBirth;
    private String phoneNumber;
    private String address;
    private String label;
    private Boolean courierAvailability;
    private Boolean isOwnedByJet;
    private String ownedByKioskCode;
    private String profilePictureUrl;
    private String managedBy;
    private String referencedBy;
    private String imei;
    private Double rating;
    private Integer ratingCount;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getFullname()
    {
        return fullname;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public String getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public Boolean getCourierAvailability()
    {
        return courierAvailability;
    }

    public void setCourierAvailability(Boolean courierAvailability)
    {
        this.courierAvailability = courierAvailability;
    }

    public Boolean getOwnedByJet()
    {
        return isOwnedByJet;
    }

    public void setOwnedByJet(Boolean ownedByJet)
    {
        isOwnedByJet = ownedByJet;
    }

    public String getOwnedByKioskCode()
    {
        return ownedByKioskCode;
    }

    public void setOwnedByKioskCode(String ownedByKioskCode)
    {
        this.ownedByKioskCode = ownedByKioskCode;
    }

    public String getProfilePictureUrl()
    {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl)
    {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getManagedBy()
    {
        return managedBy;
    }

    public void setManagedBy(String managedBy)
    {
        this.managedBy = managedBy;
    }

    public String getReferencedBy()
    {
        return referencedBy;
    }

    public void setReferencedBy(String referencedBy)
    {
        this.referencedBy = referencedBy;
    }

    public String getImei()
    {
        return imei;
    }

    public void setImei(String imei)
    {
        this.imei = imei;
    }

    public Double getRating()
    {
        return rating;
    }

    public void setRating(Double rating)
    {
        this.rating = rating;
    }

    public Integer getRatingCount()
    {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount)
    {
        this.ratingCount = ratingCount;
    }
}
