package com.rebeldev.bondiapp.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.rebeldev.bondiapp.database.AdminSQLiteOpenHelper;

import java.util.ArrayList;

public class MicroParada {
    //Atributos de clase
    private static final String TABLE_NAME = "micro_parada";
    private static final String[] COLUMNAS = {"id_mp", "linea_mic_mp", "id_par_mp"};

    //Atributos de objeto
    private int id;
    private int lineaMicro;
    private int idParada;

    public MicroParada() {
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getLineaMicro() {
        return lineaMicro;
    }

    public void setLineaMicro(int lineaMicro) {
        this.lineaMicro = lineaMicro;
    }

    public int getIdParada() {
        return idParada;
    }

    public void setIdParada(int idParada) {
        this.idParada = idParada;
    }

    //Métodos con bases de datos
    public boolean crear(Context context){
        boolean creado = false;
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        ArrayList<MicroParada> existentes = MicroParada.buscarPorRelacion(context,this.lineaMicro, this.idParada);
        if(existentes.size() == 0){
            ContentValues registro = new ContentValues();
            registro.put("linea_mic_mp", this.getLineaMicro());
            registro.put("id_par_mp", this.getIdParada());
    
            try{
                adminDB.create(registro,TABLE_NAME);
                creado = true;
            }catch (Exception e){
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Ya existe este micro en esta parada.", Toast.LENGTH_SHORT).show();
        }
        return creado;
    }//crear()
    public boolean borrar(Context context){
        boolean eliminado = false;
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        try{
            adminDB.delete(TABLE_NAME,"id_mp=" + this.getID(),null);
            eliminado = true;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return eliminado;
    }//borrar()

    //Métodos estáticos con base de datos
    public static ArrayList<MicroParada> buscarPorParada(Context context, int idParada){
        ArrayList<MicroParada> mps = new ArrayList<>();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        Cursor cursor = adminDB.findByField(TABLE_NAME,"id_par_mp",idParada,COLUMNAS);
        while (cursor.moveToNext()){
            MicroParada mp = new MicroParada();
            mp.setID(cursor.getInt(0));
            mp.setLineaMicro(cursor.getInt(1));
            mp.setIdParada(cursor.getInt(2));
            mps.add(mp);
        }
        return mps;
    }//buscarPorParada()

    public static ArrayList<MicroParada> buscarPorMicro(Context context, int lineaMicro){
        ArrayList<MicroParada> mps = new ArrayList<>();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        Cursor cursor = adminDB.findByField(TABLE_NAME,"linea_mic_mp",lineaMicro,COLUMNAS);
        while (cursor.moveToNext()){
            MicroParada mp = new MicroParada();
            mp.setID(cursor.getInt(0));
            mp.setLineaMicro(cursor.getInt(1));
            mp.setIdParada(cursor.getInt(2));
            mps.add(mp);
        }
        return mps;
    }//buscarPorMicro()

    public static ArrayList<MicroParada> buscarPorRelacion(Context context, int lineaMicro, int idParada){
        ArrayList<MicroParada> mps = new ArrayList<>();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        Cursor cursor = adminDB.exeRawQuery("SELECT * FROM " + TABLE_NAME + " WHERE linea_mic_mp=" + lineaMicro + " AND id_par_mp=" + idParada);
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()){
                MicroParada mp = new MicroParada();
                mp.setID(cursor.getInt(0));
                mp.setLineaMicro(cursor.getInt(1));
                mp.setIdParada(cursor.getInt(2));
                mps.add(mp);
            }
        }
        return mps;
    }//buscarPorRelacion()
}
