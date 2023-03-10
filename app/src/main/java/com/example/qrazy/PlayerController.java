package com.example.qrazy;

import java.util.ArrayList;

public class PlayerController {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    public void addQR(QRCode qr) {
        player.getQRArray().add(qr);
    }
    public int getTotalScore() {
        // could change the design to have total
        // score as an attr that updates every time
        // qr gets added/removed
        int total = 0;
        ArrayList<QRCode> qrArray= player.getQRArray();
        for (int i = 0; i<qrArray.size(); i++) {
            total+=qrArray.get(i).getScore();
        }
        return total;
    }
}
