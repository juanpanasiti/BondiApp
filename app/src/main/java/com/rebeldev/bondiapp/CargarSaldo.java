package com.rebeldev.bondiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Tarjeta;

public class CargarSaldo extends AppCompatActivity {
    private TextView tvNombreTarjeta;
    private TextView tvSaldoActual;
    private EditText etCargar;
    private Tarjeta tarjeta;

    private int numeroTarjeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_saldo);

        tvNombreTarjeta = findViewById(R.id.tvTarjeta);
        tvSaldoActual = findViewById(R.id.tvSaldoActual);
        etCargar = findViewById(R.id.etCarga);

        numeroTarjeta = getIntent().getIntExtra("numeroTarjeta",-1);
        setTarjeta();
        tvNombreTarjeta.setText(tarjeta.getNombre() + " #" + tarjeta.getNumero());
        tvSaldoActual.setText("Saldo actual: $" + String.format("%.2f",tarjeta.getSaldo()));

    }

    private void setTarjeta(){
        if(numeroTarjeta != -1){
            tarjeta = Tarjeta.buscaPorNum(this,numeroTarjeta);
        } else {
            tarjeta = new Tarjeta();
            tarjeta.setNumero(-1);
            tarjeta.setNombre("Error");
            tarjeta.setSaldo(-1f);
            Toast.makeText(this, "Error al cargar tarjeta " + numeroTarjeta, Toast.LENGTH_SHORT).show();
        }
    }//setTarjeta()

    public void volver(View v){
        finish();
    }//volver()

    public void cargar(View v){
        float carga = Float.valueOf(etCargar.getText().toString());
        tarjeta.cargar(carga);

        if(tarjeta.actualizar(this)){
            Toast.makeText(this, "Cargaste $" + String.format("%.2f",carga) + ", en total tenés $" + String.format("%.2f",tarjeta.getSaldo()), Toast.LENGTH_SHORT).show();
            finish();
        }
    }//cargar()
}
