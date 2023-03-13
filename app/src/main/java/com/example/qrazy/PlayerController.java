package com.example.qrazy;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerController implements Serializable {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    /** Adds a QR code object to the player model;
     * updates highest, lowest and total score in the player model
     * @param qr QRCode object that needs to appended to QRArray
     */
    public void addQR(QRCode qr) {
        int qrScore = qr.getScore();
        // update lowest and highest
        if (player.getHighest() < qrScore) {
            player.setHighest(qrScore);
        }
        if (player.getLowest() > qrScore || player.getQRArray().isEmpty()) {
            player.setLowest(qrScore);
        }
        // update total
        player.setTotal(player.getTotal()+qrScore);
        player.getQRArray().add(qr);
    }

    // Calculates the total score and updates player object
    public int updateTotalScore() {
        int total = 0;
        ArrayList<QRCode> qrArray= player.getQRArray();
        for (int i = 0; i<qrArray.size(); i++) {
            total+=qrArray.get(i).getScore();
        }
        player.setTotal(total);
        return total;
    }

    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
}
