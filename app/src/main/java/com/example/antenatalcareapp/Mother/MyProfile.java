package com.example.antenatalcareapp.Mother;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.Doctor.AddMother;
import com.example.antenatalcareapp.MainActivity;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.SessionManager;
import com.example.antenatalcareapp.Urls;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyProfile extends AppCompatActivity {
    TextInputEditText name, address, occupation, religion, status,nok;
    EditText phone;
    String getId,contact,role,cate;
    SessionManager sessionManager;
    public static String UPDATE_ACCOUNT = Urls.IP_URL+"update_account.php";
    DrawerLayout drawerLayout;
    TextView business_name, user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);

        name = findViewById(R.id.names);
        address = findViewById(R.id.address);
        occupation = findViewById(R.id.occupation);
        religion = findViewById(R.id.religion);
        status = findViewById(R.id.marital);
        nok = findViewById(R.id.nok);
        phone = findViewById(R.id.phone);

        LoadProfile();
    }

    private void LoadProfile() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading profile...");
        dialog.show();

        final String id = this.getId;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.LOAD_PROFILE,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");
                        JSONArray jsonArray = object.getJSONArray("read");
                        switch (success) {
                            case "1":
                                dialog.dismiss();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String contact = jsonObject.getString("contact").trim();
                                    String names = jsonObject.getString("names").trim();
                                    String locate = jsonObject.getString("address").trim();
                                    String rel = jsonObject.getString("religion").trim();
                                    String job = jsonObject.getString("occupation").trim();
                                    String id1 = jsonObject.getString("id").trim();
                                    String nextofkin = jsonObject.getString("next_of_kin").trim();
                                    String marital = jsonObject.getString("marital_status").trim();
                                    phone.setText(contact);
                                    name.setText(names);
                                    address.setText(locate);
                                    religion.setText(rel);
                                    occupation.setText(job);
                                    nok.setText(nextofkin);
                                    status.setText(marital);

                                }
                                break;
                            case "0":
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Something went wrong ", Toast.LENGTH_LONG).show();
                                break;
                            case "2":
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Could not read profile ", Toast.LENGTH_LONG).show();
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Not successful, check your internet connection ", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }, error -> {
                    Toast.makeText(getApplicationContext(), "Not successful, check your internet connection ", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userid", id);
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
//
    public void UpdateProfile(View view) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                MyProfile.this);
        alertDialog2.setTitle("Confirm to update");
        alertDialog2.setMessage("Make sure you double check your info");
        alertDialog2.setIcon(R.drawable.ic_warning);
        alertDialog2.setPositiveButton("YES",
                (dialog, which) -> UpdateProfileNow());
        alertDialog2.setNegativeButton("NO",
                (dialog, which) -> dialog.cancel());
        alertDialog2.show();
    }

    private void UpdateProfileNow() {
        Toast.makeText(getApplicationContext(), "Profile updated", Toast.LENGTH_LONG).show();
    }
}