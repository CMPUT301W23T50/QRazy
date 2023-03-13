package com.example.qrazy;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to represent Player
 */
public class Player implements Parcelable {
    private String username;
    private String deviceInfo;
    private int highest = 0;
    private int lowest = 0;
    private String contact;
    private ArrayList<QRCode> QRArray = new ArrayList<>();

    private int total = 0;
    // not needed for halfway:
    // private *Image* pfp;
    // don't know what the img is stored as

    //constructor
    public Player(String username, String deviceInfo) {
        this.username = username;
        this.deviceInfo = deviceInfo;
    }

    protected Player(Parcel in) {
        username = in.readString();
        deviceInfo = in.readString();
        highest = in.readInt();
        lowest = in.readInt();
        contact = in.readString();
        QRArray = in.createTypedArrayList(QRCode.CREATOR);
        total = in.readInt();
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    /** Get the total number of qr codes scanned
     * @return int size of QRArray
     */
    public int getNumScanned() {return QRArray.size();}

    //getters
    public String getUsername() {
        return username;
    }
    public String getDeviceInfo() {
        return deviceInfo;
    }
    public String getContact() {
        return contact;
    }
    public int getHighest() {return highest;}
    public int getLowest() {return lowest;}
    public ArrayList<QRCode> getQRArray() {
        return QRArray;
    }
    public int getTotal() {return total;}
    // setters
    private void setQRArray(ArrayList<QRCode> QRArray) {
        this.QRArray = QRArray;
    }
    public void setHighest(int highest) {
        this.highest = highest;
    }
    public void setLowest(int lowest) {
        this.lowest = lowest;
    }
    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(deviceInfo);
        parcel.writeInt(highest);
        parcel.writeInt(lowest);
        parcel.writeString(contact);
        parcel.writeTypedList(QRArray);
        parcel.writeInt(total);
    }
}
