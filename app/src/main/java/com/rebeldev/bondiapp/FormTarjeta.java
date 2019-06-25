package com.rebeldev.bondiapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Tarjeta;

public class FormTarjeta extends AppCompatActivity {
    private EditText etNumero;
    private EditText etNombre;
    private EditText etSaldo;
    private TextView tvTitulo;

    private Tarjeta tarjeta;
    private int numTarjeta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tarjeta);

        numTarjeta = getIntent().getIntExtra("numTarjeta", 0);

        tvTitulo = findViewById(R.id.tvTitulo);
        etNumero = findViewById(R.id.etNumero);
        etNombre = findViewById(R.id.etNombre);
        etSaldo = findViewById(R.id.etSaldo);

        if(numTarjeta == 0){
            tarjeta = new Tarjeta();
            tvTitulo.setText("Agregar nueva tarjeta");
        } else {
            tarjeta = Tarjeta.buscaPorNum(this,numTarjeta);
            Toast.makeText(this, "#" + tarjeta.getNombre(), Toast.LENGTH_SHORT).show();
            tvTitulo.setText("Editar " + tarjeta.getNombre());
            etNumero.setText(String.valueOf(tarjeta.getNumero()));
            etNombre.setText(tarjeta.getNombre());
            etSaldo.setText(String.valueOf(tarjeta.getSaldo()));
            etNumero.setEnabled(false);//No se puede editar el numero de la tarjeta
        }

    }//onCreate()

    //Métodos para botones
    public void limpiarForm(View v){
        etNombre.setText("");
        etSaldo.setText("");
        if(numTarjeta == 0){
            etNumero.setText("");
        }
    }//limpiarForm()

    public void volver(View v){
        finish();
    }

    public void guardar(View v){
        if(numTarjeta == 0){
            this.crear();
        } else {
            this.actualizar();
        }
    }//agregar()

    private void crear(){
        String nombre = etNombre.getText().toString();
        String numero = etNumero.getText().toString();
        String saldo = etSaldo.getText().toString();
        Tarjeta tarjeta = new Tarjeta();

        if(!nombre.isEmpty() && !numero.isEmpty()){
            tarjeta.setNumero(Integer.valueOf(numero));
            tarjeta.setNombre(nombre);
            if(saldo.isEmpty()){
                //Para evitar errores, si está vacío se setea en 0
                tarjeta.setSaldo(0.0f);
            } else {
                tarjeta.setSaldo(Float.valueOf(saldo));
            }

            if (tarjeta.crear(this)){
                Toast.makeText(this, "Tarjeta agregada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Debe llenar todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }//crear()

    private void actualizar(){
        String nombre = etNombre.getText().toString();
        String numero = etNumero.getText().toString();
        String saldo = etSaldo.getText().toString();
        Tarjeta tarjeta = new Tarjeta();

        if(!nombre.isEmpty()){
            tarjeta.setNombre(nombre);
            tarjeta.setNumero(Integer.valueOf(numero));
            if(saldo.isEmpty()){
                //Para evitar errores, si está vacío se setea en 0
                tarjeta.setSaldo(0.0f);
            } else {
                tarjeta.setSaldo(Float.valueOf(saldo));
            }

            if (tarjeta.actualizar(this)){
                //Toast.makeText(this, "Tarjeta guardada correctamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Debe llenar todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }
}
