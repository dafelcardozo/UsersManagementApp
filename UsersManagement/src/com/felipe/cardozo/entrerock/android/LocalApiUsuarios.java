/*
 * Created on 11/08/2015 by Felipe Cardozo
 */
package com.felipe.cardozo.entrerock.android;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import retrofit.Callback;
import retrofit.client.Header;
import retrofit.client.Response;

public class LocalApiUsuarios implements ApiUsuarios{
    final Context context;
    private final SQLiteOpenHelper sqlite;
    public LocalApiUsuarios(Context context) {
        this.context = context;
        this.sqlite = new SQLiteOpenHelper(context, "com.felipe.cardozo.Usuarios", null, 1) {
            
            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
            
            @Override
            public void onCreate(SQLiteDatabase db) {
               db.execSQL("create table Usuarios (login varchar(100) primary key, "
                       + "password varchar(100) not null, nombre varchar(100) not null, apellido varchar(100) not null, correo varchar(100) not null)");
               db.execSQL("insert into Usuarios (login, password, nombre, apellido, correo) values('felipe', '123', 'Felipe', 'Cardozo', 'dafelcardozo@gmail.com')");
            }
        };
    }
    @Override
    public void getUsuarios(Callback<List<Usuario>> callback) {
       SQLiteDatabase db = this.sqlite.getWritableDatabase();
      
       Cursor cursor = db.query(true, "Usuarios", null, null, null, null, null, "login", null);
       cursor.moveToFirst();
       
       List<Usuario> usuarios = new ArrayList<Usuario>();
       while (!cursor.isAfterLast()) {
         Usuario u = toUsuario(cursor);
         usuarios.add(u);
         cursor.moveToNext();
       }
       cursor.close();
       db.close();
       callback.success(usuarios, fakeResponse());
    }

    private static Usuario toUsuario(final Cursor c) {
        return new Usuario(){
            {
                setLogin(c.getString(0));
                setPassword(c.getString(1));
                setNombre(c.getString(2));
                setApellido(c.getString(3));
                setEmail(c.getString(4));
            }
        };
    }
    @Override
    public void getUsuario(String login, String password, Callback<Usuario> callback) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.sqlite.getReadableDatabase();
            cursor = db.query(true, "Usuarios", null, "login = ? and password = ?", new String[]{login, password}, null, null, "login", null);
            cursor.moveToFirst();
            
            if (!cursor.isAfterLast())  
                callback.success(toUsuario(cursor), fakeResponse());
            else 
                callback.success(null, fakeResponse());
        } finally {
            cursor.close();
            db.close();
        }
                
    }
    private Response fakeResponse() {
        return new Response("", 200, "", new ArrayList<Header>(), null);
    }

    @Override
    public void getUsuario(String login, Callback<Usuario> callback) {
        SQLiteDatabase db = this.sqlite.getReadableDatabase();
        Cursor cursor = db.query(true, "Usuarios", null, "login = ?", new String[]{login}, null, null, "login", null);
        cursor.moveToFirst();
        Usuario u = null;
        if (!cursor.isAfterLast()) {
          u = toUsuario(cursor);
        }
        cursor.close();
        db.close();
        callback.success(u, fakeResponse());        
    }

    @Override
    public void deleteUsuario(String login, Callback<Void> callback) {
        SQLiteDatabase db = this.sqlite.getWritableDatabase();
        db.delete("Usuarios", "login = ?", new String[]{login});
        db.close();
        callback.success(null, fakeResponse());
    }

    @Override
    public void postUsuario(String login, Usuario usuario, Callback<Void> callback) {
        SQLiteDatabase db = this.sqlite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("login", usuario.getLogin());
        cv.put("password", usuario.getPassword());
        cv.put("correo", usuario.getEmail());
        cv.put("apellido", usuario.getApellido());
        cv.put("nombre", usuario.getNombre());
        
        db.insertOrThrow("Usuarios", null, cv);
        db.close();
        
        callback.success(null, fakeResponse());
    }

    @Override
    public void updateUsuarios(Usuario usuario, Callback<Void> callback) {
        SQLiteDatabase db = this.sqlite.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("login", usuario.getLogin());
        cv.put("password", usuario.getPassword());
        cv.put("correo", usuario.getEmail());
        cv.put("apellido", usuario.getApellido());
        cv.put("nombre", usuario.getNombre());
        
        db.update("Usuarios", cv, "login = ?", new String[]{usuario.getLogin()});
        db.close();
        callback.success(null, fakeResponse());        
    }
}
