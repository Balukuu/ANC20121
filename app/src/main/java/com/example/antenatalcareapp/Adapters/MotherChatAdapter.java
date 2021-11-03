package com.example.antenatalcareapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.antenatalcareapp.Chat;
import com.example.antenatalcareapp.Doctor.CreateAppointments;
import com.example.antenatalcareapp.Models.MothersModel;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.Urls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MotherChatAdapter extends RecyclerView.Adapter<MotherChatAdapter.ViewHolder> implements Filterable {
    static Context context;
    static List<MothersModel> mData;
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
    public MotherChatAdapter(Context context, List<MothersModel> mData) {
        this.context = context;
        this.mData = mData;
        this.markets_filter = new ArrayList<>(mData);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.select_mothers_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        MothersModel mothersModel = mData.get(position);
        holder.names.setText(mData.get(position).getNames());
        holder.contact.setText(mData.get(position).getContact());
        holder.age.setText(mData.get(position).getAge());
        holder.nin.setText(mData.get(position).getMaritual_status());
        holder.district.setText(mData.get(position).getAddress());
        holder.subcounty.setText(mData.get(position).getNext_of_kin());
        holder.parish.setText(mData.get(position).getOther_address());
        holder.village.setText(mData.get(position).getOccupation());
        holder.date.setText("start chat");

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
        TextView names, contact,age,nin,district,subcounty,parish,village;
        Button date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            names = itemView.findViewById(R.id.mother);
            age = itemView.findViewById(R.id.age);
            items = itemView.findViewById(R.id.items);
            contact = itemView.findViewById(R.id.contact);
            nin = itemView.findViewById(R.id.nin);
            date =itemView.findViewById(R.id.date);
            district = itemView.findViewById(R.id.district);
            subcounty =itemView.findViewById(R.id.subcounty);
            parish = itemView.findViewById(R.id.parish);
            village =itemView.findViewById(R.id.village);
            date.setOnClickListener(view -> {
                Intent update = new Intent(context, Chat.class);
                update.putExtra("id", mData.get(getBindingAdapterPosition()).getId());
                update.putExtra("names", mData.get(getBindingAdapterPosition()).getNames());
                update.putExtra("phone", mData.get(getBindingAdapterPosition()).getContact());
                update.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(update);
            });
        }

    }
}

