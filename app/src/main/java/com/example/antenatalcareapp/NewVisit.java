package com.example.antenatalcareapp;


import android.content.Intent;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.antenatalcareapp.Mother.MyProfile;



public class NewVisit extends Fragment {

    SessionManager sessionManager;
    TextView textView;




    // TODO: Rename and change types and number of parameters
    public static NewVisit newInstance(String param1, String param2) {
        NewVisit fragment = new NewVisit();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_new_visit, container, false);
        //super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        sessionManager = new SessionManager(getActivity());
        return view;
    }

    public void MyProfile(View view) {
        startActivity(new Intent(getActivity(), MyProfile.class));


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
        startActivity(new Intent(getActivity(), ABoutApp.class));
    }

    public void LogoutAccount(View view) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(getActivity());
        alertDialog2.setTitle("Confirm To Remove Account");
        alertDialog2.setMessage("Are you sure to remove your account? Note that you will have to " +
                "sign back into your account to use app again");
        alertDialog2.setIcon(R.drawable.ic_warning);
        alertDialog2.setPositiveButton("YES",
                (dialog, which) -> {
                    sessionManager.logout();
                });
        alertDialog2.setNegativeButton("CANCEL",
                (dialog, which) -> dialog.cancel());
        alertDialog2.show();

    }
}