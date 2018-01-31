package coid.moonlay.pickupondemand.jet.model;

import coid.moonlay.pickupondemand.jet.Utility;

public class WaybillHandover
{
    private String wbNumber;
    private String handoverDate;
    private String receiverName;
    private String relation;
    private String handedOverBy;
    private String note;
    private String signatureBase64;

    public String getWbNumber()
    {
        return wbNumber;
    }

    public void setWbNumber(String wbNumber)
    {
        this.wbNumber = wbNumber;
    }

    public String getHandoverDate()
    {
        return handoverDate;
    }

    public void setHandoverDate(String handoverDate)
    {
        this.handoverDate = handoverDate;
    }

    public Long getHandoverDataInMillis()
    {
        return Utility.DateFormat.getMillisFromDateString(this.handoverDate, "yyyy-MM-dd'T'HH:mm:ss", true);
    }

    public String getFormattedHandoverDate()
    {
        return Utility.DateFormat.getDateStringFromMillis(getHandoverDataInMillis(), "EEEE, dd MMMM yyyy - HH.mm", false);
    }

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

    public String getHandedOverBy()
    {
        return handedOverBy;
    }

    public void setHandedOverBy(String handedOverBy)
    {
        this.handedOverBy = handedOverBy;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getSignatureBase64()
    {
        return signatureBase64;
    }

    public void setSignatureBase64(String signatureBase64)
    {
        this.signatureBase64 = signatureBase64;
    }
}
