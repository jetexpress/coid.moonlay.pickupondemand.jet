package coid.moonlay.pickupondemand.jet.model;

import android.graphics.Bitmap;

/**
 * Created by jordan.leonardi on 3/5/2018.
 */

public class SuccessPRS {

    private Double Latitude;
    private Double Longitude;
    private String QuickPickupItemCount;
    private String ImageBase64;
    private String SignatureBase64;
    private String JetIDCode;

    public String getJetIDCode() {
        return JetIDCode;
    }

    public void setJetIDCode(String jetIDCode) {
        JetIDCode = jetIDCode;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getQuickPickupItemCount() {
        return QuickPickupItemCount;
    }

    public void setQuickPickupItemCount(String quickPickupItemCount) {
        QuickPickupItemCount = quickPickupItemCount;
    }

    public String getImageBase64() {
        return ImageBase64;
    }

    public void setImageBase64(String imageBase64) {
        ImageBase64 = imageBase64;
    }

    public String getSignatureBase64() {
        return SignatureBase64;
    }

    public void setSignatureBase64(String signatureBase64) {
        SignatureBase64 = signatureBase64;
    }


}
