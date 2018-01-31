package coid.moonlay.pickupondemand.jet.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import coid.moonlay.pickupondemand.jet.model.db.DBContract;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;

@Table(name = DBContract.OutletLocationEntry.TABLE_NAME)
public class OutletLocation extends Model
{
    @Column(name = DBContract.OutletLocationEntry.COLUMN_NAME)
    private String name;
    @Column(name = DBContract.OutletLocationEntry.COLUMN_ADDRESS)
    private String address;
    @Column(name = DBContract.OutletLocationEntry.COLUMN_DISTANCE)
    private String distance;
    @Column(name = DBContract.OutletLocationEntry.COLUMN_LATITUDE)
    private Double latitude;
    @Column(name = DBContract.OutletLocationEntry.COLUMN_LONGITUDE)
    private Double longitude;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getDistance()
    {
        return distance;
    }

    public void setDistance(String distance)
    {
        this.distance = distance;
    }

    public Double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(Double latitude)
    {
        this.latitude = latitude;
    }

    public Double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(Double longitude)
    {
        this.longitude = longitude;
    }

    public LatLng getLatLng()
    {
        return new LatLng(latitude, longitude);
    }

    public static List<OutletLocation> getMockOutletLocationList()
    {
        DBQuery.truncate(OutletLocation.class);

        OutletLocation outletLocation1 = new OutletLocation();
        outletLocation1.setName("Gerai Slipi Jaya");
        outletLocation1.setAddress("West Jakarta, Special Capital Region of Jakarta");
        outletLocation1.setDistance("3.6");
        outletLocation1.setLatitude(-6.189259);
        outletLocation1.setLongitude(106.796576);
        outletLocation1.save();
        OutletLocation outletLocation2 = new OutletLocation();
        outletLocation2.setName("Gerai Kebon Jeruk Utara");
        outletLocation2.setAddress("Kedoya Utara Kb. Jeruk Kota Jakarta Barat Daerah Khusus Ibukota Jakarta 11520");
        outletLocation2.setDistance("3.9");
        outletLocation2.setLatitude(-6.170232);
        outletLocation2.setLongitude(106.760707);
        outletLocation2.save();
        OutletLocation outletLocation3 = new OutletLocation();
        outletLocation3.setName("Gerai Royal Mediterania");
        outletLocation3.setAddress("Jl. Letjen. S. Parman Tj. Duren Sel. Grogol petamburan Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11470");
        outletLocation3.setDistance("5.8");
        outletLocation3.setLatitude(-6.1766089);
        outletLocation3.setLongitude(106.7878996);
        outletLocation3.save();
        OutletLocation outletLocation4 = new OutletLocation();
        outletLocation4.setName("Gerai Tomang Barat");
        outletLocation4.setAddress("Jl. Tanjung Duren Raya, RT.11/RW.2, Tj. Duren Utara, Grogol petamburan, Kota Jakarta Barat, Daerah Khusus Ibukota Jakarta 11470");
        outletLocation4.setDistance("2.1");
        outletLocation4.setLatitude(-6.1736957);
        outletLocation4.setLongitude(106.783062);
        outletLocation4.save();

        List<OutletLocation> mockOutletLocationList = new ArrayList<>();
        mockOutletLocationList.add(outletLocation1);
        mockOutletLocationList.add(outletLocation2);
        mockOutletLocationList.add(outletLocation3);
        mockOutletLocationList.add(outletLocation4);
        return mockOutletLocationList;
    }

    public static List<OutletLocation> getMockNearestThreeOutletLocation()
    {
        return new Select().from(OutletLocation.class).where("Id < 4").execute();
    }
}
