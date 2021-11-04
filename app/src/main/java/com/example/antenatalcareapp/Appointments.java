package com.example.antenatalcareapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.Adapters.AppointmentsAdapter;
import com.example.antenatalcareapp.Doctor.AddHealthyTips;
import com.example.antenatalcareapp.Doctor.AppointmentsList;
import com.example.antenatalcareapp.Doctor.CreateAppointments;
import com.example.antenatalcareapp.Models.AppointmentsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Appointments extends Fragment {

    List<AppointmentsModel> mData;
    Urls urls;
    RecyclerView recyclerView;
    String getId,contact;
    SessionManager sessionManager;
    AppointmentsAdapter adapter;
    TextView error;
    EditText editText;
    FloatingActionButton button;

    public Appointments() {
        // Required empty public constructor
    }


    public static Appointments newInstance(String param1, String param2) {
        Appointments fragment = new Appointments();
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
        View view= inflater.inflate(R.layout.fragment_appointments, container, false);

        urls = new Urls();
        sessionManager = new SessionManager(getActivity());
        error = view.findViewById(R.id.error);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);
        button = view.findViewById(R.id.floatingActionButton);
        button.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddHealthyTips.class)));

        //handle recyclerview
        recyclerView = view.findViewById(R.id.recylcerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mData = new ArrayList<>();
        adapter = new AppointmentsAdapter(getActivity(), mData);
        loadCenters();
        editText = view.findViewById(R.id.search);
        editText.setOnClickListener(v -> {
            editText.setFocusableInTouchMode(true);
            editText.setFocusable(true);
        });
        editText.setFocusableInTouchMode(false);
        editText.setFocusable(false);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        return view;
    }
    private void filter(String text) {
        ArrayList<AppointmentsModel> filteredList = new ArrayList<>();

        for (AppointmentsModel name : mData) {
            if (name.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(name);
            }
        }

        adapter.filterList(filteredList);
    }


    private void loadCenters() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.APPOINTMENTS_LIST,
                response -> {
                    progressDialog.dismiss();
                    Log.i("tagconvertstr", "[" + response + "]");
                    try {
                        JSONArray array = new JSONArray(response);

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject list = array.getJSONObject(i);
                            mData.add(new AppointmentsModel(
                                    list.getString("id"),
                                    list.getString("names"),
                                    list.getString("contact"),
                                    list.getString("appoint_date"),
                                    list.getString("appoint_time"),
                                    list.getString("appoint_status"),
                                    list.getString("comments")
                            ));
                        }

                        adapter = new AppointmentsAdapter(getActivity(), mData);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        error.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Error Reading Details" + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Error Reading Details" + error.toString(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("contact", contact);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void goBack(View view) {
        startActivity(new Intent(getActivity(), MainActivity.class));

    }
    public void open(View view) {
        view.findViewById(R.id.floatingActionButton);
        startActivity(new Intent(getActivity(), CreateAppointments.class));

    }
    
}
