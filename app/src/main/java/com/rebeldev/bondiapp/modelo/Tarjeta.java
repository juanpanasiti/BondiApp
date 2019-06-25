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
    private static final String TABLE_NAME = "tarjeta";
    private static final String[] COLUMNAS = {"numero_tar", "nombre_tar", "saldo_tar"};

    //Atributos de objeto
    private String nombre;
    private int numero;//PRIMARY KEY!
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


    public void cargar(float monto){
        this.saldo += monto;
    }

    public boolean pagarBoleto(float monto){
        boolean pagado = false;
        if(this.saldo >= monto){
            this.saldo -= monto;
            pagado = true;
        }
        return pagado;
    }

    //Métodos con base de datos
    public boolean crear(Context context){
        boolean creado = false;
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        if(adminDB.findByField(TABLE_NAME,"numero_tar", numero, null).getCount() == 0){
            ContentValues registro = new ContentValues();
            registro.put("numero_tar", this.numero);
            registro.put("nombre_tar", this.nombre);
            registro.put("saldo_tar", this.saldo);

            try{
                adminDB.create(registro,TABLE_NAME);
                creado = true;
            }catch (Exception e){
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Ya existe una tarjeta con ese número", Toast.LENGTH_SHORT).show();
        }
        return creado;
    }//crear()

    public boolean actualizar(Context context){
        boolean actualizado = false;

        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);

        ContentValues registro = new ContentValues();
        registro.put("numero_tar", this.numero);
        registro.put("nombre_tar", this.nombre);
        registro.put("saldo_tar", this.saldo);

        try{
            adminDB.update(registro, TABLE_NAME, "numero_tar="+this.numero, null);
            actualizado = true;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            adminDB.close();
        }
        return actualizado;
    }//actualizar()

    public boolean borrar(Context context){
        boolean eliminado = false;
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        try{
            adminDB.delete(TABLE_NAME,"numero_tar=" + this.numero,null);
            eliminado = true;
            Toast.makeText(context, "Tarjeta eliminada correctamente", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return eliminado;
    }//borrar()

    //Métodos estáticos con base de datos
    public static ArrayList<Tarjeta> buscarTodos(Context context){
        ArrayList<Tarjeta> tarjetas = new ArrayList<>();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        Cursor cursor = adminDB.findAll(TABLE_NAME,COLUMNAS);
        while (cursor.moveToNext()){
            Tarjeta t = new Tarjeta(cursor.getString(1),cursor.getInt(0));
            t.setSaldo(cursor.getFloat(2));
            tarjetas.add(t);
        }
        return tarjetas;
    }//buscarTodos()

    public static Tarjeta buscaPorNum(Context context, int numero){
        Tarjeta tarjeta = new Tarjeta();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        Cursor cursor = adminDB.findByField(TABLE_NAME,"numero_tar", numero, COLUMNAS);
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
    }//buscarPorNum

}
