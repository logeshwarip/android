package com.example.netflix.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.netflix.DashBoard.DashBoard;
import com.example.netflix.R;
import com.example.netflix.SeriesDetail.SeriesDetail;
import com.example.netflix.models.Shows;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context mContext;
    ArrayList<Shows> list;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView imgView;
        Button episode;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.description = (TextView) itemView.findViewById(R.id.description);
            this.imgView = (ImageView) itemView.findViewById(R.id.imgcar);
            this.episode = (Button) itemView.findViewById(R.id.episode);
        }
    }
    public CustomAdapter(Context mContext, ArrayList<Shows> list) {
        this.mContext = mContext;
        this.list = list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Shows showList = list.get(position);
        Shows.ImageShow imageShow = showList.getImage();

      holder.title.setText(showList.getName());
      holder.description.setText(showList.getType());
      Picasso.with(mContext).load(imageShow.getOriginal()).into(holder.imgView);

      holder.episode.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mContext.startActivity(new Intent(mContext, SeriesDetail.class));
          }
      });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}