/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.felipe.cardozo.entrerock.android;

import com.example.android.skeletonapp.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class UserRegistrationActivity extends Activity {
    

    public UserRegistrationActivity() {}

    private String getServiceApi() {
        return ((UserManagementApplication)  getApplication()).getServiceAPI();
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.skeleton_activity);
        ((Button) findViewById(R.id.registrarme)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String login = ((EditText) findViewById(R.id.login)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();
                String correo = ((EditText) findViewById(R.id.correo)).getText().toString();
                String nombre = ((EditText) findViewById(R.id.nombre)).getText().toString();
                String apellido = ((EditText) findViewById(R.id.apellido)).getText().toString();

                ApiUsuarios api = new LocalApiUsuarios(UserRegistrationActivity.this);
                api.postUsuario(login, new Usuario(login, password, nombre, apellido, correo), new Callback<Void>() {

                    @Override
                    public void success(Void arg0, Response arg1) {
                        Intent i = new Intent(UserRegistrationActivity.this, ListaUsuarios.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError arg0) {
                        arg0.printStackTrace();
                    }
                });
            }
        });

        ((Button) findViewById(R.id.actualizar)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String login = ((EditText) findViewById(R.id.login)).getText().toString();
                String password = ((EditText) findViewById(R.id.password)).getText().toString();
                String correo = ((EditText) findViewById(R.id.correo)).getText().toString();
                String nombre = ((EditText) findViewById(R.id.nombre)).getText().toString();
                String apellido = ((EditText) findViewById(R.id.apellido)).getText().toString();

                ApiUsuarios api = new LocalApiUsuarios(UserRegistrationActivity.this);
                api.updateUsuarios(new Usuario(login, password, nombre, apellido, correo), new Callback<Void>() {

                    @Override
                    public void success(Void arg0, Response arg1) {
                        Intent i = new Intent(UserRegistrationActivity.this, ListaUsuarios.class);
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError e) {
                        e.printStackTrace();
                    }
                });
            }
        });

        ((Button) findViewById(R.id.clear)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(UserRegistrationActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString("usuario_editado") != null) {
            String usuarioEditado = extras.getString("usuario_editado");
            ApiUsuarios api = new LocalApiUsuarios(this);
            api.getUsuario(usuarioEditado, new Callback<Usuario>() {
                @Override
                public void success(Usuario l, Response arg1) {
                    ((EditText) findViewById(R.id.login)).setText(l.getLogin());
                    ((EditText) findViewById(R.id.password)).setText(l.getPassword());
                    ((EditText) findViewById(R.id.correo)).setText(l.getEmail());
                    ((EditText) findViewById(R.id.nombre)).setText(l.getNombre());
                    ((EditText) findViewById(R.id.apellido)).setText(l.getApellido());
                }

                @Override
                public void failure(RetrofitError arg0) {
                    arg0.printStackTrace();
                }
            });
            ((EditText) findViewById(R.id.login)).setEnabled(false);
            ((Button) findViewById(R.id.registrarme)).setVisibility(Button.INVISIBLE);
            ((Button) findViewById(R.id.clear)).setVisibility(Button.INVISIBLE);
            ((Button) findViewById(R.id.actualizar)).setVisibility(Button.VISIBLE);
        } else {
            ((EditText) findViewById(R.id.login)).setEnabled(true);
            ((Button) findViewById(R.id.registrarme)).setVisibility(Button.VISIBLE);
            ((Button) findViewById(R.id.clear)).setVisibility(Button.VISIBLE);
            ((Button) findViewById(R.id.actualizar)).setVisibility(Button.INVISIBLE);

        }
    }
}
