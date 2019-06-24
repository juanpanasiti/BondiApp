package com.rebeldev.bondiapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Boleto;
import com.rebeldev.bondiapp.modelo.Micro;
import com.rebeldev.bondiapp.modelo.Tarjeta;

import java.util.ArrayList;
import java.util.Calendar;

public class PagarBoleto extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private Spinner spTarjetas;
    private Spinner spTarifas;
    private Spinner spMicros;
    private ArrayList<Tarjeta> tarjetas;
    private ArrayList<String> nombresTar;
    private ArrayList<Boleto> boletos;
    private ArrayList<String> tarifas;
    private ArrayList<Micro> micros;
    private ArrayList<String> nombresMic;
    private TextView tvFecha;
    private TextView tvHora;
    private ImageButton ibFecha;
    private ImageButton ibHora;
    private Button btnPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_boleto);

        spTarjetas = findViewById(R.id.spTarjetas);
        spTarifas = findViewById(R.id.spTarifas);
        spMicros = findViewById(R.id.spMicros);
        tarjetas = Tarjeta.buscarTodos(this);
        nombresTar = new ArrayList<>();
        boletos = Boleto.buscarTodos(this);
        tarifas = new ArrayList<>();
        micros = Micro.buscarTodos(this);
        nombresMic = new ArrayList<>();
        tvFecha = findViewById(R.id.tvFecha);
        tvHora = findViewById(R.id.tvHora);
        ibFecha = findViewById(R.id.ibFecha);
        ibHora = findViewById(R.id.ibHora);
        btnPagar = findViewById(R.id.btnPagar);

        ibFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        ibHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        llenarCampos();
        habilitarBotonPagar();
    }//onCreate()

    //Métodos privados
    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }//showDatePickerDialog()

    private void showTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }//showTimePickerDialog()

    private void llenarCampos(){
        //Llenar spinner de tarjetas
        nombresTar.clear();
        if(tarjetas.size() > 0){
            for(Tarjeta tarjeta : tarjetas){
                nombresTar.add(tarjeta.getNombre() + " $" + String.format("%.2f",tarjeta.getSaldo()));
            }
            ArrayAdapter<String> adapterTarjetas = new ArrayAdapter<String>(this,R.layout.spinner_item_general, nombresTar);
            spTarjetas.setAdapter(adapterTarjetas);
        }

        //Llenar spinner de tarifas
        tarifas.clear();
        if(boletos.size() > 0){
            for(Boleto boleto : boletos){
                tarifas.add(boleto.getDescripcion() + " ($" + String.format("%.2f",boleto.getPrecio()) + ")");
            }
            ArrayAdapter<String> adapterTarifas = new ArrayAdapter<String>(this,R.layout.spinner_item_general, tarifas);
            spTarifas.setAdapter(adapterTarifas);
        }

        //Llenar spinner de micros
        nombresMic.clear();
        if(micros.size() > 0){
            for(Micro micro : micros){
                nombresMic.add(micro.toString());
            }
            ArrayAdapter<String> adapterMicros = new ArrayAdapter<String>(this,R.layout.spinner_item_general, nombresMic);
            spMicros.setAdapter(adapterMicros);
        }

        //Setear fecha y hora actual
        String fecha = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR);
        String hora = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + Calendar.getInstance().get(Calendar.MINUTE);
        tvFecha.setText(fecha);
        tvHora.setText(hora);

    }//llenarCampos()

    private void habilitarBotonPagar(){
        if(!(tarjetas.size() > 0 && tarifas.size() > 0 && micros.size() > 0)){
            btnPagar.setEnabled(false);
        }
    }//habilitarBotonPagar()
    //Metodos botones
    public void pagar(View v){
        Tarjeta tarSelec = tarjetas.get(spTarjetas.getSelectedItemPosition());
        Boleto bolSelec = boletos.get(spTarifas.getSelectedItemPosition());
        if (tarSelec.pagarBoleto(bolSelec.getPrecio())){
            if(tarSelec.actualizar(this)){
                Toast.makeText(this, "Pagado! Te quedan $" + String.format("%.2f",tarSelec.getSaldo()), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No se puede pagar", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    public void volver(View v){
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String fecha = dayOfMonth + "/" + month + "/" + year;
        tvFecha.setText(fecha);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String hora = hourOfDay + ":" + minute;
        tvHora.setText(hora);
    }
}
