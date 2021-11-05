package com.example.antenatalcareapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.Doctor.AddHealthyTips;
import com.example.antenatalcareapp.Doctor.DocDashboard;
import com.example.antenatalcareapp.Doctor.MyTips;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class NewVisit extends Fragment {

    EditText u_phone, u_exam,u_diagnosis,u_treatment;
    String phone, exam,diagnosis,treatment,getId;
    SessionManager sessionManager;
    Button button;
    Urls urls;


    // TODO: Rename and change types and number of parameters
    public static NewVisit newInstance(String param1, String param2) {
        NewVisit fragment = new NewVisit();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public NewVisit() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_visit, container, false);
        sessionManager = new SessionManager(getActivity());
        urls = new Urls();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        phone = user.get(SessionManager.CONTACT);

        u_phone = view.findViewById(R.id.textView4);
        u_exam = view.findViewById(R.id.textView5);
        u_diagnosis = view.findViewById(R.id.textView6);
        u_treatment = view.findViewById(R.id.textView7);
        button =view.findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {

    public void onClick (View view) {
        Toast.makeText(getActivity(), "call", Toast.LENGTH_SHORT).show();
        final String phone = u_phone.getText().toString();
        final String exam = u_exam.getText().toString();
        final String diagnosis = u_diagnosis.getText().toString();
        final String treatment = u_treatment.getText().toString();
        if (phone.isEmpty() || exam.isEmpty()) {
            Toast.makeText(getActivity(), "all fields are required", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    getActivity());
            alertDialog2.setTitle("Confirm to Add New Visit");
            alertDialog2.setMessage("Make sure you double check entered info");
            alertDialog2.setIcon(R.drawable.ic_warning);
            alertDialog2.setPositiveButton("YES",
                    (dialog, which) -> PostNewVisit(phone, exam, diagnosis,treatment));
            alertDialog2.setNegativeButton("NO",
                    (dialog, which) -> dialog.cancel());
            alertDialog2.show();

        }
    }

        });
        return view;

    }



    private void PostNewVisit(String phone, String exam, String diagnosis,String treatment) {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please wait...");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.New_Visit,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");
                        if (success.equals("1")) {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Health Tips Added", Toast.LENGTH_LONG).show();
//                            Intent list = new Intent(getActivity(), MyTips.class);
//                            startActivity(list);
                        } else {
                            Toast.makeText(getActivity(), "Error, please try3 again ", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Not successful, please try1 again " + e.getMessage(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }, error -> {
            Toast.makeText(getActivity(), "Not successful, please check your network and try again ", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phone);
                params.put("exam", exam);
                params.put("diagnosis", diagnosis);
                params.put("treatment", treatment);

                return params;

                //see the name from php file and much them
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    }

