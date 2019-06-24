package com.rebeldev.bondiapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class VerInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_info);
    }

    //Metodos botones
    public void aHistViajes(View v){
        Intent aHV = new Intent(this, VerViajes.class);
        startActivity(aHV);
    }

    public void aConsumos(View v){
        Intent aCons = new Intent(this, VerConsumo.class);
        startActivity(aCons);
    }

    public void volver(View v){
        finish();
    }
}
