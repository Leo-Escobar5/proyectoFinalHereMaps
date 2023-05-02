package com.leo_escobar.pruebafinal;

import com.leo_escobar.pruebafinal.data.BodyLogin;
import com.leo_escobar.pruebafinal.data.Clients;
import com.leo_escobar.pruebafinal.data.LoginRespuesta;
import com.leo_escobar.pruebafinal.data.User;
import com.leo_escobar.pruebafinal.data.Usesrs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ServiceRetrofit {
    @POST("auth/login")
    Call<LoginRespuesta> login(@Body BodyLogin bodyLogin);

    @GET("usuarios")
    Call<Usesrs> getUsuarios(@Header("token") String token);

    // GET clientes
    @GET("clientes")
    Call<Clients> getClientes(@Header("token") String token);



//    @GET("usuario")
//    Call<BodyLogin> login(@Header("token") String token, User User);



}
