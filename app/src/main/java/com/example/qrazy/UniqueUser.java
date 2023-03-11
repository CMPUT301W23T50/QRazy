package com.example.qrazy;

import java.io.Serializable;
import java.util.ArrayList;

public class UniqueUser implements Serializable {
    private String ID;
    private Player player;
    private String username;
    private ArrayList<OwnerID> aUser = new ArrayList<OwnerID>();
    private ArrayList<OwnerID> bUser =  new ArrayList<OwnerID>();
    public UniqueUser() {}
    /**
     * constructor function for UniqueUser object
     * @param ID
     * @param player
     */
    public UniqueUser(String ID, Player player) {
        this.ID = ID;
        this.player = player;
        this.aUser = new ArrayList<OwnerID>();
        this.bUser = new ArrayList<OwnerID>();
    }
    /**
     * get UniqueUser ID
     * @return
     * returns the UniqueUser ID
     */
    public String getId() { return ID; }

    public void setID(String ID) {
        this.ID = ID;
    }

    /**
    * set the player of the uniqueuser
    * @param player
    */
    public void setPlayer(Player player) {
        this.player = player;
    }
    /**
     * get the player of the uniqueuser
     * @return
     *  returns a player object
     */
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


