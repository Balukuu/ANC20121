package com.example.antenatalcareapp.Doctor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.SessionManager;
import com.example.antenatalcareapp.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddHealthyTips extends AppCompatActivity {
    EditText u_title, u_body;
    String getId, contact;
    SessionManager sessionManager;
    Urls urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_healthy_tips);

        sessionManager = new SessionManager(getApplicationContext());
        urls = new Urls();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);

        u_body = findViewById(R.id.u_body);
        u_title = findViewById(R.id.u_title);

    }

    public void AddTips(View view) {
        final String title = u_title.getText().toString();
        final String desc = u_body.getText().toString();
        if (title.isEmpty() || desc.isEmpty()) {
            Toast.makeText(getApplicationContext(), "all fields are required", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    AddHealthyTips.this);
            alertDialog2.setTitle("Confirm to Add Tips");
            alertDialog2.setMessage("Make sure you double check entered info");
            alertDialog2.setIcon(R.drawable.ic_warning);
            alertDialog2.setPositiveButton("YES",
                    (dialog, which) -> PostTips(title, desc, contact));
            alertDialog2.setNegativeButton("NO",
                    (dialog, which) -> dialog.cancel());
            alertDialog2.show();

        }
    }

    private void PostTips(String title, String desc, String contact) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.ADD_TIPS_URL,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");
                        if (success.equals("1")) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Health Tips Added", Toast.LENGTH_LONG).show();
                            Intent list = new Intent(getApplicationContext(), MyTips.class);
                            startActivity(list);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error, please try again ", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Not successful, please try again " + e.getMessage(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }, error -> {
            Toast.makeText(getApplicationContext(), "Not successful, please check your network and try again ", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                params.put("details", desc);
                params.put("contact", contact);

                return params;

                //see the name from php file and much them
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void ShowList(View view) {
        startActivity(new Intent(getApplicationContext(), MyTips.class));
        finish();
    }
    public void goBack(View view) {
        startActivity(new Intent(getApplicationContext(), DocDashboard.class));
        finish();
    }
}