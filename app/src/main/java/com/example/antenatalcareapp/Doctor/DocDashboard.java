package com.example.antenatalcareapp.Doctor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.antenatalcareapp.Chat;
import com.example.antenatalcareapp.Mother.MotherDashboard;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.SessionManager;

import java.util.HashMap;

public class DocDashboard extends AppCompatActivity {
    private static final String TAG = DocDashboard.class.getSimpleName();
    TextView name;
    SessionManager sessionManager;
    String getId, contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_dashboard);

        name =findViewById(R.id.names);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);
        name.setText("Dr. " +user.get(SessionManager.FULLNAME));
    }
    public void openSettings(View view) {
    }

    public void openProfile(View view) {
    }

    public void openCalender(View view) {
        startActivity(new Intent(getApplicationContext(), SelectMother.class));
        finish();
    }

    public void openTips(View view) {
        startActivity(new Intent(getApplicationContext(), AddHealthyTips.class));
        finish();
    }

    public void openMedicalCenters(View view) {
        startActivity(new Intent(getApplicationContext(), HealthCenters.class));
        finish();
    }

    public void openMedicalPersonnel(View view) {
    }

    public void openMothersList(View view) {
        startActivity(new Intent(getApplicationContext(), MothersList.class));
        finish();
    }

    public void openAddMothers(View view) {
        startActivity(new Intent(getApplicationContext(), AddMother.class));
        finish();
    }

    public void openChat(View view) {
        startActivity(new Intent(getApplicationContext(), MotherChatList.class));
        finish();
    }
    public void LogOut(View view) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                DocDashboard.this);
        alertDialog2.setTitle("Confirm to logout");
        alertDialog2.setMessage("Are you sure you want to sign out?");
        alertDialog2.setIcon(R.drawable.ic_warning);
        alertDialog2.setPositiveButton("YES",
                (dialog, which) -> {
                    SessionManager.DocLogout();
                });
        alertDialog2.setNegativeButton("CANCEL",
                (dialog, which) -> dialog.cancel());
        alertDialog2.show();

    }
}