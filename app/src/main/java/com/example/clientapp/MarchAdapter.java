package com.example.clientapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clientapp.Classes.Marchandise;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MarchAdapter extends RecyclerView.Adapter <MarchAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Marchandise> mUploads;
    public MarchAdapter(Context context, List<Marchandise> uploads){
        mContext = context;
        mUploads =uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.carditems,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Marchandise uploadCurrent = mUploads.get(position);
        holder.price.setText(uploadCurrent.getPrice());
        holder.desc.setText(uploadCurrent.getDescription());
        Picasso.with(mContext).load(uploadCurrent.getmImageUrl()).fit().centerCrop().into(holder.image);
        //  Picasso.with(mContext).load(uploadCurrent.getmImageUrl()).into(holder.imageView);
        //.placeholder(R.mipmap.ic_launcher)
        //.fit().placeholder(R.mipmap.ic_launcher)
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public TextView desc;
        public TextView price;
        public ImageView image;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            desc = itemView.findViewById(R.id.desc);
            image = itemView.findViewById(R.id.image);
            price = itemView.findViewById(R.id.price);
        }
    }
}