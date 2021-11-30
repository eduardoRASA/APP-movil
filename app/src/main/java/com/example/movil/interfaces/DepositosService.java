package com.example.movil.interfaces;

import com.example.movil.models.PostDepositos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DepositosService {
    @GET("depositos")
    Call<List<PostDepositos>> find();
}
