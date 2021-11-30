package com.example.movil.interfaces;

import com.example.movil.models.PostDetalle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DetalleTanquesService {
    @GET("registroTanques")
    Call<List<PostDetalle>> find(@Query("id") int id);
}
