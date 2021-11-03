package com.example.antenatalcareapp.Models;

public class CentersModel {
    String id, referral, address, officer, contact;

    public CentersModel() {
    }

    public CentersModel(String id, String referral, String address, String officer, String contact) {
        this.id = id;
        this.referral = referral;
        this.address = address;
        this.officer = officer;
        this.contact = contact;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOfficer() {
        return officer;
    }

    public void setOfficer(String officer) {
        this.officer = officer;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
