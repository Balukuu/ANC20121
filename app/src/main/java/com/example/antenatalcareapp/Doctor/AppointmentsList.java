package com.example.antenatalcareapp.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.Adapters.AppointmentsAdapter;
import com.example.antenatalcareapp.Adapters.CentersAdapter;
import com.example.antenatalcareapp.Adapters.MothersAdapter;
import com.example.antenatalcareapp.MainActivity;
import com.example.antenatalcareapp.Models.AppointmentsModel;
import com.example.antenatalcareapp.Models.CentersModel;
import com.example.antenatalcareapp.Models.MothersModel;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.SessionManager;
import com.example.antenatalcareapp.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentsList extends AppCompatActivity {
    List<AppointmentsModel> mData;
    Urls urls;
    RecyclerView recyclerView;
    String getId,contact;
    SessionManager sessionManager;
    AppointmentsAdapter adapter;
    TextView error;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments_list);
        urls = new Urls();
        sessionManager = new SessionManager(this);
        error = findViewById(R.id.error);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);

        //handle recyclerview
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(AppointmentsList.this));

        mData = new ArrayList<>();
        adapter = new AppointmentsAdapter(AppointmentsList.this, mData);
        loadCenters();
        editText = findViewById(R.id.search);
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
        final ProgressDialog progressDialog = new ProgressDialog(this);
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

                        adapter = new AppointmentsAdapter(AppointmentsList.this, mData);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        error.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Error Reading Details" + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error Reading Details" + error.toString(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("contact", contact);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void goBack(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}