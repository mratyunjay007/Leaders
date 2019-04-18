package com.spirit.tech.leaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class LifeListAdapter extends RecyclerView.Adapter<LifeListAdapter.ViewHolder> {

    private ArrayList<LeaderVision> list;
    int index;

    ItemClicked activity;



    public LifeListAdapter(Context context,ArrayList<LeaderVision> list,int index)
    {
        activity=(ItemClicked) context;
        this.list=list;
        this.index=index;
    }

    public interface ItemClicked
    {
        void onItemClicled(int index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivIcon;
        TextView tvtitle,tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivIcon=itemView.findViewById(R.id.ivIcon);
            tvtitle=itemView.findViewById(R.id.tvtitle);
            tvDescription=itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.onItemClicled(list.indexOf((LeaderVision) v.getTag()));

                }
            });
        }
    }

    @NonNull
    @Override
    public LifeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_life,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LifeListAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(list.get(position));

        holder.tvtitle.setText(list.get(position).getTitle());
        holder.tvDescription.setText(list.get(position).getLife());

        if(ApplicationClass.leader.get(index).getName().equals("Swami Vivekanand"))
        {
            holder.ivIcon.setImageResource(R.drawable.vivek4);
        }
        else if(ApplicationClass.leader.get(index).getName().equals("Mahatma Gandhi"))
        {
            holder.ivIcon.setImageResource(R.drawable.gandhi4);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
