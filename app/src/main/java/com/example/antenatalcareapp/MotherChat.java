package com.example.antenatalcareapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.Adapters.MotherChatAdapter;
import com.example.antenatalcareapp.Doctor.AppointmentsList;
import com.example.antenatalcareapp.Doctor.MotherChatList;
import com.example.antenatalcareapp.Models.MothersModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MotherChat extends Fragment {
    private Menu action;
    List<MothersModel> mData;
    Urls urls;
    RecyclerView recyclerView;
    String getId,contact;
    SessionManager sessionManager;
    TextView error;
    EditText editText;
    MotherChatAdapter adapter;



    public MotherChat() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MotherChat newInstance(String param1, String param2) {
        MotherChat fragment = new MotherChat();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_mother_chat, container, false);

        urls = new Urls();
        sessionManager = new SessionManager(getActivity());
        error = view.findViewById(R.id.error);

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);

        //handle recyclerview
        recyclerView = view.findViewById(R.id.recylcerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mData = new ArrayList<>();
        adapter = new MotherChatAdapter(getActivity(), mData);
        loadMothers();

        editText = view.findViewById(R.id.search);
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
        return view;
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




    private void loadMothers() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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

                        adapter = new MotherChatAdapter(getActivity(), mData);
                        recyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        error.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Error Reading Details" + e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Error Reading Details" + error.toString(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("userId", getId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void goBack(View view) {
        startActivity(new Intent(getActivity(), MainActivity.class));
    }

    public void ShowList(View view) {
        startActivity(new Intent(getActivity(), AppointmentsList.class));

    }
}
