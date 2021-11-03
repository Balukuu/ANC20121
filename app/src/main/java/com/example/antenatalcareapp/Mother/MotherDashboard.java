package com.example.antenatalcareapp.Mother;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.antenatalcareapp.Doctor.DocDashboard;
import com.example.antenatalcareapp.Doctor.HealthCenters;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.RegisterActivity;
import com.example.antenatalcareapp.SessionManager;
import com.example.antenatalcareapp.Settings;

import java.util.HashMap;

public class MotherDashboard extends AppCompatActivity {
    private static final String TAG = MotherDashboard.class.getSimpleName();
    TextView name;
    SessionManager sessionManager;
    String getId, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mother_dasboard);
        name = findViewById(R.id.names);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);
        name.setText("Mrs. " + user.get(SessionManager.FULLNAME));
    }

    public void openSettings(View view) {
        startActivity(new Intent(getApplicationContext(), Settings.class));
        finish();
    }

    public void openProfile(View view) {
        startActivity(new Intent(getApplicationContext(), MyProfile.class));
        finish();
    }

    public void openCalender(View view) {
        startActivity(new Intent(getApplicationContext(), MyAppointments.class));
        finish();
    }

    public void openTips(View view) {
        startActivity(new Intent(getApplicationContext(), HealthTips.class));
        finish();
    }

    public void openMedicalCenters(View view) {
        startActivity(new Intent(getApplicationContext(), CentersList.class));
        finish();
    }

    public void openMedicalPersonnel(View view) {
        startActivity(new Intent(getApplicationContext(), MedicalPersonals.class));
        finish();
    }

    public void LogOut(View view) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                MotherDashboard.this);
        alertDialog2.setTitle("Confirm to logout");
        alertDialog2.setMessage("Are you sure you want to sign out?");
        alertDialog2.setIcon(R.drawable.ic_warning);
        alertDialog2.setPositiveButton("YES",
                (dialog, which) -> {
                    SessionManager.MotherLogout();
                });
        alertDialog2.setNegativeButton("CANCEL",
                (dialog, which) -> dialog.cancel());
        alertDialog2.show();

    }
}