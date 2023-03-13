package com.example.qrazy;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerController implements Serializable {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

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
    public int updateTotalScore() {
        int total = 0;
        ArrayList<QRCode> qrArray= player.getQRArray();
        for (int i = 0; i<qrArray.size(); i++) {
            total+=qrArray.get(i).getScore();
        }
        player.setTotal(total);
        return total;
    }
}
