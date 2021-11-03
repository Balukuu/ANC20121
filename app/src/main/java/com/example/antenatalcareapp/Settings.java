package com.example.antenatalcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.antenatalcareapp.Mother.MyProfile;

public class Settings extends AppCompatActivity {
SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        sessionManager = new SessionManager(this);
    }
    public void MyProfile(View view) {
        startActivity(new Intent(getApplicationContext(), MyProfile.class));
        finish();

    }


    public void ShareApp(View view) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        share.putExtra(Intent.EXTRA_SUBJECT, "Antenatal Care App");
        share.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/");

        startActivity(Intent.createChooser(share, "link to download app!"));
    }

    public void AboutUs(View view) {
        startActivity(new Intent(getApplicationContext(), ABoutApp.class));
    }

    public void LogoutAccount(View view) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                Settings.this);
        alertDialog2.setTitle("Confirm To Remove Account");
        alertDialog2.setMessage("Are you sure to remove your account? Note that you will have to " +
                "sign back into your account to use app again");
        alertDialog2.setIcon(R.drawable.ic_warning);
        alertDialog2.setPositiveButton("YES",
                (dialog, which) -> {
                    sessionManager.logout();
                    finish();
                });
        alertDialog2.setNegativeButton("CANCEL",
                (dialog, which) -> dialog.cancel());
        alertDialog2.show();

    }
}