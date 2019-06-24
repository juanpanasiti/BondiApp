package com.rebeldev.bondiapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rebeldev.bondiapp.database.AdminSQLiteOpenHelper;
import com.rebeldev.bondiapp.modelo.Tarjeta;

import java.util.ArrayList;

public class Tarjetas extends AppCompatActivity {
    private ListView lvTarjetas;
    private ArrayList<Tarjeta> tarjetas = new ArrayList<>();
    private ArrayList<String> nombres = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas);
        lvTarjetas = findViewById(R.id.lv_tarjetas);
    }//onCreate()

    @Override
    protected void onStart() {
        super.onStart();
        nombres.clear();
        llenarNombres();
    }//onStart()

    //Metodos privados
    private void llenarNombres(){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"bondiapp",null,1);
        SQLiteDatabase db = admin.getReadableDatabase();

        String tableName = "tarjeta";
        String[] columns = {"numero_tar", "nombre_tar", "saldo_tar"};
        Cursor cursor = db.query(tableName, columns, null, null,null,null,null);

        while (cursor.moveToNext()){
            Tarjeta t = new Tarjeta(cursor.getString(1),cursor.getInt(0));
            t.setSaldo(cursor.getFloat(2));
            tarjetas.add(t);
            nombres.add(t.getNombre() + " $" + String.format("%.2f",t.getSaldo()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_tarjetas, nombres);
        lvTarjetas.setAdapter(adapter);

        lvTarjetas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent verTarjeta = new Intent(view.getContext(), VerTarjeta.class);
                verTarjeta.putExtra("numeroTarjeta", tarjetas.get(position).getNumero());
                startActivity(verTarjeta);
            }
        });

    }//llenarNombres

    //Métodos para botones
    public void agregarTarjeta(View v){
        Intent aNuevaTarjeta = new Intent(this, FormTarjeta.class);
        startActivity(aNuevaTarjeta);
    }//agregarTarjeta()

    public void volver(View v){
        finish();
    }
}
