package com.example.qrazy;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private String username;
    private String deviceInfo;
    private String contact;
    private ArrayList<QRCode> QRArray = new ArrayList<>();

    public Player () {}
    // not needed for halfway:
    // private *Image* pfp;
    // dont know what the img is stored as
    public Player(String username, String deviceInfo) {
        this.username = username;
        this.deviceInfo = deviceInfo;
    }

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
    public ArrayList<QRCode> getQRArray() {
        return QRArray;
    }
    // setters
    private void setQRArray(ArrayList<QRCode> QRArray) {
        this.QRArray = QRArray;
    }

}