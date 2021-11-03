package com.example.antenatalcareapp.Models;

public class TipsModel {
    String id, title, details, added_by, date;

    public TipsModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAdded_by() {
        return added_by;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TipsModel(String id, String title, String details, String added_by, String date) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.added_by = added_by;
        this.date = date;
    }
}
