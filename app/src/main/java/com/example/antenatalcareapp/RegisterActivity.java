package com.example.antenatalcareapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    Spinner category;
    CardView reg, already;
    EditText fullname, phone, residence, subcounty, date_birth, parish;
    Urls urls;
    SessionManager sessionManager;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        category = (Spinner) findViewById(R.id.category);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        urls = new Urls();


        already = findViewById(R.id.button17);
        fullname = findViewById(R.id.fullname_regist);
        phone = findViewById(R.id.phone);
        residence = findViewById(R.id.residence);
        reg = findViewById(R.id.reg);
        subcounty = findViewById(R.id.subcounty);
        date_birth = findViewById(R.id.date_birth);
        parish = findViewById(R.id.parish);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        date = dateFormat.format(calendar.getTime());
        date_birth.setText(date);

        date_birth.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date =  day + "-" + month + "-" + year;
            date_birth.setText(date);
        };

        already.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });


        reg.setOnClickListener(v -> {
            String full_name_val = fullname.getText().toString().trim();
            final String pp = phone.getText().toString().trim();
            String ree = residence.getText().toString().trim();
            String ssuu = subcounty.getText().toString().trim();
            String parishs = parish.getText().toString().trim();
            String cat = category.getSelectedItem().toString().trim();

            if (!full_name_val.isEmpty() && !pp.isEmpty() && !ree.isEmpty()
                    && !ssuu.isEmpty() && !parishs.isEmpty() && !cat.equals("select account")) {


                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        RegisterActivity.this);
                alertDialog2.setTitle("Confirm to proceed to register");
                alertDialog2.setMessage("Make sure you double check your registration details");
                alertDialog2.setIcon(R.drawable.ic_warning);
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) -> {
                            Register();
                        });
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();

            } else {
                fullname.setError("Enter full_name");
                phone.setError("Enter phone number");
                residence.setError("Enter your current residence");
                subcounty.setError("Enter your sub county");

            }
        });
    }
    private void Register() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait ...");
        dialog.show();

        final String full_names = this.fullname.getText().toString().trim();
        final String phonne = this.phone.getText().toString().trim();
        final String resi = this.residence.getText().toString().trim();
        final String subb = this.subcounty.getText().toString().trim();
        final String dddd = this.date_birth.getText().toString().trim();
        final String ppp = this.parish.getText().toString().trim();
        String cat = this.category.getSelectedItem().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.FIRSTREG_URL,
                response -> {
                    try {

                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        if (success.equals("1")) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                            String uu = fullname.getText().toString().trim();
                            Intent aa = new Intent(RegisterActivity.this, LoginActivity.class);
                            aa.putExtra("fullname", uu);
                            startActivity(aa);
                            finish();


                        } else if (success.equals("2")) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Either Email address or contact or fullnames already taken, check and try again", Toast.LENGTH_SHORT).show();

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Registration was unsuccessful, please try again", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Registration continues to fail, please check your internet connection and try again" + error.toString(), Toast.LENGTH_SHORT).show();

                }
        ) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fullname", full_names);
                params.put("phone", phonne);
                params.put("residence", resi);
                params.put("subcounty", subb);
                params.put("date_birth", dddd);
                params.put("parish", ppp);
                params.put("account", cat);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void openLogin(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}