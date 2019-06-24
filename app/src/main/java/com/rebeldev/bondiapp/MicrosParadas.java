package com.rebeldev.bondiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MicrosParadas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micros_paradas);
    }

    //Metodos botones
    public void aMicros(View v){
        Intent aMicros = new Intent(this, Micros.class);
        startActivity(aMicros);
    }

    public void aParadas(View v){
        Intent aParadas = new Intent(this, Paradas.class);
        startActivity(aParadas);
    }

    public void volver(View v){
        finish();
    }
}
