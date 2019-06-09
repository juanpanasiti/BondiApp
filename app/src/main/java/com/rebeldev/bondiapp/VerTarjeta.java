package com.rebeldev.bondiapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rebeldev.bondiapp.database.AdminSQLiteOpenHelper;
import com.rebeldev.bondiapp.modelo.Tarjeta;

public class VerTarjeta extends AppCompatActivity {

    private TextView tvNombre;
    private TextView tvSaldo;
    private TextView tvNumero;
    private int numeroTarjeta;
    private Tarjeta tarjeta;// = new Tarjeta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tarjeta);

        tvNombre = findViewById(R.id.tvNombre);
        tvSaldo = findViewById(R.id.tvSaldo);
        tvNumero = findViewById(R.id.tvNumero);

        numeroTarjeta = getIntent().getIntExtra("numeroTarjeta",0);

        setElementos();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setElementos();
    }


    public void volver(View v){
        finish();
    }

    public void borrar(View v){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "bondiapp",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();

        db.delete("tarjetas","numero=" + tarjeta.getNumero(),null);
        db.close();
        Toast.makeText(this, "Se eliminó la tarjeta correctamente", Toast.LENGTH_SHORT).show();
        finish();//Cerrar activity
    }

    public void aCargaSaldo(View v){
        Intent aCarga = new Intent(this, CargarSaldo.class);
        aCarga.putExtra("numeroTarjeta", this.tarjeta.getNumero());
        startActivity(aCarga);
    }

    private void setElementos(){
        tarjeta = Tarjeta.findByNum(this,getIntent().getIntExtra("numeroTarjeta",-1));

        //Settear textos a elementos de la vista
        tvNombre.setText(tarjeta.getNombre());
        tvSaldo.setText("$" + String.format("%.2f",tarjeta.getSaldo()));
        tvNumero.setText("#" + tarjeta.getNumero());
    }
}
