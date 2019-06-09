package com.rebeldev.bondiapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rebeldev.bondiapp.database.AdminSQLiteOpenHelper;
import com.rebeldev.bondiapp.modelo.Tarjeta;

public class nuevaTarjeta extends AppCompatActivity {
    private EditText etNumero;
    private EditText etNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_tarjeta);

        etNumero = findViewById(R.id.etNumero);
        etNombre = findViewById(R.id.etNombre);

    }

    public void limpiarForm(View v){
        etNombre.setText("");
        etNumero.setText("");
    }

    public void volver(View v){
        finish();
    }

    public void agregar(View v){
        //AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"bondiapp",null,1);
        //SQLiteDatabase db = admin.getWritableDatabase();

        String nombre = etNombre.getText().toString();
        String numero = etNumero.getText().toString();
        Tarjeta tarjeta = new Tarjeta();

        if(!nombre.isEmpty() && !numero.isEmpty()){
            //ContentValues reg = new ContentValues();

            //reg.put("numero",numero);
            //reg.put("nombre",nombre);
            //reg.put("saldo",0.0f);

            //db.insert("tarjetas",null,reg);
            //db.close();

            tarjeta.setNumero(Integer.valueOf(numero));
            tarjeta.setNombre(nombre);
            tarjeta.setSaldo(0.0f);

            tarjeta.create(this);

            etNumero.setText("");
            etNombre.setText("");

            Toast.makeText(this, "Tarjeta agregada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al agregar la tarjeta", Toast.LENGTH_SHORT).show();
        }
        finish();
    }//agregar()
}
