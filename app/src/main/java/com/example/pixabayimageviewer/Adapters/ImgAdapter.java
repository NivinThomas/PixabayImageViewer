package com.example.pixabayimageviewer.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pixabayimageviewer.Activity.MainActivity;
import com.example.pixabayimageviewer.Interface.AdapterClick;
import com.example.pixabayimageviewer.ModelClasses.Hits;
import com.example.pixabayimageviewer.R;

import java.util.List;

public class ImgAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Hits> ImgList;
    private int AdapterType;
    private AdapterClick adapterClick;

    public ImgAdapter(Context context, List<Hits> imgList, int adapterType, AdapterClick adapterClick) {
        this.context = context;
        ImgList = imgList;
        AdapterType = adapterType;
        this.adapterClick = adapterClick;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        if (AdapterType == 0) {

            View view = layoutInflater.inflate(R.layout.preview_row_layout, parent, false);
            return new ThumbnailImageViewHolder(view);

        } else {
            View view = layoutInflater.inflate(R.layout.fullscreen_row_layout, parent, false);
            return new FullscreenImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (AdapterType == 0) {

            ((ThumbnailImageViewHolder) holder).Databind(ImgList.get(position));

        } else {
            ((FullscreenImageViewHolder) holder).Databind(ImgList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return ImgList.size();
    }

    //For ThumbnailImage
    public class ThumbnailImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ThumnailImgView;

        public ThumbnailImageViewHolder(@NonNull View itemView) {
            super(itemView);

            ThumnailImgView = itemView.findViewById(R.id.preview_img);
            itemView.setOnClickListener(this);
        }

        public void Databind(Hits hits) {

            Glide.with(context).load(hits.getLargeImageURL()).into(ThumnailImgView);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            adapterClick.itemclick(ImgList.get(position), position);
        }
    }

    //For FullScreenImage
    public class FullscreenImageViewHolder extends RecyclerView.ViewHolder {

        ImageView FullScreenImgView;

        public FullscreenImageViewHolder(@NonNull View itemView) {
            super(itemView);

            FullScreenImgView = itemView.findViewById(R.id.fullscrreen_img);
        }

        public void Databind(Hits hits) {
            Glide.with(context).load(hits.getLargeImageURL()).into(FullScreenImgView);
        }
    }

}
