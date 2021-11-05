package com.example.antenatalcareapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.Mother.MyProfile;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {
    TextInputEditText name, address, occupation, religion, status,nok;
    EditText phone;
    String getId,contact,role,cate;
    SessionManager sessionManager;
    Button button;
    public static String UPDATE_ACCOUNT = Urls.IP_URL+"update_account.php";
    DrawerLayout drawerLayout;
    TextView business_name, user_name;

    public ProfileFragment() {

    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        sessionManager = new SessionManager(getActivity());
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);
        contact = user.get(SessionManager.CONTACT);

        name = view.findViewById(R.id.names);
        address = view.findViewById(R.id.address);
        occupation = view.findViewById(R.id.occupation);
        religion = view.findViewById(R.id.religion);
        status = view.findViewById(R.id.marital);
        nok = view.findViewById(R.id.nok);
        phone = view.findViewById(R.id.phone);
        LoadProfile();

        button = (Button) view.findViewById(R.id.reg_btn);
       button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Toast.makeText(getActivity(), "call", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(getActivity());
                alertDialog2.setTitle("Confirm to update");
                alertDialog2.setMessage("Make sure you double check your info");
                alertDialog2.setIcon(R.drawable.ic_warning);
                alertDialog2.setPositiveButton("YES",
                        (dialog, which) -> UpdateProfileNow());
                alertDialog2.setNegativeButton("NO",
                        (dialog, which) -> dialog.cancel());
                alertDialog2.show();

            }});


        return view;
    }

    private void LoadProfile() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
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
                                Toast.makeText(getActivity(), "Something went wrong ", Toast.LENGTH_LONG).show();
                                break;
                            case "2":
                                dialog.dismiss();
                                Toast.makeText(getActivity(), "Could not read profile ", Toast.LENGTH_LONG).show();
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Not successful, check your internet connection ", Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                }, error -> {
            Toast.makeText(getActivity(), "Not successful, check your internet connection ", Toast.LENGTH_LONG).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }




    private void UpdateProfileNow() {
        Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_LONG).show();
    }
}

