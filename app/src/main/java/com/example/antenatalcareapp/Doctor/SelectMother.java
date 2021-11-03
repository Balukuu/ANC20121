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
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.Adapters.MothersAdapter;
import com.example.antenatalcareapp.Adapters.SelectMotherAdapter;
import com.example.antenatalcareapp.MainActivity;
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

public class SelectMother extends AppCompatActivity {
    private Menu action;
    List<MothersModel> mData;
    Urls urls;
    RecyclerView recyclerView;
    String getId;
    SessionManager sessionManager;
    TextView error;
    EditText editText;
    SelectMotherAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mother);
        urls = new Urls();
        sessionManager = new SessionManager(this);
        error = findViewById(R.id.error);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);

        //handle recyclerview
        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(SelectMother.this));

        mData = new ArrayList<>();
        adapter = new SelectMotherAdapter(SelectMother.this, mData);
        loadMothers();

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
        ArrayList<MothersModel> filteredList = new ArrayList<>();

        for (MothersModel name : mData) {
            if (name.getNames().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(name);
            }
        }

        adapter.filterList(filteredList);
    }


    @Override
    protected void onResume() {
        super.onResume();
//        loadMothers();
    }


    private void loadMothers() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.MOTHERS_LIST,
                response -> {
                    progressDialog.dismiss();
                    Log.i("tagconvertstr", "[" + response + "]");
                    try {
                        JSONArray array = new JSONArray(response);

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject list = array.getJSONObject(i);
                            mData.add(new MothersModel(
                                    list.getString("id"),
                                    list.getString("patient_no"),
                                    list.getString("names"),
                                    list.getString("age"),
                                    list.getString("contact"),
                                    list.getString("occupation"),
                                    list.getString("address"),
                                    list.getString("religion"),
                                    list.getString("maritual_status"),
                                    list.getString("next_of_kin"),
                                    list.getString("other_address"),
                                    list.getString("date_added")
                            ));
                        }

                        adapter = new SelectMotherAdapter(SelectMother.this, mData);
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
                params.put("userId", getId);
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

    public void ShowList(View view) {
        startActivity(new Intent(getApplicationContext(), AppointmentsList.class));
        finish();
    }
}