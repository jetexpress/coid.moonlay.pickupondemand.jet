package coid.moonlay.pickupondemand.jet.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import coid.moonlay.pickupondemand.jet.JetApplication;
import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.base.IBaseItemSelectModel;
import coid.moonlay.pickupondemand.jet.model.db.DBContract;

@Table(name = DBContract.PackagingItemEntry.TABLE_NAME)
public class PackagingItem extends Model implements IBaseItemSelectModel
{
    @Column(name = DBContract.PackagingItemEntry.COLUMN_CODE)
    private String code;
    @Column(name = DBContract.PackagingItemEntry.COLUMN_NAME)
    private String name;
    @Column(name = DBContract.PackagingItemEntry.COLUMN_DESCRIPTION)
    private String description;

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
        return hasPackaging() ? this.name : Utility.Message.get(R.string.pickup_item_default_packaging);
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

    public Boolean hasPackaging()
    {
        return code != null && !code.isEmpty();
    }

    public static PackagingItem getDefaultPackaging()
    {
        PackagingItem packagingItem = new PackagingItem();
        packagingItem.setCode("");
        packagingItem.setName(Utility.Message.get(R.string.pickup_item_default_packaging));
        return packagingItem;
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
