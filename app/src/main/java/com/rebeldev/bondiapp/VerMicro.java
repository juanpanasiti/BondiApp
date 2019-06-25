package com.rebeldev.bondiapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

public class VerMicro extends AppCompatActivity {
    private TextView tvTitulo;
    private ImageView ivColorLine;
    private ListView lvParadas;
    private ArrayList<String> nombresPar;

    private Micro micro;
    private int lineaMicro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_micro);

        ivColorLine = findViewById(R.id.ivColorLine);
        tvTitulo = findViewById(R.id.tvTitulo);
        lvParadas = findViewById(R.id.lvParadas);
        nombresPar = new ArrayList<>();
        lineaMicro = getIntent().getIntExtra("lineaMicro",-1);
    }//onCreate()

    @Override
    protected void onStart() {
        super.onStart();
        micro = Micro.buscaPorLinea(this,lineaMicro);
        ivColorLine.setColorFilter(Color.parseColor(micro.getColorHexa()));
        tvTitulo.setText(micro.toString());
        llenarListView();
    }//onStart

    //Métodos privados
    private void llenarListView(){
        nombresPar.clear();
        final ArrayList<MicroParada> mps = MicroParada.buscarPorMicro(this,micro.getLinea());
        for(MicroParada mp : mps){
            Parada par = Parada.buscaPorID(this,mp.getIdParada());
            this.nombresPar.add(par.getDireccion());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item_paradas, nombresPar);
        lvParadas.setAdapter(adapter);

        lvParadas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent verParada = new Intent(view.getContext(), VerParada.class);
                Parada par = Parada.buscaPorID(VerMicro.this,mps.get(position).getIdParada());
                verParada.putExtra("idParada", par.getID());
                startActivity(verParada);
            }
        });

        lvParadas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VerMicro.this);
                builder.setCancelable(true);
                builder.setTitle("Borrar relacion con la parada");
                builder.setMessage("Seguro que desea borrarla?");
                builder.setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(mps.get(position).borrar(VerMicro.this)){
                                    Toast.makeText(getApplicationContext(), "Relacion con la parada eliminada", Toast.LENGTH_SHORT).show();
                                    llenarListView();
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
                return true;
            }
        });
    }

    //Metodos botones
    public void aFormMicro(View v){
        Intent aFM = new Intent(this, FormMicro.class);
        aFM.putExtra("lineaMicro",micro.getLinea());
        //startActivity(aFM);
        startActivityForResult(aFM,1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                lineaMicro = data.getIntExtra("lineaMicro",-1);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult()

    public void pagar(View v){
        //finish();
        Intent aPagoBoleto = new Intent(this, PagarBoleto.class);
        startActivity(aPagoBoleto);
    }
    public void borrar(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Borrar micro");
        builder.setMessage("Seguro que desea borrarlo?");
        builder.setPositiveButton("Si",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(micro.borrar(VerMicro.this)){
                            Toast.makeText(VerMicro.this, "Micro eliminado", Toast.LENGTH_SHORT).show();
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
    }

}
