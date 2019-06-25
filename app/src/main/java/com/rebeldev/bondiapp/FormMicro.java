package com.rebeldev.bondiapp;

import android.app.Activity;
import android.content.Intent;
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

    private int lineaMicro;
    private Micro micro;
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

        lineaMicro = getIntent().getIntExtra("lineaMicro",-1);
        if(lineaMicro == -1){
            micro = new Micro();
            tvTitulo.setText("Nuevo Micro");
        } else {
            micro = Micro.buscaPorLinea(this,lineaMicro);
            tvTitulo.setText("Editar micro " + micro.getLinea());
            etLinea.setText(String.valueOf(micro.getLinea()));
            etDescripcion.setText(micro.getDescripcion());
        }

        llenarSpinnerColores();


    }//onCreate()

    //Métodos privados
    private void llenarSpinnerColores(){
        int pos = 0;
        int encontrado = 0;
        for (Color color : Micro.getColores()){
            coloresHexa.add(color.getHexa());
            coloresNombre.add(color.getDescripcion());
            if(!color.getDescripcion().equals(micro.getColor())){
                pos++;
            } else {
                encontrado = pos;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item_colores_micros, coloresNombre);
        spColores.setAdapter(adapter);
        if(lineaMicro != -1){
            spColores.setSelection(encontrado);
        }
    }//llenarColores()
    //Metodos botones
    public void volver(View v){
        if(lineaMicro == -1){
            finish();
        } else {
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }
    }

    public void guardar(View v){
        int linea = Integer.valueOf(etLinea.getText().toString());
        String color = coloresNombre.get(spColores.getSelectedItemPosition());
        String descripcion = etDescripcion.getText().toString();
        if(!etLinea.getText().toString().isEmpty()){
            Micro micro = new Micro(linea, color);
            micro.setDescripcion(descripcion);
            if(lineaMicro == -1){
                if(micro.crear(this)){
                    Toast.makeText(this, "Creado micro linea " + linea + " " + descripcion + " " + color, Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                if(micro.actualizar(this, lineaMicro)){
                    Toast.makeText(this, "Guardado micro linea " + linea + " " + descripcion + " " + color, Toast.LENGTH_SHORT).show();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("lineaMicro",micro.getLinea());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        } else {
            Toast.makeText(this, "La linea es un campo obligatorio", Toast.LENGTH_SHORT).show();
        }

    }//guardar()
}
