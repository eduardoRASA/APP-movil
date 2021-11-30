package com.example.movil.interfaces;

import com.example.movil.models.PostTanques;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TanquesService {
    @GET("tanques")
    Call<List<PostTanques>> find();
}
