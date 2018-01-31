package coid.moonlay.pickupondemand.jet.model;

public class FailedDelivery
{
    private String operationCode;
    private Boolean isRetry;
    private String note;

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
