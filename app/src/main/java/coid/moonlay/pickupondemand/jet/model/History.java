package coid.moonlay.pickupondemand.jet.model;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.ArrayList;
import java.util.List;

import coid.moonlay.pickupondemand.jet.JetApplication;
import coid.moonlay.pickupondemand.jet.R;
import coid.moonlay.pickupondemand.jet.Utility;
import coid.moonlay.pickupondemand.jet.model.db.DBContract;
import coid.moonlay.pickupondemand.jet.model.db.DBQuery;

public class History
{

}

//@Table(name = DBContract.HistoryEntry.TABLE_NAME)
//public class History extends Model
//{
//    @Column(name = DBContract.HistoryEntry.COLUMN_DELIVERY_TYPE)
//    private String deliveryType;
//    @Column(name = DBContract.HistoryEntry.COLUMN_PICKUP_NUMBER)
//    private String pickupNumber;
//    @Column(name = DBContract.HistoryEntry.COLUMN_DATE)
//    private String date;
//    @Column(name = DBContract.HistoryEntry.COLUMN_STATUS)
//    private String status;
//    @Column(name = DBContract.HistoryEntry.COLUMN_LATITUDE)
//    private Double latitude;
//    @Column(name = DBContract.HistoryEntry.COLUMN_LONGITUDE)
//    private Double longitude;
//    @Column(name = DBContract.HistoryEntry.COLUMN_CUSTOMER_NAME)
//    private String customerName;
//    @Column(name = DBContract.HistoryEntry.COLUMN_CUSTOMER_PHONE_NUMBER)
//    private String customerPhoneNumber;
//    @Column(name = DBContract.HistoryEntry.COLUMN_CUSTOMER_ADDRESS)
//    private String customerAddress;
//    @Column(name = DBContract.HistoryEntry.COLUMN_WEIGHT_IN_KG)
//    private Double weightInKg;
//    @Column(name = DBContract.HistoryEntry.COLUMN_DIMENSION)
//    private String dimension;
//    @Column(name = DBContract.HistoryEntry.COLUMN_VOLUME)
//    private Double volume;
//    @Column(name = DBContract.HistoryEntry.COLUMN_PAYMENT_METHOD)
//    private String paymentMethod;
//    @Column(name = DBContract.HistoryEntry.COLUMN_PRICE)
//    private Double price;
//
//    private List<HistoryBooking> historyBookingList;
//
//
//    public String getDeliveryType()
//    {
//        return deliveryType;
//    }
//
//    public void setDeliveryType(String deliveryType)
//    {
//        this.deliveryType = deliveryType;
//    }
//
//    public String getPickupNumber()
//    {
//        return pickupNumber;
//    }
//
//    public void setPickupNumber(String pickupNumber)
//    {
//        this.pickupNumber = pickupNumber;
//    }
//
//    public String getDate()
//    {
//        return date;
//    }
//
//    public void setDate(String date)
//    {
//        this.date = date;
//    }
//
//    public String getStatus()
//    {
//        return status;
//    }
//
//    public void setStatus(String status)
//    {
//        this.status = status;
//    }
//
//    public Double getLatitude()
//    {
//        return latitude;
//    }
//
//    public void setLatitude(Double latitude)
//    {
//        this.latitude = latitude;
//    }
//
//    public Double getLongitude()
//    {
//        return longitude;
//    }
//
//    public void setLongitude(Double longitude)
//    {
//        this.longitude = longitude;
//    }
//
//    public String getCustomerName()
//    {
//        return customerName;
//    }
//
//    public void setCustomerName(String customerName)
//    {
//        this.customerName = customerName;
//    }
//
//    public String getCustomerPhoneNumber()
//    {
//        return customerPhoneNumber;
//    }
//
//    public void setCustomerPhoneNumber(String customerPhoneNumber)
//    {
//        this.customerPhoneNumber = customerPhoneNumber;
//    }
//
//    public String getCustomerAddress()
//    {
//        return customerAddress;
//    }
//
//    public void setCustomerAddress(String customerAddress)
//    {
//        this.customerAddress = customerAddress;
//    }
//
//    public Double getWeightInKg()
//    {
//        return weightInKg;
//    }
//
//    public void setWeightInKg(Double weightInKg)
//    {
//        this.weightInKg = weightInKg;
//    }
//
//    public String getDimension()
//    {
//        return dimension;
//    }
//
//    public void setDimension(String dimension)
//    {
//        this.dimension = dimension;
//    }
//
//    public Double getVolume()
//    {
//        return volume;
//    }
//
//    public void setVolume(Double volume)
//    {
//        this.volume = volume;
//    }
//
//    public String getPaymentMethod()
//    {
//        return paymentMethod;
//    }
//
//    public void setPaymentMethod(String paymentMethod)
//    {
//        this.paymentMethod = paymentMethod;
//    }
//
//    public Double getPrice()
//    {
//        return price;
//    }
//
//    public void setPrice(Double price)
//    {
//        this.price = price;
//    }
//
//    public List<HistoryBooking> getHistoryBookingList()
//    {
//        try
//        {
//            historyBookingList = getMany(HistoryBooking.class, DBContract.HistoryBookingEntry.FK_HISTORY_ID);
//        }
//        catch (Exception ex)
//        {
//            if (historyBookingList == null)
//                historyBookingList = new ArrayList<>();
//        }
//        return historyBookingList;
//    }
//
//    public void setHistoryBookingList(List<HistoryBooking> historyBookingList)
//    {
//        this.historyBookingList = historyBookingList;
//    }
//
//    public String getPickupNumberDetail()
//    {
//        if (isPickup())
//            return deliveryType + " no " + pickupNumber;
//        else
//            return deliveryType;
//    }
//
//    public String getWeightInKgString()
//    {
//        return String.valueOf(weightInKg) + " kg";
//    }
//
//    public SpannableString getSpannableVolumeString()
//    {
//        String volumeString = String.valueOf(volume) + " cm2";
//        SpannableString spannableVolumeString = new SpannableString(volumeString);
//        spannableVolumeString.setSpan(new SuperscriptSpan(), volumeString.length() - 1, volumeString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        spannableVolumeString.setSpan(new RelativeSizeSpan(0.5f), volumeString.length() - 1, volumeString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        return spannableVolumeString;
//    }
//
//    public String getPriceString()
//    {
//        return "Rp." + Utility.NumberFormat.doubleToString(price, 0);
//    }
//
//    public Boolean isPickup()
//    {
//        return pickupNumber != null;
//    }
//
//    public Drawable getTypeDrawable()
//    {
//        if (isPickup())
//            return ContextCompat.getDrawable(JetApplication.getContext(), R.drawable.ic_pickup);
//        else
//            return ContextCompat.getDrawable(JetApplication.getContext(), R.drawable.ic_delivery);
//    }
//
//    public static List<History> getMockupHistoryList()
//    {
//        DBQuery.truncate(History.class);
//
//        History history1 = new History();
//        history1.setDeliveryType("Pick up");
//        history1.setPickupNumber("2033478695");
//        history1.setDate("Kamis, 15 September 2016 - 12.29");
//        history1.setStatus("Delivered");
//        history1.setLatitude(0d);
//        history1.setLongitude(0d);
//        history1.setCustomerName("Patricia Aviani");
//        history1.setCustomerPhoneNumber("+62 819 1982 9879");
//        history1.setCustomerAddress("Equity Tower 25TH floor, SCBD lot 9, Jl. Jend. Sudirman kav.52 - 53, 12190");
//        history1.setWeightInKg(1d);
//        history1.setDimension("11 x 9 x 15 (p x l x t)");
//        history1.setVolume(1485d);
//        history1.setPaymentMethod("Credit");
//        history1.setPrice(500000d);
//        history1.save();
//        List<HistoryBooking> historyBookingList1 = new ArrayList<>();
//        historyBookingList1.add(new HistoryBooking("1111111111111"));
//        historyBookingList1.add(new HistoryBooking("1111111111112"));
//        historyBookingList1.add(new HistoryBooking("1111111111113"));
//        historyBookingList1.add(new HistoryBooking("1111111111114"));
//        for (HistoryBooking item : historyBookingList1)
//        {
//            item.setHistory(history1);
//            item.save();
//        }
//
//        History history2 = new History();
//        history2.setDeliveryType("Delivery");
//        history2.setDate("Kamis, 15 September 2016 - 19.28");
//        history2.setStatus("Delivered");
//        history2.setLatitude(0d);
//        history2.setLongitude(0d);
//        history2.setCustomerName("Patricia Aviani");
//        history2.setCustomerPhoneNumber("+62 819 1982 9879");
//        history2.setCustomerAddress("Equity Tower 25TH floor, SCBD lot 9, Jl. Jend. Sudirman kav.52 - 53, 12190");
//        history2.setWeightInKg(2d);
//        history2.setDimension("2 x 2 x 15 (p x l x t)");
//        history2.setVolume(2485d);
//        history2.setPaymentMethod("Cash On Delivery");
//        history2.setPrice(1000000d);
//        history2.save();
//        List<HistoryBooking> historyBookingList2 = new ArrayList<>();
//        historyBookingList2.add(new HistoryBooking("2222222222221"));
//        historyBookingList2.add(new HistoryBooking("2222222222222"));
//        historyBookingList2.add(new HistoryBooking("2222222222223"));
//        historyBookingList2.add(new HistoryBooking("2222222222224"));
//        for (HistoryBooking item : historyBookingList2)
//        {
//            item.setHistory(history2);
//            item.save();
//        }
//
//        History history3 = new History();
//        history3.setDeliveryType("Pick up");
//        history3.setPickupNumber("2033478696");
//        history3.setDate("Rabu, 14 September 2016 - 22.03");
//        history3.setStatus("Picked up");
//        history3.setLatitude(0d);
//        history3.setLongitude(0d);
//        history3.setCustomerName("Patricia Aviani");
//        history3.setCustomerPhoneNumber("+62 819 1982 9879");
//        history3.setCustomerAddress("Equity Tower 25TH floor, SCBD lot 9, Jl. Jend. Sudirman kav.52 - 53, 12190");
//        history3.setWeightInKg(3d);
//        history3.setDimension("3 x 3 x 15 (p x l x t)");
//        history3.setVolume(3485d);
//        history3.setPaymentMethod("Cash On Delivery");
//        history3.setPrice(1500000d);
//        history3.save();
//        List<HistoryBooking> historyBookingList3 = new ArrayList<>();
//        historyBookingList3.add(new HistoryBooking("3333333333331"));
//        historyBookingList3.add(new HistoryBooking("3333333333332"));
//        historyBookingList3.add(new HistoryBooking("3333333333333"));
//        historyBookingList3.add(new HistoryBooking("3333333333334"));
//        for (HistoryBooking item : historyBookingList3)
//        {
//            item.setHistory(history3);
//            item.save();
//        }
//
//        History history4 = new History();
//        history4.setDeliveryType("Pick up");
//        history4.setPickupNumber("2033478697");
//        history4.setDate("Rabu, 14 September 2016 - 19.48");
//        history4.setStatus("Picked up");
//        history4.setLatitude(0d);
//        history4.setLongitude(0d);
//        history4.setCustomerName("Patricia Aviani");
//        history4.setCustomerPhoneNumber("+62 819 1982 9879");
//        history4.setCustomerAddress("Equity Tower 25TH floor, SCBD lot 9, Jl. Jend. Sudirman kav.52 - 53, 12190");
//        history4.setWeightInKg(4d);
//        history4.setDimension("4 x 4 x 15 (p x l x t)");
//        history4.setVolume(485d);
//        history4.setPaymentMethod("Gratis");
//        history4.setPrice(2000000d);
//        history4.save();
//        List<HistoryBooking> historyBookingList4 = new ArrayList<>();
//        historyBookingList4.add(new HistoryBooking("4444444444441"));
//        historyBookingList4.add(new HistoryBooking("4444444444442"));
//        historyBookingList4.add(new HistoryBooking("4444444444443"));
//        historyBookingList4.add(new HistoryBooking("4444444444444"));
//        for (HistoryBooking item : historyBookingList4)
//        {
//            item.setHistory(history4);
//            item.save();
//        }
//
//        History history5 = new History();
//        history5.setDeliveryType("Delivery");
//        history5.setDate("Selasa, 13 September 2016 - 16.22");
//        history5.setStatus("Delivered");
//        history5.setLatitude(0d);
//        history5.setLongitude(0d);
//        history5.setCustomerName("Patricia Aviani");
//        history5.setCustomerPhoneNumber("+62 819 1982 9879");
//        history5.setCustomerAddress("Equity Tower 25TH floor, SCBD lot 9, Jl. Jend. Sudirman kav.52 - 53, 12190");
//        history5.setWeightInKg(5d);
//        history5.setDimension("5 x 5 x 15 (p x l x t)");
//        history5.setVolume(585d);
//        history5.setPaymentMethod("Cash On Delivery");
//        history5.setPrice(2500000d);
//        history5.save();
//        List<HistoryBooking> historyBookingList5 = new ArrayList<>();
//        historyBookingList5.add(new HistoryBooking("5555555555551"));
//        historyBookingList5.add(new HistoryBooking("5555555555552"));
//        historyBookingList5.add(new HistoryBooking("5555555555553"));
//        historyBookingList5.add(new HistoryBooking("5555555555554"));
//        for (HistoryBooking item : historyBookingList5)
//        {
//            item.setHistory(history5);
//            item.save();
//        }
//
//        List<History> mockHistoryList = DBQuery.getAll(History.class);
//        for (History history : mockHistoryList)
//        {
//            history.getHistoryBookingList();
//        }
//
//        return mockHistoryList;
//    }
//}
