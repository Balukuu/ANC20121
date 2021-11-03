package com.example.antenatalcareapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.antenatalcareapp.Doctor.CreateAppointments;
import com.example.antenatalcareapp.Doctor.MothersList;
import com.example.antenatalcareapp.Models.MothersModel;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MothersAdapter extends RecyclerView.Adapter<MothersAdapter.ViewHolder> implements Filterable {
    Context context;
    List<MothersModel> mData;
    List<MothersModel> markets_filter;
    private static final String URL_IMG = Urls.IP_URL;


    private Filter examplefilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MothersModel> filterexample = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterexample.addAll(markets_filter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (MothersModel marketsModel : markets_filter) {
                    if (marketsModel.getNames().toLowerCase().contains(filterPattern)) {
                        filterexample.add(marketsModel);
                    }

                }
            }

            FilterResults results = new FilterResults();
            results.values = filterexample;
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mData.clear();
            mData.addAll((Collection<? extends MothersModel>) results.values);
            notifyDataSetChanged();
        }
    };
    public MothersAdapter(Context context, List<MothersModel> mData) {
        this.context = context;
        this.mData = mData;
        this.markets_filter = new ArrayList<>(mData);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.mothers_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MothersModel mothersModel = mData.get(position);

        String Mother_Name =  mData.get(position).getNames();
        String Phone_Number =  mData.get(position).getContact();

        holder.names.setText(Mother_Name);
        holder.contact.setText(Phone_Number);
        holder.age.setText(mData.get(position).getAge());
        holder.nin.setText(mData.get(position).getMaritual_status());
        holder.district.setText(mData.get(position).getAddress());
        holder.subcounty.setText(mData.get(position).getNext_of_kin());
        holder.parish.setText(mData.get(position).getOther_address());
        holder.village.setText(mData.get(position).getOccupation());
        holder.date.setText("Date added: " + mData.get(position).getDate_added());


        holder.createappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(context ,CreateAppointments.class);
                i.putExtra("Mother_Name",Mother_Name);
                i.putExtra("Phone_Number",Phone_Number);
                context.startActivity(i);
            }
        });

        holder.delete.setOnClickListener(v -> {
            final String itemid = mothersModel.getId();
            final String mothername = mothersModel.getNames();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete " + mothername)
                    .setMessage("Are you sure you want to Remove this Record Permanently?")
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_warning)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.DELETE_MOTHER,
                                (Response.Listener<String>) response -> {
                                    try {
                                        Log.i("tagconvertstr", "[" + response + "]");
                                        JSONObject object = new JSONObject(response);
                                        String success = object.getString("success");
                                        if (success.equals("1")) {
                                            Log.i("tagconvertstr", "[" + response + "]");
                                            Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
//
                                            Intent i = new Intent(context, MothersList.class);
                                            context.startActivity(i);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(context, "Not deleted, please try again " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }, error -> Toast.makeText(context, "Not deleted, please check your internet connection and try again" + error.getMessage(), Toast.LENGTH_LONG).show()) {

                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<>();
                                params.put("id", itemid);
                                return params;

                                //see the name from php file and much them
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        requestQueue.add(stringRequest);
                    })
                    .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
            //Creating dialog box
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        holder.items.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    @Override
    public Filter getFilter() {
        return examplefilter;
    }

    public void filterList(ArrayList<MothersModel> filteredList) {
        mData = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView items;
        TextView names, contact, age, nin, district, subcounty, parish, village, date;
        Button delete,createappointment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete);
            names = itemView.findViewById(R.id.mother);
            age = itemView.findViewById(R.id.age);
            items = itemView.findViewById(R.id.items);
            contact = itemView.findViewById(R.id.contact);
            nin = itemView.findViewById(R.id.nin);
            date = itemView.findViewById(R.id.date);
            district = itemView.findViewById(R.id.district);
            subcounty = itemView.findViewById(R.id.subcounty);
            parish = itemView.findViewById(R.id.parish);
            village = itemView.findViewById(R.id.village);
            createappointment=itemView.findViewById(R.id.date2);
        }

    }
}
