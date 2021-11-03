package com.example.antenatalcareapp.Models;

public class MothersModel {

        String id, patient_no, names, age, contact, occupation, address, religion, maritual_status, next_of_kin, other_address,date_added;

        public MothersModel() {
        }


        public String getId() {
                return id;
        }

        public void setId(String id) {
                this.id = id;
        }

        public String getPatient_no() {
                return patient_no;
        }


        public void setPatient_no(String patient_no) {
                this.patient_no = patient_no;
        }

        public String getNames() {
                return names;
        }

        public void setNames(String names) {
                this.names = names;
        }

        public String getAge() {
                return age;
        }

        public void setAge(String age) {
                this.age = age;
        }

        public String getContact() {
                return contact;
        }

        public void setContact(String contact) {
                this.contact = contact;
        }

        public String getOccupation() {
                return occupation;
        }

        public void setOccupation(String occupation) {
                this.occupation = occupation;
        }

        public String getAddress() {
                return address;
        }

        public void setAddress(String address) {
                this.address = address;
        }

        public String getReligion() {
                return religion;
        }

        public void setReligion(String religion) {
                this.religion = religion;
        }

        public String getMaritual_status() {
                return maritual_status;
        }

        public void setMaritual_status(String maritual_status) {
                this.maritual_status = maritual_status;
        }

        public String getNext_of_kin() {
                return next_of_kin;
        }

        public void setNext_of_kin(String next_of_kin) {
                this.next_of_kin = next_of_kin;
        }

        public String getOther_address() {
                return other_address;
        }

        public void setOther_address(String other_address) {
                this.other_address = other_address;
        }

        public String getDate_added() {
                return date_added;
        }

        public void setDate_added(String date_added) {
                this.date_added = date_added;
        }

        public MothersModel(String id, String patient_no, String names, String age, String contact, String occupation, String address, String religion,
                            String maritual_status, String next_of_kin, String other_address, String date_added) {
                this.id = id;
                this.patient_no = patient_no;
                this.names = names;
                this.age = age;
                this.contact = contact;
                this.occupation = occupation;
                this.address = address;
                this.religion = religion;
                this.maritual_status = maritual_status;
                this.next_of_kin = next_of_kin;
                this.other_address = other_address;
                this.date_added = date_added;
        }
}
