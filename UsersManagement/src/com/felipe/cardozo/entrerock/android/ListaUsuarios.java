package com.felipe.cardozo.entrerock.android;

import java.util.ArrayList;
import java.util.List;

import com.example.android.skeletonapp.R;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaUsuarios extends Activity {
    private final List<String> usuarios = new ArrayList<String>();
    private StableArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
            setContentView(R.layout.activity_lista_usuarios);
            
            ListView listView = (ListView) findViewById(R.id.usersListView);
            registerForContextMenu(listView);
            
            adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, usuarios);
            listView.setAdapter(adapter);

            recargarDataSet();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
    
    private void recargarDataSet() {
        ApiUsuarios api = new LocalApiUsuarios(this);
        api.getUsuarios(new Callback<List<Usuario>>() {

            @Override
            public void success(List<Usuario> users, Response arg1) {
                try {
                    usuarios.clear();
                    for (Usuario u : users) {
                        usuarios.add(u.getLogin());
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError e) {
                e.printStackTrace();
            }
        });    
    }

    private String getServiceApi() {
        return ((UserManagementApplication)  getApplication()).getServiceAPI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lista_usuarios, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().toString().equals("Eliminar")) {
         // view.animate().setDuration(2000).alpha(0)

            ApiUsuarios api = new LocalApiUsuarios(this);
            api.deleteUsuario(actionUser, new Callback<Void>() {
                @Override
                public void failure(RetrofitError e) {
                    e.printStackTrace();
                }

                @Override
                public void success(Void arg0, Response arg1) {
                    recargarDataSet();
                }                
            });        
        }
        if (item.getTitle().toString().equals("Actualizar")) {
            // TODO: inicializar la vista con la información de ese usuario
            Intent i = new Intent(ListaUsuarios.this, UserRegistrationActivity.class);
            i.putExtra("usuario_editado", actionUser);
            startActivity(i); 
            finish();
        }
        return false;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.usersListView) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterContextMenuInfo ) menuInfo;
            
            actionUser = (String) lv.getItemAtPosition(acmi.position);
            
            menu.add("Actualizar");
            menu.add("Eliminar");
        }
    }
    private String actionUser;
}

class StableArrayAdapter extends ArrayAdapter<String> {
    private final List<String> objects;
    public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        this.objects = objects;
        
        
    }
    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return objects.indexOf(item);
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
}

