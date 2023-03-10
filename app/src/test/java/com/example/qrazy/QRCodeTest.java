package com.example.qrazy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QRCodeTest {
    @Test
    public void QRCode_isCorrect() {
        QRCode testQR = new QRCode("BFG5DGW54\n");
        assertEquals("696ce4dbd7bb57cbfe58b64f530f428b74999cb37e2ee60980490cd9552de3a6", testQR.calculateHash());
        assertEquals(115, testQR.getScore());
    }
}
