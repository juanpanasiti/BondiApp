package com.rebeldev.bondiapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Micro;

public class VerMicro extends AppCompatActivity {
    private TextView tvTitulo;
    private ImageView ivColorLine;

    private Micro micro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_micro);

        ivColorLine = findViewById(R.id.ivColorLine);
        tvTitulo = findViewById(R.id.tvTitulo);
    }//onCreate()

    @Override
    protected void onStart() {
        super.onStart();
        micro = Micro.buscaPorLinea(this,getIntent().getIntExtra("lineaMicro",-1));
        ivColorLine.setColorFilter(Color.parseColor(micro.getColorHexa()));
        tvTitulo.setText(micro.toString());

    }//onStart
    //Metodos botones
    public void aFormMicro(View v){
        Intent aFM = new Intent(this, FormMicro.class);
        startActivity(aFM);
    }

    public void pagar(View v){
        //finish();
        Intent aPagoBoleto = new Intent(this, PagarBoleto.class);
        startActivity(aPagoBoleto);
    }
    public void borrar(View v){
        if(micro.borrar(this)){
            Toast.makeText(this, "Micro eliminado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }//borrar()

    public void volver(View v){
        finish();
    }
}
