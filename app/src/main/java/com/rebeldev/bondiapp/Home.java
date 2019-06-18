package com.rebeldev.bondiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    //Acciones a otras activities
    public void aTarjetas(View v){
        Intent aConfig = new Intent(this, Tarjetas.class);
        startActivity(aConfig);
    }

    public void aMicros(View v){
        Intent aConfig = new Intent(this, Configuracion.class);
        startActivity(aConfig);
    }

    public void aTarifas(View v){
        Intent aConfig = new Intent(this, Configuracion.class);
        startActivity(aConfig);
    }
}
