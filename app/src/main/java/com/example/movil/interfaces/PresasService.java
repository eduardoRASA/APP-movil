package com.example.movil.interfaces;

import com.example.movil.models.PostPresas;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface PresasService {
    @GET("presas")
    Call<List<PostPresas>> find();
}
