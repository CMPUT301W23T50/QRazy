package com.example.qrazy;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * Class to represent qr codes
 */
public class QRCode implements Parcelable {
    //TODO<- Controller for comments and db connectivity
    private String hash;
    private String name;

    private int score = 0;
    // initializing to default values, so we don't have to check if the location contains null
    private Pair<Double, Double> location = new Pair<Double, Double>(0.0,0.0);  // <longitude, latitude>
    private String visualRep;
    private HashMap<String, String> comments = new HashMap<>();  // <userID, comment>


    // constructor

    public QRCode(String hash) {
        this.hash = hash;
        this.score = calculateScore();
        this.visualRep = generateVisualRep();
    }

    protected QRCode(Parcel in) {
        hash = in.readString();
        name = in.readString();
        score = in.readInt();
        visualRep = in.readString();
    }

    public static final Creator<QRCode> CREATOR = new Creator<QRCode>() {
        @Override
        public QRCode createFromParcel(Parcel in) {
            return new QRCode(in);
        }

        @Override
        public QRCode[] newArray(int size) {
            return new QRCode[size];
        }
    };

    /** Calculates the score from the hash value
     * @return score int
     */
    public int calculateScore() {

        int score = 0;
        // regex global search to get substrs of repeating chars AND all zeroes
        Pattern repeat = Pattern.compile("(.)(\\1+)|(0)");
        Matcher matcher = repeat.matcher(hash);
        while(matcher.find()) {
            String match = matcher.group();
            // determine val
            char ch = match.charAt(0);
            int charVal;
            if (ch == '0') {
                if (match.length() == 1) {
                    // non repeating zero
                    score+=1;
                    continue;
                }
                else {charVal = 20;}
            }
            else if (Character.isDigit(ch)) {
                // 0-9 ascii vals are in order
                charVal = ch-'0';
            }
            else {
                // a-z ascii vals are in order
                // +10 for hex->dec (a=10)
                charVal = ch-'a'+10;
            }
            score += Math.pow(charVal,(match.length()-1));
        }
        return score;
    }

    /** Generate a unique visual representation of the qr code
     * @return String representation of qr as a string
     */
    public String generateVisualRep() {
        // for the unique visual representation
        // TODO: implement after halfway
        return "";
    }

    // getters
    public String getHash() {
        return hash;
    }
    public int getScore() {
        return score;
    }
    public String getVisualRep() {
        return visualRep;
    }
    // setters
    public void setScore(int score) {
        this.score = score;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Pair<Double, Double> getLocation() { return location; }

    private void setVisualRep(String visualRep) {
        this.visualRep = visualRep;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(hash);
        parcel.writeString(name);
        parcel.writeInt(score);
        parcel.writeString(visualRep);
    }
}
