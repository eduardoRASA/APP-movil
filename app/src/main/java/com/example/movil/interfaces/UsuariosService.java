package com.example.movil.interfaces;

import com.example.movil.models.PostUsuarios;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UsuariosService {
    @GET("usuarios")
    Call<PostUsuarios> find(@Query("email") String str);
}
