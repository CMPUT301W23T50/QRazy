package com.example.qrazy;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerController implements Parcelable {
    private Player player;

    public PlayerController(Player player) {
        this.player = player;
    }

    protected PlayerController(Parcel in) {
        player = in.readParcelable(Player.class.getClassLoader());
    }

    public static final Creator<PlayerController> CREATOR = new Creator<PlayerController>() {
        @Override
        public PlayerController createFromParcel(Parcel in) {
            return new PlayerController(in);
        }

        @Override
        public PlayerController[] newArray(int size) {
            return new PlayerController[size];
        }
    };

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

    public void removeQR(int index) {
        player.getQRArray().remove(index);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(player, i);
    }
}
