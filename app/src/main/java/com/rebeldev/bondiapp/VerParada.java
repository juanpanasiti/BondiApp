package com.rebeldev.bondiapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Micro;
import com.rebeldev.bondiapp.modelo.MicroParada;
import com.rebeldev.bondiapp.modelo.Parada;

import java.util.ArrayList;

public class VerParada extends AppCompatActivity {
    private TextView tvTitulo;
    private Parada parada;
    private ListView lvMicros;
    private ArrayList<Micro> micros = new ArrayList<>();
    private ArrayList<String> nombresMicros = new ArrayList<>();
    private ImageView ivVerFoto;
    private ImageView ivTomarFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_parada);

        tvTitulo = findViewById(R.id.tvTitulo);
        lvMicros = findViewById(R.id.lvMicros);
        ivVerFoto = findViewById(R.id.ivVerFoto);
        ivTomarFoto = findViewById(R.id.ivTomarFoto);

    }//onCreate()



    @Override
    protected void onStart() {
        super.onStart();
        parada = Parada.buscaPorID(this,getIntent().getIntExtra("idParada",0));
        tvTitulo.setText(parada.getDireccion());
        llenarArrays();
        

    }//onStart
    //Métodos privados
    private void llenarArrays(){
        this.micros.clear();
        this.nombresMicros.clear();
        this.micros = parada.buscarMicros(this);
        for(Micro micro : micros){
            this.nombresMicros.add(micro.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_micros, nombresMicros);
        lvMicros.setAdapter(adapter);

        lvMicros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent verMicro = new Intent(view.getContext(), VerMicro.class);
                verMicro.putExtra("lineaMicro", micros.get(position).getLinea());
                startActivity(verMicro);
            }
        });
        lvMicros.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                ArrayList<MicroParada> mps = MicroParada.buscarPorRelacion(getApplicationContext(),micros.get(position).getLinea(),parada.getID());
                String ids = "";
                for(MicroParada mp : mps){
                    ids.concat(mp.getID()+ " ");
                }
                ids = String.valueOf(mps.size());
                Toast.makeText(VerParada.this, ids, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }//llenarArrays()
    //Metodos botones
    public void aFormParada(View v){
        Intent aFP = new Intent(this, FormParada.class);
        startActivity(aFP);
    }

    public void borrar(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Borrar Parada de micro");
        builder.setMessage("Seguro que desea borrarla?");
        builder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(parada.borrar(getApplicationContext())){
                            Toast.makeText(getApplicationContext(), "Parada de micro eliminada", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }//borrar()

    public void volver(View v){
        finish();
    }//volver()

    public void agregarMicro(View v){
        Intent aAgregarMicro = new Intent(this,AgregarMicroAParada.class);
        aAgregarMicro.putExtra("idParada",parada.getID());
        startActivity(aAgregarMicro);
    }//agregarMicro()

    public void tomarFoto(View v){
        Intent aTomarFoto = new Intent(this,TomarFotoParadaMicro.class);
        aTomarFoto.putExtra("idParada", parada.getID());
        startActivity(aTomarFoto);
    }
    public void verFoto(View v){
        Intent aVerFoto = new Intent(this, VerFotoParada.class);
        aVerFoto.putExtra("idParada", parada.getID());
        startActivity(aVerFoto);
    }//verFoto()
}
