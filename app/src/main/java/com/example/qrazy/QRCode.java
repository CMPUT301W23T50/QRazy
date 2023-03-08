package com.example.qrazy;

import android.util.Pair;
import java.util.HashMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
/**
 * Class to represent qr codes
 */
public class QRCode {
    private String content;
    private String name;
    private int score;
    private Pair<Double, Double> location;  // <longitude, latitude>
    private HashMap<String, String> comments;  // <userID, comment>

    public QRCode(String content) {
        this.content = content;
    }

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
}
