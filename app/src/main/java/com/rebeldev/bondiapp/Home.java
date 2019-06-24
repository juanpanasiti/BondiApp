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
        Intent aTarj = new Intent(this, Tarjetas.class);
        startActivity(aTarj);
    }

    public void aMicrosParadas(View v){
        Intent aMP = new Intent(this, MicrosParadas.class);
        startActivity(aMP);
    }

    public void aTarifas(View v){
        Intent aTarif = new Intent(this, Tarifas.class);
        startActivity(aTarif);
    }

    public void aVerInfo(View v){
        Intent aInfo = new Intent(this, VerInfo.class);
        startActivity(aInfo);
    }

    public void pagarBoleto(View v){
        Intent aPagarBoleto = new Intent(this, PagarBoleto.class);
        startActivity(aPagarBoleto);
    }
}
