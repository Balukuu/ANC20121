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
import com.example.antenatalcareapp.Models.TipsModel;
import com.example.antenatalcareapp.R;
import com.example.antenatalcareapp.Urls;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.ViewHolder> {
    Context context;
    List<TipsModel> mData;
    private static final String URL_IMG = Urls.IP_URL;
    public TipsAdapter(Context context, List<TipsModel> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.tips_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        TipsModel tipsModel = mData.get(position);
        holder.title.setText(mData.get(position).getTitle());
        holder.details.setText(mData.get(position).getDetails());
        holder.items.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView items;
        TextView title, details;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            details = itemView.findViewById(R.id.details);
            items = itemView.findViewById(R.id.items);
        }

    }
}
