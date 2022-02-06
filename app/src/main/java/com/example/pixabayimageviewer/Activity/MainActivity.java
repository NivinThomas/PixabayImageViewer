package com.example.pixabayimageviewer.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pixabayimageviewer.API.RetrofitClient;
import com.example.pixabayimageviewer.Adapters.ImgAdapter;
import com.example.pixabayimageviewer.Interface.AdapterClick;
import com.example.pixabayimageviewer.ModelClasses.Hits;
import com.example.pixabayimageviewer.ModelClasses.Responses;
import com.example.pixabayimageviewer.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterClick {

    private ActivityMainBinding binding;
    private List<Hits>Imagelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Imagelist=new ArrayList<>();

        binding.RecyclerViewGallery.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        binding.RecyclerViewGallery.setLayoutManager(gridLayoutManager);

        getThumbnail();

    }

    private void getThumbnail() {

        Call<Responses>responsesCall= RetrofitClient.getInstance().getApi().imageGet(1,50);
        responsesCall.enqueue(new Callback<Responses>() {
            @Override
            public void onResponse(Call<Responses> call, Response<Responses> response) {
                if (response.isSuccessful()){
                    Responses myResponses=response.body();
                    if (myResponses!=null){
                        Imagelist=myResponses.getHits();
                        if (Imagelist!=null&&Imagelist.size()>0){
                            ImgAdapter adapter=new ImgAdapter(MainActivity.this,Imagelist, 0, MainActivity.this);
                            binding.RecyclerViewGallery.setAdapter(adapter);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<Responses> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void itemclick(Hits hits, int position) {

        Intent intent=new Intent(MainActivity.this,FullScreenImage.class);
        startActivity(intent);
        intent.putExtra("position",position);

    }
}