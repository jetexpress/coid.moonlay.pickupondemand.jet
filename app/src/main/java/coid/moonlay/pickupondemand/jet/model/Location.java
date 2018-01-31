package coid.moonlay.pickupondemand.jet.model;

import coid.moonlay.pickupondemand.jet.base.IBaseItemSelectModel;

public class Location implements IBaseItemSelectModel
{
    private String code;
    private String display;
    private String branchCode;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getDisplay()
    {
        return display;
    }

    public void setDisplay(String display)
    {
        this.display = display;
    }

    public String getBranchCode()
    {
        return branchCode;
    }

    public void setBranchCode(String branchCode)
    {
        this.branchCode = branchCode;
    }

    @Override
    public CharSequence getItemSelectCode()
    {
        return this.code;
    }

    @Override
    public CharSequence getItemSelectDescription()
    {
        return this.display;
    }
}
