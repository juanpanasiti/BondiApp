package com.rebeldev.bondiapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Micro;
import com.rebeldev.bondiapp.modelo.MicroParada;
import com.rebeldev.bondiapp.modelo.Parada;

import java.util.ArrayList;

public class AgregarMicroAParada extends AppCompatActivity {
    private TextView tvTitulo;
    private ListView lvMicros;
    private Parada parada;
    private ArrayList<Micro> micros = new ArrayList<>();
    private ArrayList<String> nombresMicros = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_micro_aparada);

        parada = Parada.buscaPorID(this,getIntent().getIntExtra("idParada",0));
        tvTitulo = findViewById(R.id.tvTitulo);
        lvMicros = findViewById(R.id.lvMicros);

        setTitulo();
        llenarArrays();
    }//onCreate()
    //Métodos privados
    private void llenarArrays(){
        this.micros.clear();
        this.nombresMicros.clear();
        this.micros = parada.buscarMicrosParaAgregar(this);
        for(Micro micro : micros){
            this.nombresMicros.add(micro.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_micros, nombresMicros);
        lvMicros.setAdapter(adapter);

        lvMicros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Agregar micro_parada
                MicroParada mp = new MicroParada();
                Micro micro = micros.get(position);
                mp.setLineaMicro(micro.getLinea());
                mp.setIdParada(parada.getID());

                if(mp.crear(getApplicationContext())){
                    Toast.makeText(AgregarMicroAParada.this, "Micro agregado a la parada.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }//llenarArrays()

    private void setTitulo(){
        tvTitulo.setText("Agregar micro a " + parada.getDireccion());
    }

    //Metodos botones
    public void volver(View v){
        finish();
    }
}
