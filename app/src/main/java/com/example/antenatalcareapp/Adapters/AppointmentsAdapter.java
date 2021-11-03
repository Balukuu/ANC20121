package com.example.antenatalcareapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antenatalcareapp.Models.AppointmentsModel;
import com.example.antenatalcareapp.Models.CentersModel;
import com.example.antenatalcareapp.Models.MothersModel;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.Urls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder> implements Filterable {
    Context context;
    List<AppointmentsModel> mData;
    List<AppointmentsModel> markets_filter;
    private static final String URL_IMG = Urls.IP_URL;

    private Filter examplefilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AppointmentsModel> filterexample = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterexample.addAll(markets_filter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (AppointmentsModel marketsModel : markets_filter) {
                    if (marketsModel.getName().toLowerCase().contains(filterPattern)) {
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
            mData.addAll((Collection<? extends AppointmentsModel>) results.values);
            notifyDataSetChanged();
        }
    };
    public AppointmentsAdapter(Context context, List<AppointmentsModel> mData) {
        this.context = context;
        this.mData = mData;
        this.markets_filter = new ArrayList<>(mData);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.appointment_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        AppointmentsModel appointmentsModel = mData.get(position);
        holder.names.setText(mData.get(position).getName());
        holder.contact.setText(mData.get(position).getContact());
        holder.comment.setText(mData.get(position).getComment());
        holder.date.setText(mData.get(position).getDate());
        holder.time.setText(mData.get(position).getTime());
        holder.status.setText(mData.get(position).getStatus());

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

    public void filterList(ArrayList<AppointmentsModel> filteredList) {
        mData = filteredList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView items;
        TextView names, contact,date,time,status,comment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.names);
            date = itemView.findViewById(R.id.date);
            items = itemView.findViewById(R.id.items);
            contact = itemView.findViewById(R.id.contact);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
            comment = itemView.findViewById(R.id.comment);
        }

    }
}
