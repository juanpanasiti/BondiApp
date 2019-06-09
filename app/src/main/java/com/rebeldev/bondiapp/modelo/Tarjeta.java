package com.rebeldev.bondiapp.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.rebeldev.bondiapp.database.AdminSQLiteOpenHelper;

import java.util.ArrayList;

public class Tarjeta {
    //Atributos de clase
    private static final String TABLE_NAME = "tarjetas";
    private static final String[] COLUMNAS = {"numero", "nombre", "saldo"};

    //Atributos de objeto
    private String nombre;
    private int numero;
    private float saldo;

    //Constructores
    public Tarjeta(String nombre, int numero) {
        this.nombre = nombre;
        this.numero = numero;
        this.saldo = 0.00f;
    }
    public Tarjeta(){

    }

    //Setters y Getters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }


    public void cargar(float carga){
        this.saldo += carga;
    }

    //Métodos con base de datos
    public boolean create(Context context){
        boolean creado = false;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);

        ContentValues registro = new ContentValues();
        registro.put("numero", this.numero);
        registro.put("nombre", this.nombre);
        registro.put("saldo", this.saldo);
        try{
            admin.create(registro,TABLE_NAME);
            creado = true;
            Toast.makeText(context, "Nueva tarjeta agregada correctamente.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "Error al cargar la tarjeta: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return creado;
    }//create()

    public boolean update(Context context){
        boolean actualizado = false;

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);

        ContentValues registro = new ContentValues();
        registro.put("numero", this.numero);
        registro.put("nombre", this.nombre);
        registro.put("saldo", this.saldo);

        try{
            admin.update(registro, TABLE_NAME, "numero=" + this.numero, null);
            actualizado = true;
            Toast.makeText(context, "Tarjeta actualizada correctamente", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return actualizado;
    }//update()

    public boolean delete(Context context){
        boolean eliminado = false;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase db = admin.getWritableDatabase();
        try{
            admin.delete(TABLE_NAME,"numero=" + this.numero,null);
            eliminado = true;
            Toast.makeText(context, "Tarjeta eliminada correctamente", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
        return eliminado;
    }//delete()

    //Métodos estáticos con base de datos
    public static ArrayList<Tarjeta> getAll(Context context){
        ArrayList<Tarjeta> tarjetas = new ArrayList<>();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context,"bondiapp",null,1);
        Cursor cursor = admin.findAll(TABLE_NAME,COLUMNAS);
        while (cursor.moveToNext()){
            Tarjeta t = new Tarjeta(cursor.getString(1),cursor.getInt(0));
            t.setSaldo(cursor.getFloat(2));
            tarjetas.add(t);
        }
        return tarjetas;
    }

    public static Tarjeta findByNum(Context context, int numero){
        Tarjeta tarjeta = new Tarjeta();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
        Cursor cursor = admin.findByFK(TABLE_NAME,"numero", numero, COLUMNAS);
        if(cursor.moveToFirst()){
            tarjeta.setNumero(cursor.getInt(0));
            tarjeta.setNombre(cursor.getString(1));
            tarjeta.setSaldo(cursor.getFloat(2));
        } else {
            tarjeta.setNumero(0);
            tarjeta.setNombre("Err");
            tarjeta.setSaldo(0.0f);
        }

        return tarjeta;
    }

}
