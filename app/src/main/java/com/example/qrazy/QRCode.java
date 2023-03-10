package com.example.qrazy;

import android.util.Log;
import android.util.Pair;
import java.util.HashMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Class to represent qr codes
 */
public class QRCode {
    private String content;
    private String name;
    private int score;
    private String visualRep;
    private Pair<Double, Double> location;  // <longitude, latitude>
    private HashMap<String, String> comments;  // <userID, comment>

    public QRCode(String content) {
        this.content = content;
        this.score = calculateScore();
        this.visualRep = generateVisualRep();
    }

    public int calculateScore() {
        int score = 0;
        String hash = calculateHash();
        // regex global search for substrings of repeating chars AND all zeroes
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

    public String calculateHash() {
        return DigestUtils.sha256Hex(content);
    }

    public String generateVisualRep() {
        // for the unique visual representation
        // TODO: implement after halfway
        return "";
    }

    // getters
    public String getContent() {
        return content;
    }
    public int getScore() {
        return score;
    }
    public String getVisualRep() {
        return visualRep;
    }
    // setters
    public void setContent(String content) {
        this.content = content;
    }
    public void setScore(int score) {
        this.score = score;
    }
    private void setVisualRep(String visualRep) {
        this.visualRep = visualRep;
    }
}
