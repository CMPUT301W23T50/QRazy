package com.example.qrazy;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class to represent Player
 */
public class Player implements Serializable {
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
}
