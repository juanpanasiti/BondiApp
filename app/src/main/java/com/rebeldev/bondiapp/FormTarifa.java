package com.rebeldev.bondiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Boleto;

public class FormTarifa extends AppCompatActivity {
    private EditText etDescripcion;
    private EditText etPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tarifa);

        etDescripcion = findViewById(R.id.etDescripcion);
        etPrecio = findViewById(R.id.etPrecio);
    }

    //Métodos para botones
    public void volver(View v){
        finish();
    }

    public void guardar(View v){
        String descripcion = etDescripcion.getText().toString();
        String precio = etPrecio.getText().toString();
        Boleto boleto = new Boleto();

        if(!descripcion.isEmpty() && !precio.isEmpty()){
            boleto.setDescripcion(descripcion);
            boleto.setPrecio(Float.valueOf(precio));
            if (boleto.crear(this)){
                Toast.makeText(this, "Boleto agregado correctamente", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debe llenar todos los campos.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }//agregar()
}
