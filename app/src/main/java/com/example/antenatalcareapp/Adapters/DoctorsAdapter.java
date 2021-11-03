package com.example.antenatalcareapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antenatalcareapp.Chat;
import com.example.antenatalcareapp.Models.DoctorsModel;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.Urls;

import java.util.List;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.ViewHolder> {
    static Context context;
    static List<DoctorsModel> mData;
    private static final String URL_IMG = Urls.IP_URL;
    public DoctorsAdapter(Context context, List<DoctorsModel> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.select_doctors_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        DoctorsModel doctorsModel = mData.get(position);
        holder.names.setText(mData.get(position).getName());
        holder.contact.setText(mData.get(position).getPhone());
        holder.email.setText(mData.get(position).getEmail());
        holder.date.setText("start chat");

        holder.items.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView items;
        TextView names, contact,email;
        Button date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.doctor);
            email = itemView.findViewById(R.id.email);
            items = itemView.findViewById(R.id.items);
            contact = itemView.findViewById(R.id.contact);
            date = itemView.findViewById(R.id.date);
            date.setOnClickListener(view -> {
                Intent update = new Intent(context, Chat.class);
                update.putExtra("id", mData.get(getBindingAdapterPosition()).getId());
                update.putExtra("names", mData.get(getBindingAdapterPosition()).getName());
                update.putExtra("phone", mData.get(getBindingAdapterPosition()).getPhone());
                update.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(update);
            });
        }

    }
}
