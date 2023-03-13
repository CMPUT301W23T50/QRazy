package com.example.qrazy;


import android.util.Log;
import android.util.Pair;

import java.io.Serializable;
import java.util.HashMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.commons.codec.digest.DigestUtils;


/**
 * Class to represent qr codes
 */
public class QRCode implements Serializable {
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
    public Pair<Double, Double> getLocation() { return location; }
    public String getContent() { return content; }

    private void setVisualRep(String visualRep) {
        this.visualRep = visualRep;
    }


}
