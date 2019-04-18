package com.spirit.tech.leaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class VisionAdapter extends RecyclerView.Adapter<VisionAdapter.ViewHolder> {

   private ArrayList<LeaderVision> list;
   int index;
   boolean check=false;
   ItemClicked activity;

   public interface ItemClicked
   {
       void onItemClicked(int index);
   }

   public VisionAdapter(Context context,ArrayList<LeaderVision> list,int i)
    {
        activity=(ItemClicked) context;
        this.list=list;
        index=i;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCount,tvVision;
        ImageView ivLeader,ivcheck;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCount=itemView.findViewById(R.id.tvCount);
            tvVision=itemView.findViewById(R.id.tvVision);
            ivLeader=itemView.findViewById(R.id.ivLeader);

            itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                            ApplicationClass.flag=true;
                            activity.onItemClicked(list.indexOf((LeaderVision) v.getTag()));
                            }
        });

        }
    }


    @NonNull
    @Override
    public VisionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.vision_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VisionAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(list.get(position));
        holder.tvCount.setText(position+1+"/"+getItemCount());
        holder.tvVision.setText(list.get(position).getVision());
        holder.tvVision.setTextColor(ApplicationClass.leader.get(index).getColor());

        if(ApplicationClass.leader.get(index).getName().equals("Swami Vivekanand"))
        {
            if(position%2==0)
                holder.ivLeader.setImageResource(R.drawable.vivek3);
            else
                holder.ivLeader.setImageResource(R.drawable.vivek4);


        }
        else if(ApplicationClass.leader.get(index).getName().equals("Mahatma Gandhi"))
        {
            if(position%2==0)
                holder.ivLeader.setImageResource(R.drawable.gandhi3);
            else
                holder.ivLeader.setImageResource(R.drawable.gandhi4);

        }

        }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
