package com.example.antenatalcareapp.Doctor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.MainActivity;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.SessionManager;
import com.example.antenatalcareapp.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddNewVisit extends AppCompatActivity {
    EditText condn, u_exam,u_diagnosis,u_treatment;
    TextView  names, phone;
    String  getId,clinician,contact,id;
    SessionManager sessionManager;
    Button button;
    Urls urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_visit);
        sessionManager = new SessionManager(getApplicationContext());
        urls = new Urls();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);

        clinician =user.get(SessionManager.FULLNAME);
        names = findViewById(R.id.names);
        phone = findViewById(R.id.phone);

        condn = findViewById(R.id.textView4);
        u_exam = findViewById(R.id.textView5);
        u_diagnosis = findViewById(R.id.textView6);
        u_treatment = findViewById(R.id.textView7);


        id = getIntent().getStringExtra("id");
        names.setText(getIntent().getStringExtra("Mother_Name"));
        phone.setText(getIntent().getStringExtra("Phone_Number"));


        }
    public void goBack(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void NewVisited(View view) {

        final String condition = condn.getText().toString();
        final String exam = u_exam.getText().toString();
        final String diagnosis = u_diagnosis.getText().toString();
        final String treatment = u_treatment.getText().toString();
        if (condition.isEmpty() || exam.isEmpty()) {
            Toast.makeText(getApplicationContext(), "all fields are required", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    AddNewVisit.this);
            alertDialog2.setTitle("Confirm to Add New Visit");
            alertDialog2.setMessage("Make sure you double check entered info");
            alertDialog2.setIcon(R.drawable.ic_warning);
            alertDialog2.setPositiveButton("YES",
                    (dialog, which) -> PostNewVisit(condition, exam, diagnosis,treatment,clinician,contact));
            alertDialog2.setNegativeButton("NO",
                    (dialog, which) -> dialog.cancel());
            alertDialog2.show();

        }
    }


    private void PostNewVisit(String condition, String exam, String diagnosis,String treatment,
                              String clinician,String contact) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        final String name = names.getText().toString();
        final String phn = phone.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.New_Visit,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");
                        if (success.equals("1")) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Visit Details Added", Toast.LENGTH_LONG).show();
                      Intent list = new Intent(getApplicationContext(), MyTips.class);
                       startActivity(list);
                        } else {
                            Toast.makeText(getApplicationContext(), "Error, please try3 again ", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Not successful, please try1 again " + e.getMessage(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }, error -> {
            Toast.makeText(getApplicationContext(), "Not successful, please check your network and try again ", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("condition", condition);
                params.put("name", name);
                params.put("exam", exam);
                params.put("phone", phn);
                params.put("contact", contact);
                params.put("diagnosis", diagnosis);
                params.put("treatment", treatment);
                params.put("clinician", clinician);

                return params;

                //see the name from php file and much them
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}