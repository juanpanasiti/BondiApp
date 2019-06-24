package com.rebeldev.bondiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Color;
import com.rebeldev.bondiapp.modelo.Micro;

import java.util.ArrayList;

public class FormMicro extends AppCompatActivity {
    private TextView tvTitulo;
    private EditText etLinea;
    private EditText etDescripcion;
    private Spinner spColores;
    private ArrayList<String> coloresHexa;
    private ArrayList<String> coloresNombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_micro);

        etLinea = findViewById(R.id.etLinea);
        etDescripcion = findViewById(R.id.etDescripcion);
        tvTitulo = findViewById(R.id.tvTitulo);
        spColores = findViewById(R.id.spColores);
        coloresHexa = new ArrayList<>();
        coloresNombre = new ArrayList<>();

        tvTitulo.setText("Nuevo Micro");
        llenarColores();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, coloresNombre);

        spColores.setAdapter(adapter);
    }//onCreate()

    //Métodos privados
    private void llenarColores(){
        for (Color color : Micro.getColores()){
            coloresHexa.add(color.getHexa());
            coloresNombre.add(color.getDescripcion());
        }
    }//llenarColores()
    //Metodos botones
    public void volver(View v){
        finish();
    }

    public void guardar(View v){
        int linea = Integer.valueOf(etLinea.getText().toString());
        String color = coloresNombre.get(spColores.getSelectedItemPosition());
        String descripcion = etDescripcion.getText().toString();
        if(!etLinea.getText().toString().isEmpty()){
            Micro micro = new Micro(linea, color);
            micro.setDescripcion(descripcion);
            if(micro.crear(this)){
                Toast.makeText(this, "Creado micro linea " + linea + " " + descripcion + " " + color, Toast.LENGTH_SHORT).show();
            }
        finish();
        } else {
            Toast.makeText(this, "La linea es un campo obligatorio", Toast.LENGTH_SHORT).show();
        }

    }//guardar()
}
