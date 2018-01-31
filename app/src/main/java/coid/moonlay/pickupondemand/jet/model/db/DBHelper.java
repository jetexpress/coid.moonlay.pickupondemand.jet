package coid.moonlay.pickupondemand.jet.model.db;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;

import java.util.List;

public class DBHelper
{
    public static<T extends Model> void bulkSave(List<T> modelList)
    {
        ActiveAndroid.beginTransaction();
        try
        {
            for (T model : modelList)
            {
                model.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        }
        finally
        {
            ActiveAndroid.endTransaction();
        }
    }
}
