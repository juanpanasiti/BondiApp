package com.rebeldev.bondiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Tarjeta;

public class FormTarjeta extends AppCompatActivity {
    private EditText etNumero;
    private EditText etNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tarjeta);

        etNumero = findViewById(R.id.etNumero);
        etNombre = findViewById(R.id.etNombre);

    }
    
    //Métodos para botones
    public void limpiarForm(View v){
        etNombre.setText("");
        etNumero.setText("");
    }

    public void volver(View v){
        finish();
    }

    public void agregar(View v){
        String nombre = etNombre.getText().toString();
        String numero = etNumero.getText().toString();
        Tarjeta tarjeta = new Tarjeta();

        if(!nombre.isEmpty() && !numero.isEmpty()){
            tarjeta.setNumero(Integer.valueOf(numero));
            tarjeta.setNombre(nombre);
            tarjeta.setSaldo(0.0f);

            if (tarjeta.crear(this)){
                Toast.makeText(this, "Tarjeta agregada correctamente", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debe llenar todos los campos.", Toast.LENGTH_SHORT).show();
        }
        finish();
    }//agregar()
}
