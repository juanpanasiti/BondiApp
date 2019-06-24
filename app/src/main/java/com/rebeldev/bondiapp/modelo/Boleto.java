package com.rebeldev.bondiapp.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.rebeldev.bondiapp.database.AdminSQLiteOpenHelper;

import java.util.ArrayList;

public class Boleto {
    //Atributos de clase
    private static final String TABLE_NAME = "boleto";
    private static final String[] COLUMNAS = {"id_bol", "precio_bol", "descripcion_bol"};

    //Atributos de objeto
    private int id;
    private float precio;
    private String descripcion;

    //Constructores
    public Boleto(float precio, String descripcion) {
        this.precio = precio;
        this.descripcion = descripcion;
    }
    public Boleto() {
    }

    //Getters y Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Metodos con base de datos
    public boolean crear(Context context){
        boolean creado = false;
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);

        ContentValues registro = new ContentValues();
        registro.put("precio_bol", this.precio);
        registro.put("descripcion_bol", this.descripcion);

        try{
            adminDB.create(registro,TABLE_NAME);
            creado = true;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return creado;
    }//crear()

    //Métodos estáticos con base de datos
    public static ArrayList<Boleto> buscarTodos(Context context){
        ArrayList<Boleto> boletos = new ArrayList<>();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        Cursor cursor = adminDB.findAll(TABLE_NAME,COLUMNAS);
        while (cursor.moveToNext()){
            Boleto b = new Boleto(cursor.getFloat(1), cursor.getString(2));
            b.setId(cursor.getInt(0));
            boletos.add(b);
        }
        return boletos;
    }//buscarTodos()

    public static Boleto buscaPorNum(Context context, int id){
        Boleto boleto = new Boleto();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        Cursor cursor = adminDB.findByField(TABLE_NAME,"id_bol", id, COLUMNAS);
        if(cursor.moveToFirst()){
            boleto.setId(cursor.getInt(0));
            boleto.setPrecio(cursor.getFloat(1));
            boleto.setDescripcion(cursor.getString(2));
        } else {
            boleto.setId(0);
            boleto.setPrecio(0.0f);
            boleto.setDescripcion("Error");
        }

        return boleto;
    }//buscarPorID
}
