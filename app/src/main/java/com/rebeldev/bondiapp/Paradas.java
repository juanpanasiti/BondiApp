package com.rebeldev.bondiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Parada;

import java.util.ArrayList;

public class Paradas extends AppCompatActivity {
    private ListView lvParadas;
    private ArrayList<Parada> paradas = new ArrayList<>();
    private ArrayList<String> direcciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paradas);

        lvParadas = findViewById(R.id.lvParadas);
    }//onCreate()

    @Override
    protected void onStart() {
        super.onStart();
        llenarArrays();
    }

    //Métodos privados
    private void llenarArrays(){
        this.paradas.clear();
        this.direcciones.clear();
        this.paradas = Parada.buscarTodos(this);
        for(Parada par : paradas){
            this.direcciones.add(par.getDireccion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_paradas, direcciones);
        lvParadas.setAdapter(adapter);

        lvParadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent verParada = new Intent(view.getContext(), VerParada.class);
                verParada.putExtra("idParada", paradas.get(position).getID());
                startActivity(verParada);
            }
        });
    }//llenarArrays()
    //Metodos botones
    public void aFormParada(View v){
        Intent aFP = new Intent(this, FormParada.class);
        startActivity(aFP);
    }

    public void volver(View v){
        finish();
    }
}
