package com.example.antenatalcareapp.Doctor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CreateAppointments extends AppCompatActivity {
    private int year, month, day, hour, minute;
    TextView tv_date, tv_time, names, phone;
    Button date, time;
    EditText comment;
    String getId, contact, id;
    SessionManager sessionManager;
    Urls urls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointments);

        sessionManager = new SessionManager(getApplicationContext());
        urls = new Urls();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);

        tv_date = findViewById(R.id.date);
        tv_time = findViewById(R.id.time);
        date = findViewById(R.id.datepicker);
        time = findViewById(R.id.timepicker);
        comment = findViewById(R.id.comment);
        names = findViewById(R.id.names);
        phone = findViewById(R.id.phone);

        id = getIntent().getStringExtra("id");
        names.setText(getIntent().getStringExtra("names"));
        phone.setText(getIntent().getStringExtra("phone"));

        date.setOnClickListener(view -> {

            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog picker = new DatePickerDialog(CreateAppointments.this, new DatePickerDialog.OnDateSetListener() {

                @SuppressLint("ResourceAsColor")
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    tv_date.setText(day+"-"+(month+1)+"-"+year);
                    tv_date.setTextColor(R.color.red);
                    tv_date.setTextSize(18);
                }
            }, year, month,day);
            picker.show();
        });

        time.setOnClickListener(view -> {

            final Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR_OF_DAY);
            minute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(CreateAppointments.this, new TimePickerDialog.OnTimeSetListener()
            {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int min) {
                    tv_time.setText(hour+":"+min);
                    tv_time.setTextColor(R.color.red);
                    tv_time.setTextSize(18);
                }
            },hour,minute,false);
            timePickerDialog.show();
        });
    }

    public void goBack(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public void CreateAppointment(View view) {
        final String dt = tv_date.getText().toString();
        final String tm = tv_time.getText().toString();
        final String comm = comment.getText().toString().trim();
        if (comm.isEmpty() || tm.isEmpty() || dt.isEmpty()) {
            Toast.makeText(getApplicationContext(), "all fields are required", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    CreateAppointments.this);
            alertDialog2.setTitle("Confirm to Create Appointment");
            alertDialog2.setMessage("Make sure you double check entered info");
            alertDialog2.setIcon(R.drawable.ic_warning);
            alertDialog2.setPositiveButton("YES",
                    (dialog, which) -> CreateNow(dt, tm, comm, contact));
            alertDialog2.setNegativeButton("NO",
                    (dialog, which) -> dialog.cancel());
            alertDialog2.show();

        }
    }

    private void CreateNow(String dt, String tm, String comm, String contact)  {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        final String name = names.getText().toString();
        final String phn = phone.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.CREATE_APPOINTMENTS_URL,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");
                        if (success.equals("1")) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Appointment created", Toast.LENGTH_LONG).show();
                            Intent list = new Intent(getApplicationContext(), AppointmentsList.class);
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
                params.put("date", dt);
                params.put("time", tm);
                params.put("comment", comm);
                params.put("contact", contact);
                params.put("m_name", name);
                params.put("m_contact", phn);

                return params;

                //see the name from php file and much them
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}