package com.rebeldev.bondiapp.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.rebeldev.bondiapp.database.AdminSQLiteOpenHelper;

import java.util.ArrayList;

public class Parada {
    //Atributos de clase
    private static final String TABLE_NAME = "parada";
    private static final String[] COLUMNAS = {"id_par", "latitud_par", "longitud_par", "direccion_par", "fotoPath_par"};

    //Atributos de objeto
    private int id;
    private double latitud;
    private double longitud;
    private String direccion;
    private String fotoPath;

    //Constructores
    public Parada() {
        this.id = 0;
        this.latitud = 0;
        this.longitud = 0;
        this.direccion = "";
        this.fotoPath = "";
    }

    //Setters y Getters

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    //Metodos con base de datos
    //Columnas en la DB: id_par, latitud_par, longitud_par, direccion_par, fotoPath_par
    public boolean crear(Context context){
        boolean creado = false;
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);

        ContentValues registro = new ContentValues();
        registro.put("direccion_par", this.getDireccion());
        registro.put("latitud_par", this.getLatitud());
        registro.put("longitud_par", this.getLongitud());
        registro.put("fotoPath_par", this.getFotoPath());

        try{
            adminDB.create(registro,TABLE_NAME);
            creado = true;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return creado;
    }//crear()

    public boolean actualizar(Context context){
        boolean actualizado = false;

        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);

        ContentValues registro = new ContentValues();
        registro.put("latitud_par", this.latitud);
        registro.put("longitud_par", this.longitud);
        registro.put("direccion_par", this.direccion);
        registro.put("fotoPath_par", this.fotoPath);

        try{
            adminDB.update(registro, TABLE_NAME, "id_par=" + this.id, null);
            actualizado = true;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return actualizado;
    }//actualizar()

    public boolean borrar(Context context){
        boolean eliminado = false;
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        try{
            adminDB.delete(TABLE_NAME,"id_par=" + this.id,null);
            eliminado = true;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return eliminado;
    }//borrar()
    public ArrayList<Micro> buscarMicros(Context context){
        ArrayList<MicroParada> mps = MicroParada.buscarPorParada(context,this.getID());
        Toast.makeText(context, "Relaciones: " + mps.size(), Toast.LENGTH_SHORT).show();
        ArrayList<Micro> micros = new ArrayList<>();
        for(MicroParada mp : mps){
            Micro m = Micro.buscaPorLinea(context,mp.getLineaMicro());
            micros.add(m);
        }
        return micros;
    }//buscarMicros()
    public ArrayList<Micro> buscarMicrosParaAgregar(Context context){
        //ArrayList<Micro> micros = Micro.buscarTodos(context);
        ArrayList<MicroParada> mps = MicroParada.buscarPorParada(context,this.getID());
        String lineas = "";
        MicroParada ultMP;
        if(mps.size() != 0){
            ultMP = mps.get(mps.size() - 1);
        } else {
            ultMP = new MicroParada();
        }
        for(MicroParada mp : mps){
            lineas.concat(String.valueOf(mp.getLineaMicro()));
            if(!mp.equals(ultMP)){
                lineas.concat(",");//Si no es la ultima linea que se agregará, se concatena una coma
            }
        }
        ArrayList<Micro> micros = new ArrayList<>();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        String query = "SELECT * FROM micro WHERE linea_mic NOT IN (" + lineas + ")";
        Cursor cursor = adminDB.exeRawQuery(query);
        while (cursor.moveToNext()){
            Micro m = Micro.buscaPorLinea(context,cursor.getInt(0));

            micros.add(m);
        }
        adminDB.close();
        cursor.close();
        return micros;
    }//buscarMicrosParaAgregar()

    //Métodos estáticos con base de datos
    public static ArrayList<Parada> buscarTodos(Context context){
        ArrayList<Parada> paradas = new ArrayList<>();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        Cursor cursor = adminDB.findAll(TABLE_NAME,COLUMNAS);
        while (cursor.moveToNext()){
            Parada p = new Parada();
            p.setID(cursor.getInt(0));
            p.setLatitud(cursor.getInt(1));
            p.setLongitud(cursor.getInt(2));
            p.setDireccion(cursor.getString(3));
            p.setFotoPath(cursor.getString(4));
            paradas.add(p);
        }
        cursor.close();
        return paradas;
    }//buscarTodos()

    public static Parada buscaPorID(Context context, int id){
        Parada parada = new Parada();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        Cursor cursor = adminDB.findByField(TABLE_NAME,"id_par", id, COLUMNAS);
        if(cursor.moveToFirst()){
            parada.setID(cursor.getInt(0));
            parada.setDireccion(cursor.getString(3));
        } else {
            parada.setID(0);
            parada.setDireccion("Error");
        }
        cursor.close();
        return parada;
    }//buscarPorID
}
