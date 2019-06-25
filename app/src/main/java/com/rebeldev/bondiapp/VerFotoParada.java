package com.rebeldev.bondiapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Parada;

import java.io.File;

public class VerFotoParada extends AppCompatActivity {
    private ImageView ivFotoParada;
    private Parada parada;

    private final String CARPETA_RAIZ = "BondiApp";
    private final String CARPETA_IMAGENES = "paradas_micros";
    private final String RUTA_IMAGEN = CARPETA_RAIZ + File.separator + CARPETA_IMAGENES;
    private String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_foto_parada);

        ivFotoParada = findViewById(R.id.ivFotoParada);
        parada = Parada.buscaPorID(this,getIntent().getIntExtra("idParada",0));
        String nombreImagen = "foto_parada_micro_" + parada.getID() + ".jpg";
        path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
        mostrarFoto();
    }

    private void mostrarFoto(){
        try{
            if((new File(path)).exists()){
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ivFotoParada.setImageBitmap(rotatedBitmap);
            }
        } catch (Exception e){
            Toast.makeText(this, "Error al mostrar la imagen: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void volver(View v){
        finish();
    }
}
