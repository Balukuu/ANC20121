package com.example.antenatalcareapp.Doctor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddMother extends AppCompatActivity {
    private static final String TAG = "AddMother";

    EditText names, phone, sub_county, parish, village, occupation,
            nok,nok_relationship,nok_address, religion;
    Button register_btn, btn_date_birth;
    Spinner gender, region, western, central, northern, eastern,m_status;
    TextView code, date_birth, reg_id, textView;
    SessionManager sessionManager;
    String getId, contact;
    ProgressBar progress_reg;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    LinearLayout   place_details;
    String districtholder;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Urls urls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mother);

        sessionManager = new SessionManager(getApplicationContext());
        urls = new Urls();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);

        nok = findViewById(R.id.nok);
        nok_address = findViewById(R.id.nok_address);
        nok_relationship = findViewById(R.id.nok_relationship);
        progress_reg = findViewById(R.id.progress_reg);
        register_btn = findViewById(R.id.reg_btn);
        village = findViewById(R.id.village);
        parish = findViewById(R.id.parish);
        religion = findViewById(R.id.religion);
        sub_county = findViewById(R.id.sub_county);
        place_details = findViewById(R.id.place_details);
        date_birth = findViewById(R.id.date_picked);
        btn_date_birth = findViewById(R.id.btn_select_date);
        occupation = findViewById(R.id.occupation);
        phone = findViewById(R.id.phone);
        names = findViewById(R.id.names);
        gender = findViewById(R.id.gender);
        m_status = findViewById(R.id.m_status);

        region = findViewById(R.id.regions);
        central = findViewById(R.id.central);
        western = findViewById(R.id.western);
        northern = findViewById(R.id.northern);
        eastern = findViewById(R.id.eastern);
        ImageView back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });


        btn_date_birth.setOnClickListener((View.OnClickListener) view -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    AddMother.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        mDateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

            String date = year + "-" + month + "-" + day;
            date_birth.setText(date);
        };

//        handle districts
        region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedregion = adapterView.getItemAtPosition(i).toString();
                switch (selectedregion) {
                    case "Central":
                        place_details.setVisibility(View.VISIBLE);
                        western.setVisibility(View.GONE);
                        eastern.setVisibility(View.GONE);
                        northern.setVisibility(View.GONE);
                        central.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Please select your district", Toast.LENGTH_SHORT).show();


                        break;
                    case "Western":
                        place_details.setVisibility(View.VISIBLE);
                        central.setVisibility(View.GONE);
                        eastern.setVisibility(View.GONE);
                        northern.setVisibility(View.GONE);
                        western.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Please select your district", Toast.LENGTH_SHORT).show();


                        break;
                    case "Eastern":
                        place_details.setVisibility(View.VISIBLE);
                        central.setVisibility(View.GONE);
                        western.setVisibility(View.GONE);
                        northern.setVisibility(View.GONE);
                        eastern.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Please select your district", Toast.LENGTH_SHORT).show();


                        break;
                    case "Northern":
                        place_details.setVisibility(View.VISIBLE);
                        central.setVisibility(View.GONE);
                        western.setVisibility(View.GONE);
                        eastern.setVisibility(View.GONE);
                        northern.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), "Please select your district", Toast.LENGTH_SHORT).show();

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        central.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selecteddistrict = parent.getItemAtPosition(position).toString();
                if (!selecteddistrict.equals("Select your district")) {
                    districtholder = central.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });
        western.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selecteddistrict = parent.getItemAtPosition(position).toString();
                if (!selecteddistrict.equals("Select your district")) {
                    districtholder = western.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });
        eastern.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selecteddistrict = parent.getItemAtPosition(position).toString();
                if (!selecteddistrict.equals("Select your district")) {
                    districtholder = eastern.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });
        northern.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selecteddistrict = parent.getItemAtPosition(position).toString();
                if (!selecteddistrict.equals("Select your district")) {
                    districtholder = northern.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // can leave this empty
            }
        });

        register_btn.setOnClickListener(v -> {
            final String fn = names.getText().toString();
            final String date = date_birth.getText().toString();
            final String reg = districtholder;
            final String sub = sub_county.getText().toString();
            final String nk = nok.getText().toString();
            final String nk_rel = nok_relationship.getText().toString();
            final String nk_add = nok_address.getText().toString();
            final String par = parish.getText().toString();
            final String vill = village.getText().toString();
            if (fn.isEmpty() || nk.isEmpty()  || nk_add.isEmpty() || date.isEmpty() || reg.equals("Select your district") || sub.isEmpty() || par.isEmpty() || vill.isEmpty()) {
                Toast.makeText(getApplicationContext(), "all fields are required", Toast.LENGTH_SHORT).show();
            } else {
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                        AddMother.this);
                alertDialog2.setTitle("Confirm to register");
                alertDialog2.setMessage("Make sure you double check your registration details click \n yes to proceed \n no to go back");
                alertDialog2.setIcon(R.drawable.ic_warning);
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) -> register());
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();

            }
        });
    }
    private void register() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        final String fname = this.names.getText().toString();
        final String phone = this.phone.getText().toString();
        final String date = this.date_birth.getText().toString();
        final String districts = districtholder.trim();
        final String sub_county = this.sub_county.getText().toString();
        final String parish = this.parish.getText().toString();
        final String village = this.village.getText().toString();
        final String gender = this.gender.getSelectedItem().toString();
        final String mstatus = m_status.getSelectedItem().toString();
        final String m_occupation = occupation.getText().toString().trim();
        final String nk = nok.getText().toString();
        final String nk_rel = nok_relationship.getText().toString();
        final String nk_add = nok_address.getText().toString();
        final String rel = religion.getText().toString().trim();
        final String m_address = village +" "+ parish +" "+ sub_county +" "+ districts;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.REG_URL,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");
                        if (success.equals("1")) {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Register success", Toast.LENGTH_LONG).show();
                            Intent list = new Intent(getApplicationContext(), MothersList.class);
                            startActivity(list);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Error, please try again ", Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Register not successful, please try again "+e.getMessage(), Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }, error -> {
            Toast.makeText(getApplicationContext(), "Register not successful, please check your network and try again ", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("m_name", fname);
                params.put("next_of_kin", nk);
                params.put("gender", gender);
                params.put("m_contact", phone);
                params.put("date_birth", date);
                params.put("m_age", date);
                params.put("m_address", m_address);
                params.put("m_occupation", m_occupation);
                params.put("m_religion", rel);
                params.put("m_status", mstatus);
                params.put("n_relationship", nk_rel);
                params.put("n_address", nk_add);
                params.put("contact", contact);

                return params;

                //see the name from php file and much them
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