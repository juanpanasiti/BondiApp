package com.rebeldev.bondiapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.rebeldev.bondiapp.modelo.Parada;

import java.io.File;

public class TomarFotoParadaMicro extends AppCompatActivity {
    private ImageView ivFoto;
    private Button btnTomarFoto;
    private Parada parada;

    private final int COD_FOTO = 20;
    private final String CARPETA_RAIZ = "BondiApp";
    private final String CARPETA_IMAGENES = "paradas_micros";
    private final String RUTA_IMAGEN = CARPETA_RAIZ + File.separator + CARPETA_IMAGENES;
    private String path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomar_foto_parada_micro);

        ivFoto = findViewById(R.id.ivFoto);
        btnTomarFoto = findViewById(R.id.btnCamara);
        parada = Parada.buscaPorID(this,getIntent().getIntExtra("idParada",0));

        //PERMISOS PARA ANDROID 6 O SUPERIOR
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TomarFoto();
            }
        });
    }//onCreate()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            switch (requestCode){
                case COD_FOTO:
                    MediaScannerConnection.scanFile(this,new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener(){
                        @Override
                        public void onScanCompleted(String path, Uri uri){

                        }
                    });

                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    parada.setFotoPath(path);
                    if(parada.actualizar(this)){
                        //Toast.makeText(this, "Foto de la parada guardada", Toast.LENGTH_SHORT).show();
                    }
                    ivFoto.setImageBitmap(bitmap);

                    break;
            }
        }
    }//onActivityResult()

    public void TomarFoto(){
        String nombreImagen = "";

        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();

        if(!isCreada){
            isCreada = fileImagen.mkdirs();
        }

        if (isCreada){
            nombreImagen = "foto_parada_micro_" + parada.getID() + ".jpg";
        }

        path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
        File imagen = new File(path);

        Intent intent = null;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            String authorities = this.getPackageName() + ".provider";
            Uri imageUri = FileProvider.getUriForFile(this,authorities,imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imagen));
        }
        startActivityForResult(intent,COD_FOTO);


    }//tomarFoto()
}
