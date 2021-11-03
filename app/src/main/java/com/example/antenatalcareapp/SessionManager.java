package com.example.antenatalcareapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.antenatalcareapp.Doctor.DocDashboard;
import com.example.antenatalcareapp.Mother.MotherDashboard;

import java.util.HashMap;

public class SessionManager {
    public static final String CONTACT = "CONTACT";
    public static final String FULLNAME = "FULLNAME";
    public static final String ROLE = "ROLE";
    public static final String ID = "ID";
    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static SharedPreferences.Editor editor;
    public static Context context;
    SharedPreferences sharedPreferences;
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String contact, String fullname, String id, String role) {
        editor.putBoolean(LOGIN, true);
        editor.putString(CONTACT, contact);
        editor.putString(FULLNAME, fullname);
        editor.putString(ID, id);
        editor.putString(ROLE, role);
        editor.apply();

    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public void checkLogin() {
        if (!this.isLogin()) {
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> user = new HashMap<>();
        user.put(CONTACT, sharedPreferences.getString(CONTACT, null));
        user.put(FULLNAME, sharedPreferences.getString(FULLNAME, null));
        user.put(ROLE, sharedPreferences.getString(ROLE,null));
        user.put(ID, sharedPreferences.getString(ID, null));
        return user;
    }

    public static void logout() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((Settings) context).finish();
    }
    public static void MotherLogout() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MotherLogin.class);
        context.startActivity(i);
        ((MainActivity2) context).finish();
    }
    public static void DocLogout() {
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((MainActivity3) context).finish();
    }

}
