package com.spirit.tech.leaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderAdapter extends RecyclerView.Adapter<LeaderAdapter.ViewHolder> {

    private ArrayList<Leader> list;

    ItemClicked activity;

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }


    public LeaderAdapter(Context context, ArrayList<Leader> list)
    {
        activity=(ItemClicked)context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivLeader;
        TextView tvName,tvDOB,tvPOB,tvDOE,tvContribution;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivLeader=itemView.findViewById(R.id.ivLeader);
            tvName=itemView.findViewById(R.id.tvName);
            tvDOB=itemView.findViewById(R.id.tvDOB);
            tvPOB=itemView.findViewById(R.id.tvPOB);
            tvDOE=itemView.findViewById(R.id.tvDOE);
            tvContribution=itemView.findViewById(R.id.tvContribution);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(list.indexOf((Leader) v.getTag()));

                }
            });
        }
    }

    @NonNull
    @Override
    public LeaderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderAdapter.ViewHolder holder, int position)
    {
        holder.itemView.setTag(list.get(position));
        if(list.get(position).getName().equals("Swami Vivekanand"))
        {
            holder.ivLeader.setImageResource(R.drawable.vivek);
        }
        else if(list.get(position).getName().equals("Mahatma Gandhi"))
        {
            holder.ivLeader.setImageResource(R.drawable.gandhi0);
        }
        holder.tvName.setText(list.get(position).getName());
        holder.tvDOB.setText(list.get(position).getDOB());
        holder.tvPOB.setText(list.get(position).getPOB());
        holder.tvDOE.setText(list.get(position).getDOE());
        holder.tvContribution.setText(list.get(position).getContibution());

        holder.tvName.setTextColor(list.get(position).getColor());
        holder.tvDOB.setTextColor(list.get(position).getColor());
        holder.tvPOB.setTextColor(list.get(position).getColor());
        holder.tvDOE.setTextColor(list.get(position).getColor());
        holder.tvContribution.setTextColor(list.get(position).getColor());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
