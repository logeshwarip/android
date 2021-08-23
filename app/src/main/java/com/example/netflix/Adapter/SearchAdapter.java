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

import com.example.netflix.R;
import com.example.netflix.SeriesDetail.SeriesDetail;
import com.example.netflix.models.SearchSeries;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private Context mContext;
    ArrayList<SearchSeries> list;

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
    public SearchAdapter(Context mContext, ArrayList<SearchSeries> list) {
        this.mContext = mContext;
        this.list = list;
    }
    @Override
    public SearchAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout, parent, false);
        SearchAdapter.MyViewHolder myViewHolder = new SearchAdapter.MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(final SearchAdapter.MyViewHolder holder, final int position) {
        SearchSeries searchSeries = list.get(position);

        holder.title.setText(searchSeries.getShow().getName());
        holder.description.setText(searchSeries.getShow().getType());
        if (searchSeries.getShow().getImage() != null) {
            Picasso.with(mContext).load(searchSeries.getShow().getImage().getOriginal()).into(holder.imgView);
        }else {
            Picasso.with(mContext).load(R.drawable.netflix_splash).into(holder.imgView);
        }

        holder.episode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(mContext, SeriesDetail.class);
                intent.putExtra("episodesId",searchSeries.getShow().getId());
                mContext.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}