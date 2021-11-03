package com.example.antenatalcareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.antenatalcareapp.Doctor.DocDashboard;
import com.example.antenatalcareapp.Mother.MotherDashboard;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    SessionManager sessionManager;
    String getId, contact, role, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);
        name = user.get(SessionManager.FULLNAME);
        role = user.get(SessionManager.ROLE);


        Intent intent;
        if (role!= null && role.equals("doctor")) {
            intent = new Intent(getApplicationContext(), MainActivity3.class);
        }
        else{
            intent = new Intent(getApplicationContext(), MainActivity2 .class);
        }
        startActivity(intent);
        finish();
    }
}