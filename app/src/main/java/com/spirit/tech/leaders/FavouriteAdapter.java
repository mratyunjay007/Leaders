package com.spirit.tech.leaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder> {

    ArrayList<Favourite> list;
    ItemClicked activity;

    public interface ItemClicked
    {
        void onItemClicked(int index);
    }

    public FavouriteAdapter(Context context, ArrayList<Favourite> list)
    {
        activity=(ItemClicked) context;
        this.list=list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cvVision,cvStory;
        TextView tvVision,tvtitle,tvstory;
        ImageView ivleader;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cvVision=itemView.findViewById(R.id.cvVision);
            cvStory=itemView.findViewById(R.id.cvStory);
            tvVision=itemView.findViewById(R.id.tvVision);
            tvtitle=itemView.findViewById(R.id.tvTitle);
            tvstory=itemView.findViewById(R.id.tvStory);
            ivleader=itemView.findViewById(R.id.ivLeader);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        activity.onItemClicked(list.indexOf((Favourite) v.getTag()));
                }
            });


        }
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.favlist_layout,parent,false);
        return new ViewHolder(v);
        }

    @Override
    public void onBindViewHolder(@NonNull FavouriteAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(list.get(position));

        if(list.get(position).getSegment().equals("Vision"))
        {
            holder.cvStory.setVisibility(View.GONE);
            holder.cvVision.setVisibility(View.VISIBLE);

            if(list.get(position).getLeadername().equals("Swami Vivekanand"))
            {
            if(list.get(position).getPosition()%2==0)
            {
                holder.ivleader.setImageResource(R.drawable.vivek3);
            }
            else{
                holder.ivleader.setImageResource(R.drawable.vivek4);
            }}
            else if(list.get(position).getLeadername().equals("Mahatma Gandhi"))
            { if(list.get(position).getPosition()%2==0)
            {
            holder.ivleader.setImageResource(R.drawable.gandhi3);
             }
        else{
            holder.ivleader.setImageResource(R.drawable.gandhi4);
            }
            }


            holder.tvVision.setText(list.get(position).getData());
            holder.tvVision.setTextColor(list.get(position).getColor());
        }
        else if(list.get(position).getSegment().equals("Story"))
        {
            holder.cvVision.setVisibility(View.GONE);
            holder.cvStory.setVisibility(View.VISIBLE);

            holder.tvtitle.setText(list.get(position).getTitle());
            holder.tvstory.setText(list.get(position).getData());
            }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
