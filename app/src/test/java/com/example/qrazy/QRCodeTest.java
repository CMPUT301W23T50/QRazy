package com.example.qrazy;

import static org.junit.Assert.assertEquals;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class QRCodeTest {
    @Test
    public void QRCode_isCorrect() {
        HashMap<String, Integer> inOut = new HashMap<String, Integer>();
        inOut.put("696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6", 115);
        inOut.put("b34276e1a90c26252f1571683e875e8db7a4c48ab4389fd8627ab62b93cb3cec", 1);
        inOut.put("f33c22ddasdasd61674b9b3b46bb69bc51d30a145a4a05b4c4e9be94384743378dcf5917", 34);

        for (String in: inOut.keySet()) {
            QRCode testQR = new QRCode(in);
            int out = inOut.get(in);
            assertEquals(out, testQR.getScore());
        }
    }
}
