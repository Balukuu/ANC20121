package com.example.antenatalcareapp.Models;

public class AppointmentsModel {
    String id, name, contact, date, time, status, added_date, comment;

    public AppointmentsModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AppointmentsModel(String id, String name, String contact,
                             String date, String time, String status, String comment) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.date = date;
        this.time = time;
        this.status = status;
        this.comment = comment;
    }
}
