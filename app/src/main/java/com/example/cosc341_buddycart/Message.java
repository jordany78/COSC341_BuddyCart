package com.example.cosc341_buddycart;

import com.google.android.gms.maps.model.LatLng;

public class Message {
    private String message, senderId, senderName;
    private double buddyLat, buddyLng, remoteLat, remoteLng;

    private long timestamp;

    public Message() {
    }

    public Message(String message, String senderId, String senderName, long timestamp) {
        this.message = message;
        this.senderId = senderId;
        this.senderName = senderName;
        this.timestamp = timestamp;
    }

    public Message(String message, String senderId, String senderName, long timestamp, double buddyLat, double buddyLng, double remoteLat, double remoteLng) {
        this.message = message;
        this.senderId = senderId;
        this.senderName = senderName;
        this.timestamp = timestamp;
        this.buddyLat = buddyLat;
        this.buddyLng = buddyLng;
        this.remoteLat = remoteLat;
        this.remoteLng = remoteLng;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getBuddyLat() {return buddyLat;}

    public void setBuddyLat(double buddyLat) { this.buddyLat = buddyLat;}

    public double getBuddyLng() {return buddyLng;}

    public void setBuddyLng(double buddyLng) {this.buddyLng = buddyLng;}

    public double getRemoteLat() {return remoteLat;}

    public void setRemoteLat(double remoteLat) { this.remoteLat = remoteLat;}

    public double getRemoteLng() {return remoteLng;}

    public void setRemoteLng(double remoteLng) { this.remoteLng = remoteLng;}
}
