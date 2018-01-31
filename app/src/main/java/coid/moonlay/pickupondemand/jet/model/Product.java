package coid.moonlay.pickupondemand.jet.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import coid.moonlay.pickupondemand.jet.base.IBaseItemSelectModel;
import coid.moonlay.pickupondemand.jet.model.db.DBContract;

@Table(name = DBContract.ProductEntry.TABLE_NAME)
public class Product extends Model implements IBaseItemSelectModel
{
    public static final String CODE_PRIORITY = "PRI";
    public static final String CODE_REGULAR = "REG";
    public static final String CODE_CARGO = "CRG";

    @Column(name = DBContract.ProductEntry.COLUMN_CODE)
    private String code;
    @Column(name = DBContract.ProductEntry.COLUMN_NAME)
    private String name;
    @Column(name = DBContract.ProductEntry.COLUMN_DESCRIPTION)
    private String description;
    @Column(name = DBContract.ProductEntry.COLUMN_LABEL)
    private String label;

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

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    @Override
    public CharSequence getItemSelectCode()
    {
        return this.code;
    }

    @Override
    public CharSequence getItemSelectDescription()
    {
        return this.code + " - " + this.name;
    }
}
