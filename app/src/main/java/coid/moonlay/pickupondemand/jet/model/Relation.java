package coid.moonlay.pickupondemand.jet.model;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

import coid.moonlay.pickupondemand.jet.base.IBaseItemSelectModel;
import coid.moonlay.pickupondemand.jet.model.db.DBContract;

@Table(name = DBContract.RelationEntry.TABLE_NAME)
public class Relation extends Model implements IBaseItemSelectModel
{
    @Column(name = DBContract.RelationEntry.COLUMN_NAME)
    private String name;

    public Relation()
    {

    }

    public Relation(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public static void bulkSave(List<String> relationList)
    {
        try
        {
            ActiveAndroid.beginTransaction();

            for (String relationName : relationList)
            {
                Relation relation = new Relation(relationName);
                relation.save();
            }

            ActiveAndroid.setTransactionSuccessful();
        }
        finally
        {
            ActiveAndroid.endTransaction();
        }
    }

    @Override
    public CharSequence getItemSelectCode()
    {
        return this.name;
    }

    @Override
    public CharSequence getItemSelectDescription()
    {
        return this.name;
    }
}
