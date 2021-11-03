package com.example.antenatalcareapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.Adapters.ChatAdapter;
import com.example.antenatalcareapp.Doctor.DocDashboard;
import com.example.antenatalcareapp.Doctor.MotherChatList;
import com.example.antenatalcareapp.Models.ChatModel;
import com.example.antenatalcareapp.Mother.MedicalPersonals;
import com.example.antenatalcareapp.Mother.MotherDashboard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Chat extends AppCompatActivity {
    private static final String TAG = "Chat";

    RecyclerView recyclerView, frecyclerView;
    List<ChatModel> mData;
    ChatAdapter adapter;
    TextView messageText;
    SessionManager sessionManager;
    String getId, contact, ext_phone,role;
    String patient_phone;
    TextView error_message;
    ProgressBar progressBar;
    ImageView sendBtn, back_btn;
    TextView fphone, you, mother, no_chat;
    public static int WaitingTime = 6000;
    Urls urls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        urls = new Urls();
        //handle session manager
        sessionManager = new SessionManager(getApplicationContext());
//        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);
        ext_phone = user.get(SessionManager.CONTACT);
        role = user.get(SessionManager.ROLE);

        fphone = findViewById(R.id.mother_phone);
        you = findViewById(R.id.you);
        mother = findViewById(R.id.mother);
        no_chat = findViewById(R.id.no_chat);
        progressBar = findViewById(R.id.progressBar);
        error_message = findViewById(R.id.error_message);
        messageText = findViewById(R.id.messageArea);
        sendBtn = findViewById(R.id.sendButton);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(v -> {
            Intent intent;
            if (role!= null && role.equals("doctor")) {
                intent = new Intent(getApplicationContext(), MotherChatList.class);
            }
            else{
                intent = new Intent(getApplicationContext(), MedicalPersonals.class);
            }
            startActivity(intent);
            finish();
        });
        sendBtn.setOnClickListener(view -> {
            String text_sms = messageText.getText().toString();
            if (text_sms.isEmpty()) {
                messageText.setError("type something for message");
            } else {
                sendSMS();
            }
        });


        //handle recyclerview
        recyclerView = findViewById(R.id.recycle_messages);

        mData = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recycle_messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter = new ChatAdapter(getApplicationContext(), mData);

        //get intents
        mother.setText(getIntent().getStringExtra("names"));
        fphone.setText(getIntent().getStringExtra("phone"));

        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    try {
                        //your method here
                        mData.clear();
                        getSMS();
                    } catch (Exception e) {
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, WaitingTime); //execute in every 1 minutes
//        getSMS();
    }

    private void sendSMS() {
        progressBar.setVisibility(View.VISIBLE);
        final String scontact = contact;
        final String message = messageText.getText().toString().trim();
        final String rcontact = fphone.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.SEND_MESSAGE,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONObject object = new JSONObject(response);
                        String success = object.getString("success");
                        if (success.equals("1")) {
                            Toast.makeText(getApplicationContext(), "sent", Toast.LENGTH_SHORT).show();
                            messageText.setText("");
                            mData.clear();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                getSMS();
                            }
                            error_message.setVisibility(View.INVISIBLE);
                        } else if (success.equals("0")) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Message Not sent", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "error ", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), "error ", Toast.LENGTH_SHORT).show();
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("scontact", scontact);
                params.put("message", message);
                params.put("rcontact", rcontact);

                return params;

            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getSMS() {
        final String phone = contact;
        final String mother_phone = fphone.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.MESSAGES_LIST,
                response -> {
                    try {
                        Log.i("tagconvertstr", "[" + response + "]");
                        JSONArray sms = new JSONArray(response);
                        if (sms.length() == 0) {
                            progressBar.setVisibility(View.GONE);
                            error_message.setVisibility(View.INVISIBLE);
                            no_chat.setVisibility(View.VISIBLE);
                        } else {
                            for (int i = 0; i < sms.length(); i++) {
                                JSONObject smsObjects = sms.getJSONObject(i);

                                String message = smsObjects.getString("message");
                                String time = smsObjects.getString("time");
                                String sender = smsObjects.getString("sender").trim();
                                String receiver = smsObjects.getString("reciever").trim();
                                String r_name = mother.getText().toString().trim();

                                progressBar.setVisibility(View.GONE);
                                error_message.setVisibility(View.INVISIBLE);
                                ChatModel chatModel = new ChatModel(message, time, sender, receiver, r_name);
                                mData.add(chatModel);
                            }
                        }

                        adapter = new ChatAdapter(getApplicationContext(), mData);
                        recyclerView.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                        error_message.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "something went wrong" + e.toString(), Toast.LENGTH_LONG).show();
                    }

                }, error -> {
            progressBar.setVisibility(View.GONE);
            error_message.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "could not load messages" + error.toString(), Toast.LENGTH_LONG).show();
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("doc_phone", phone);
                params.put("m_phone", mother_phone);

                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getApplicationContext()));
        requestQueue.add(stringRequest);
    }
}