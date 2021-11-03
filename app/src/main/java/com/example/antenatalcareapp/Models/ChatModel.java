package com.example.antenatalcareapp.Models;

public class ChatModel {
    String message, time, sender, receiver, r_name;

    public ChatModel() {
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public ChatModel(String message, String time, String sender, String receiver, String r_name) {
        this.message = message;
        this.time = time;
        this.sender = sender;
        this.receiver = receiver;
        this.r_name = r_name;
    }
}


