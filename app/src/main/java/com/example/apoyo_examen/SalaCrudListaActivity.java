package com.example.apoyo_examen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.apoyo_examen.adapter.SalaAdapter;
import com.example.apoyo_examen.api.ServiceSalaApi;
import com.example.apoyo_examen.entity.Sala;
import com.example.apoyo_examen.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalaCrudListaActivity extends AppCompatActivity {

        List<Sala> lstData = new ArrayList<Sala>();
        SalaAdapter adaptador = null;
        ListView lstView = null;
        ServiceSalaApi api =null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sala_crud_lista);

            lstView = findViewById(R.id.idCrudSalaList);
            adaptador = new SalaAdapter(this,R.layout.activity_sala_crud_item,lstData);
            lstView.setAdapter(adaptador);

            api= ConnectionRest.getConnection().create(ServiceSalaApi.class);

            lista();
        }

        public void lista(){
                mensaje("LoG ->  Respuesta 1");
             Call<List<Sala>> call=api.listaSala();
             call.enqueue(new Callback<List<Sala>>() {
                     @Override
                     public void onResponse(Call<List<Sala>> call, Response<List<Sala>> response) {
                             mensaje("LoG ->  Respuesta 2");
                              if(response.isSuccessful()){
                                      mensaje("LoG ->  Respuesta 3");
                                      List<Sala> lista = response.body();
                                  mensaje("LoG -> size"+lista.size());
                                      lstData.clear();
                                      lstData.addAll(lista);
                                      adaptador.notifyDataSetChanged();

                              }else{
                                      mensaje("Error ->" + "Error en la Respuesta");
                              }
                     }

                     @Override
                     public void onFailure(Call<List<Sala>> call, Throwable t) {
                         mensaje("ERROR ->" + " Error en la respuesta");
                     }
             });
        }

        void mensaje(String msg){
                Toast toast1 =  Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_LONG);
                toast1.show();
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.activity_menu, menu);
                return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            int id = item.getItemId();
            if (id == R.id.idMenuCrudSala) {
                Intent intent = new Intent(this, SalaCrudListaActivity.class);
                startActivity(intent);
                return true;
            }

            return super.onOptionsItemSelected(item);
        }


}
