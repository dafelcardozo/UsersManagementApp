package com.felipe.cardozo.entrerock.android;

import java.util.List;

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

public class ConfigurationActivity extends Activity {
    private static final String DEFAULT_API = "http://192.168.0.13/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        EditText serviceUrl = (EditText) findViewById(R.id.serviceUrl);
        serviceUrl.setText(DEFAULT_API);

        ((Button) findViewById(R.id.continuar)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText serviceUrl = (EditText) findViewById(R.id.serviceUrl);
                final String service_api = serviceUrl.getText().toString();
                RestAdapter restAdapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(service_api).build();
                ApiUsuarios api = restAdapter.create(ApiUsuarios.class);
                api.getUsuarios(new Callback<List<Usuario>>() {

                    @Override
                    public void success(List<Usuario> users, Response arg1) {
                        ((UserManagementApplication) getApplication()).setServiceAPI(service_api);

                        Intent i = new Intent(ConfigurationActivity.this, LoginActivity.class);
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
    }
}
