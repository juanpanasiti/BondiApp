package com.rebeldev.bondiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Parada;

public class FormParada extends AppCompatActivity {
    private TextView tvTitulo;
    private EditText etDireccion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_parada);

        tvTitulo = findViewById(R.id.tvTitulo);
        etDireccion = findViewById(R.id.etDireccion);


    }//onCreate()

    //Metodos botones
    public void volver(View v){
        finish();
    }

    public void guardar(View v){
        String direccion = etDireccion.getText().toString();
        Parada parada = new Parada();
        if(!direccion.isEmpty()){
            parada.setDireccion(direccion);
            if(parada.crear(this)){
                Toast.makeText(this, "Parada agregada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }//guardar()
}
