package coid.moonlay.pickupondemand.jet.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import coid.moonlay.pickupondemand.jet.model.db.DBContract;

@Table(name = DBContract.ConfigEntry.TABLE_NAME)
public class Config extends Model
{
    @SerializedName("id")
    @Column(name = DBContract.ConfigEntry.COLUMN_CONFIG_ID)
    private long configId;
    @Column(name = DBContract.ConfigEntry.COLUMN_WAITING_TIME)
    private long waitingTime;

    public long getConfigId()
    {
        return configId;
    }

    public void setConfigId(long configId)
    {
        this.configId = configId;
    }

    public long getWaitingTime()
    {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime)
    {
        this.waitingTime = waitingTime;
    }
}
