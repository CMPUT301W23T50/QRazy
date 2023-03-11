package com.example.qrazy;

import java.util.ArrayList;

public class OwnerID {
    private String ownerID;

    public ArrayList<UniqueUser> rejectedUsers = new ArrayList<UniqueUser>();

    public OwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    /**
     * function to get ownerid owner
     * @return
     *  return profile object of the individual who created the OwnerID
     */
    public String getOwnerID() {
        return this.ownerID;
    }

    public void addRejectedUser(UniqueUser user) {
        rejectedUsers.add(user);
    }

    public ArrayList<UniqueUser> getRejectedUsers() {
        return rejectedUsers;
    }


}
