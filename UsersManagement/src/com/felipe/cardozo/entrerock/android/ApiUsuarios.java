/*
 * Created on 28/06/2015 by Felipe Cardozo
 */
package com.felipe.cardozo.entrerock.android;

import java.util.List;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.Callback;

public interface ApiUsuarios {
    @GET("/Usuarios")
    public void getUsuarios(Callback <List<Usuario>> callback);
    @GET("/Usuarios")
    public void getUsuario(@Query("login")String login, @Query("password") String password, Callback<Usuario> callback);
    @GET("/Usuarios")
    public void getUsuario(@Query("login")String login, Callback<Usuario> callback);
    @GET("/DeleteUsuario")
    public void deleteUsuario(@Query("login")String login, Callback<Void> callback);
    @POST("/Usuarios")
    public void postUsuario(@Query("login")String login, @Body Usuario usuario, Callback<Void> callback);
    @POST("/UpdateUsuario")
    public void updateUsuarios(@Body Usuario usuario, Callback<Void> callback);
}
