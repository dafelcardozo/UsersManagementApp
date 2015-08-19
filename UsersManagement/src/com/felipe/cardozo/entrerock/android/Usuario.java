/*
 * Created on 28/06/2015 by Felipe Cardozo
 */
package com.felipe.cardozo.entrerock.android;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuario {
    public Usuario(){}
    public Usuario(String login, String password){
        setLogin(login);
        setPassword(password);
    }
    public Usuario(String login, String password, String nombre, String apellido, String correo){
        setLogin(login);
        setPassword(password);
        setNombre(nombre);
        setApellido(apellido);
        setEmail(correo);
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    @Expose
    @SerializedName("Login")
    private String login;
    @Expose
    @SerializedName("Password")
    private String password; 
    @Expose
    @SerializedName("Email")
    private String email; 
    @Expose
    @SerializedName("Nombre")
    private String nombre;
    @Expose
    @SerializedName("Apellido")
    private String apellido;
}
