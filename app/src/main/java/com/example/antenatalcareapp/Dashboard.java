package com.example.antenatalcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    public void openSettings(View view) {
    }

    public void openProfile(View view) {
    }

    public void openCalender(View view) {
    }

    public void openTips(View view) {
    }

    public void openMedicalCenters(View view) {
    }

    public void openMedicalPersonnel(View view) {
    }
}