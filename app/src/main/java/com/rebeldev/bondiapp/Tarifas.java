package com.rebeldev.bondiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Boleto;

import java.util.ArrayList;

public class Tarifas extends AppCompatActivity {
    private ListView lvTarifas;
    private ArrayList<Boleto> boletos = new ArrayList<>();
    private ArrayList<String> tarifas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarifas);

        lvTarifas = findViewById(R.id.lvTarifas);
    }//onCreate()

    @Override
    protected void onStart(){
        super.onStart();
        tarifas.clear();
        llenarBoletos();
    }//onStart()

    //Métodos privados
    private void llenarBoletos(){
        boletos = Boleto.buscarTodos(this);

        //llenar tarifas con los datos de los boletos
        for(Boleto bol : boletos){
            String tarifa = bol.getDescripcion() + " ($" + String.format("%.2f",bol.getPrecio()) + ")";
            tarifas.add(tarifa);
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_tarifas, tarifas);
        lvTarifas.setAdapter(adapter);

        lvTarifas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Tarifas.this, "Accion no programada aún.", Toast.LENGTH_SHORT).show();
            }
        });
        
    }//llenarBoletos

    //Metodos botones
    public void aFormTarifa(View v){
        Intent aFT = new Intent(this, FormTarifa.class);
        startActivity(aFT);
    }

    public void volver(View v){
        finish();
    }
}
