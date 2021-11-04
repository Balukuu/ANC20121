package com.example.antenatalcareapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.antenatalcareapp.Doctor.DocDashboard;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class MainActivity3 extends AppCompatActivity {
    TextView name;
    SessionManager sessionManager;
    String getId, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        name =findViewById(R.id.names4);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);
        name.setText("Dr. " +user.get(SessionManager.FULLNAME));

        //Initialize Bottom Navigation View.
        BottomNavigationView navView = findViewById(R.id.bottomNav_view);

        //Pass the ID's of Different destinations
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_favourites, R.id.navigation_profile, R.id.navigation_search )
                .build();

        //Initialize NavController.
        NavController navController = Navigation.findNavController(this, R.id.navHostFragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
    public void LogOut(View view) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                MainActivity3.this);
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
