package com.example.qrazy;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * UniqueUser is a class which represents a user of the app
 * This class also currently contains information that will be stored in a database in the future
 */
public class UniqueUser implements Serializable {
    private String ID;
    private Player player;
    private String username;
    private ArrayList<OwnerID> aUser = new ArrayList<>();
    private ArrayList<OwnerID> bUser = new ArrayList<>();
    public UniqueUser() {}

    /**
     * Constructor function for UniqueUser object
     * @param ID player ID
     * @param player player object
     */
    public UniqueUser(String ID, Player player) {
        this.ID = ID;
        this.player = player;
        this.aUser = new ArrayList<OwnerID>();
        this.bUser = new ArrayList<OwnerID>();
    }

    public String getId() { return ID; }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }
    /**
     * function to subscribe user to an experiment
     * @param ownerid
     */
    public void setaUser(OwnerID ownerid) {
        this.aUser.add(ownerid);
    }
    public ArrayList<OwnerID> getaUser() {
        return aUser;
    }
    public void addaUser(OwnerID id) {
        aUser.add(id);
    }
    public ArrayList<OwnerID> getbUser() {
        return bUser;
    }
    public void addbUser(OwnerID id) {
        bUser.add(id);
    }


}


