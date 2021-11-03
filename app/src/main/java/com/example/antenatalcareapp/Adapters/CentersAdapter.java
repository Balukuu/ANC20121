package com.example.antenatalcareapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antenatalcareapp.Models.CentersModel;
import com.example.antenatalcareapp.Models.MothersModel;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.Urls;

import java.util.List;

public class CentersAdapter extends RecyclerView.Adapter<CentersAdapter.ViewHolder> {
    Context context;
    List<CentersModel> mData;
    private static final String URL_IMG = Urls.IP_URL;
    public CentersAdapter(Context context, List<CentersModel> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.centers_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        CentersModel centersModel = mData.get(position);
        holder.center.setText(mData.get(position).getReferral());
        holder.contact.setText(mData.get(position).getContact());
        holder.address.setText(mData.get(position).getAddress());
        holder.officer.setText(mData.get(position).getOfficer());

        holder.items.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView items;
        TextView center, contact,officer,address;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            center = itemView.findViewById(R.id.center);
            officer = itemView.findViewById(R.id.officer);
            items = itemView.findViewById(R.id.items);
            contact = itemView.findViewById(R.id.contact);
            address = itemView.findViewById(R.id.address);
        }

    }
}
