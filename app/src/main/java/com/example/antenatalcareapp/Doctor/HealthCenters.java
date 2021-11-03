package com.example.antenatalcareapp.Doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.Adapters.CentersAdapter;
import com.example.antenatalcareapp.Adapters.MothersAdapter;
import com.example.antenatalcareapp.MainActivity;
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

public class HealthCenters extends AppCompatActivity {
    List<CentersModel> mData;
    Urls urls;
    RecyclerView recyclerView;
    String getId;
    SessionManager sessionManager;
    TextView error;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_centers);
        urls = new Urls();
        sessionManager = new SessionManager(this);
        error = findViewById(R.id.error);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);

        recyclerView = (RecyclerView) findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the ReportList
        mData = new ArrayList<>();
        loadCenters();
    }

    private void loadCenters() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.CENTERS_LIST,
                response -> {
                    progressDialog.dismiss();
                    Log.i("tagconvertstr", "[" + response + "]");
                    try {
                        JSONArray array = new JSONArray(response);

                        for (int i = 0; i < array.length(); i++) {

                            JSONObject list = array.getJSONObject(i);
                            mData.add(new CentersModel(
                                    list.getString("id"),
                                    list.getString("referral"),
                                    list.getString("address"),
                                    list.getString("officer"),
                                    list.getString("contact")
                            ));
                        }

                        CentersAdapter adapter = new CentersAdapter(HealthCenters.this, mData);
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
}