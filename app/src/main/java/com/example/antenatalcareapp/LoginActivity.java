package com.example.antenatalcareapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.Doctor.DocDashboard;
import com.example.antenatalcareapp.Mother.MotherDashboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Urls urls;
    TextView heading;
    EditText phone_login, password;
    Button login, register;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        sessionManager = new SessionManager(this);
        urls = new Urls();

        phone_login = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.button);

        login.setOnClickListener(v -> {
            String user = phone_login.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if (!user.isEmpty()) {
                Login(user, pass);

            } else {
                phone_login.setError("Enter correct phone number");
                password.setError("Enter correct password");
            }
        });
    }
    private void Login(final String phone, final String password) {

        final ProgressDialog loading = new ProgressDialog(LoginActivity.this);
        loading.setMessage("logging in please wait...");
        loading.show();
        login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urls.LOGIN_URL,
                response -> {
                    Log.i("tagconvertstr", "[" + response + "]");
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("login");
                        if (success.equals("1")) {
                            loading.dismiss();
                            Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_SHORT).show();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("tagconvertstr", "[" + response + "]");
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("userid");
                                String contact = object.getString("phone");
                                String role = object.getString("role");
                                String fullname = object.getString("fullname");
                                sessionManager.createSession(contact, fullname, id, role);

                                if (role.equals("doctor")) {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity3.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                                    startActivity(intent);
                                    finish();
                                }

                                login.setVisibility(View.GONE);

                            }
                        } else if (success.equals("0")) {
                            loading.dismiss();
                            login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "login wasn't error account not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        loading.dismiss();
                        login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "failed to login, please try again " + e.toString(), Toast.LENGTH_LONG).show();
                    }
                },

                error -> {
                    loading.dismiss();
                    login.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "login error please check your internet connection and try again ", Toast.LENGTH_SHORT).show();
                }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("phone", phone);//user the phone
                params.put("password", password);//user password
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void openRegistration(View view) {
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }

    public void openDashboard(View view) {
        startActivity(new Intent(getApplicationContext(),Dashboard.class));
    }

    public void openMotherLogin(View view) {
        startActivity(new Intent(getApplicationContext(),MotherLogin.class));
    }

    public void openLogin(View view) {
        startActivity(new Intent(getApplicationContext(),MotherLogin.class));
    }
}