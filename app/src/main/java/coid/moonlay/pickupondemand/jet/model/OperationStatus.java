package coid.moonlay.pickupondemand.jet.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

import coid.moonlay.pickupondemand.jet.base.IBaseItemSelectModel;
import coid.moonlay.pickupondemand.jet.model.db.DBContract;

@Table(name = DBContract.OperationStatusEntry.TABLE_NAME)
public class OperationStatus extends Model implements IBaseItemSelectModel
{
    public static String CODE_SUCCESS = "EX-000";

    @Column(name = DBContract.OperationStatusEntry.COLUMN_CODE)
    private String code;
    @Column(name = DBContract.OperationStatusEntry.COLUMN_NAME)
    private String name;
    @Column(name = DBContract.OperationStatusEntry.COLUMN_DESCRIPTION)
    private String description;
    @Column(name = DBContract.OperationStatusEntry.COLUMN_IS_FAIL)
    private Boolean isFail;

    public OperationStatus()
    {

    }

    public OperationStatus(String code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Boolean getFail()
    {
        return isFail;
    }

    public void setFail(Boolean fail)
    {
        isFail = fail;
    }

    @Override
    public CharSequence getItemSelectCode()
    {
        return this.code;
    }

    @Override
    public CharSequence getItemSelectDescription()
    {
        return this.name;
    }
}
