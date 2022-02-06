package com.example.pixabayimageviewer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pixabayimageviewer.API.RetrofitClient;
import com.example.pixabayimageviewer.Adapters.ImgAdapter;
import com.example.pixabayimageviewer.ModelClasses.Hits;
import com.example.pixabayimageviewer.ModelClasses.Responses;
import com.example.pixabayimageviewer.databinding.ActivityFullScreenImageBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullScreenImage extends AppCompatActivity {

    private ActivityFullScreenImageBinding binding;
    private List<Hits> Imagelist;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityFullScreenImageBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        Imagelist = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra("position", 0);
        }

        binding.RecyclerViewFullScreen.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.RecyclerViewFullScreen.setLayoutManager(linearLayoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(binding.RecyclerViewFullScreen);

        getThumbnail();
    }

    private void getThumbnail() {

        Call<Responses> responsesCall = RetrofitClient.getInstance().getApi().imageGet(1, 50);
        responsesCall.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.isSuccessful()) {
                    Responses myResponses = response.body();
                    if (myResponses != null) {
                        Imagelist = myResponses.getHits();
                        if (Imagelist != null && Imagelist.size() > 0) {
                            ImgAdapter adapter = new ImgAdapter(FullScreenImage.this, Imagelist, 1, null);
                            binding.RecyclerViewFullScreen.setAdapter(adapter);
                            binding.RecyclerViewFullScreen.scrollToPosition(position);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                Toast.makeText(FullScreenImage.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}