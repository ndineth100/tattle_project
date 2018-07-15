package com.ceyentra.tattle.model;


public class ChatMessage {
    int type;
    public int avatar;
    String address;
    String body;
    String date;
    int sender; // send 0 rece 1

    public ChatMessage() {
    }

    public ChatMessage(String address, String body) {
        this.address = address;
        this.body = body;
    }
    public ChatMessage(int type, String address, String body, String date, int avatar) {
        this.type = type;
        this.address = address;
        this.body = body;
        this.date = date;
        this.avatar = avatar;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
