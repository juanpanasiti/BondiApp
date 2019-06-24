package com.rebeldev.bondiapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.rebeldev.bondiapp.modelo.Micro;

import java.util.ArrayList;

public class Micros extends AppCompatActivity {
    private TextView tvTitulo;
    private ListView lvMicros;
    private ArrayList<Micro> micros = new ArrayList<>();
    private ArrayList<String> nombresMicros = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micros);
        tvTitulo = findViewById(R.id.tvTitulo);
        lvMicros = findViewById(R.id.lvMicros);
    }//onCreate()

    @Override
    protected void onStart() {
        super.onStart();
        llenarArrays();
    }//onStart()

    //Métodos privados
    private void llenarArrays(){
        this.micros.clear();
        this.nombresMicros.clear();
        this.micros = Micro.buscarTodos(this);
        for(Micro micro : micros){
            this.nombresMicros.add(micro.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_micros, nombresMicros);
        lvMicros.setAdapter(adapter);

        lvMicros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent verMicro = new Intent(view.getContext(), VerMicro.class);
                verMicro.putExtra("lineaMicro", micros.get(position).getLinea());
                startActivity(verMicro);
            }
        });
    }//llenarArrays()

    //Metodos botones
    public void aFormMicro(View v){
        Intent aNuevoMicro = new Intent(this, FormMicro.class);
        startActivity(aNuevoMicro);
    }//aFormMicro

    public void volver(View v){
        finish();
    }//volver()
}
