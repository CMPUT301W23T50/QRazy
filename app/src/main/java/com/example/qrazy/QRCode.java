package com.example.qrazy;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to represent qr codes
 * Implements Parcelable so that a QRCode can be passed between activities with intent.setExtra
 */
public class QRCode implements Parcelable {

    private String content;
    private String name;
    private int score = 0;
    // initializing to default values, so we don't have to check if the location contains null
    private Pair<Double, Double> location = new Pair<Double, Double>(0.0,0.0);  // <longitude, latitude>
    private HashMap<String, String> comments;  // <userID, comment>

    public QRCode(String content) {
        this.content = content;
    }

    protected QRCode(Parcel in) {
        this.content = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int number) {
        parcel.writeString(content);
    }

    /**
     * Function to update the score of this QR code
     * @return the new, updated score of this instance of a QR code
     */
    public int updateScore() {

        int score = 0;
        String hash = getHash();
        // regex global search for substrings of repeating chars
        Pattern repeat = Pattern.compile("(.)(\\1+)");
        Matcher matcher = repeat.matcher(hash);
        while(matcher.find()) {
            String match = matcher.group();
            // TODO <- map hex value to decimal; 0 to 20
            int char_val = 10;
            score += Math.pow(char_val,(match.length()-1));
        }
        // update and ret
        this.score = score;
        return score;
    }

    public String getHash() {
        return org.apache.commons.codec.digest.DigestUtils.sha256Hex(content);
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public String getName() { return name; }
    public Pair<Double, Double> getLocation() { return location; }
    public String getContent() { return content; }

}
