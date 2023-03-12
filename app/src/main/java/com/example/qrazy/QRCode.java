package com.example.qrazy;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to represent qr codes
 */
public class QRCode implements Parcelable {

    private String label;

    /**
     * Constructor method
     */
    public QRCode(String label) {
        this.label = label;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int number) {}

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }



}
