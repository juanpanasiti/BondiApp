package com.rebeldev.bondiapp.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.rebeldev.bondiapp.database.AdminSQLiteOpenHelper;

import java.util.ArrayList;

public class Micro {
    //Atributos de clase
    private static final String TABLE_NAME = "micro";
    private static final String[] COLUMNAS = {"linea_mic", "color_mic","descr_mic"};

    //Atributos de objeto
    private int linea;
    private String color;
    private String descripcion;

    //Constructores
    public Micro(int linea, String color) {
        this.linea = linea;
        this.color = color;
    }
    public Micro() {
    }

    //Setters y Getters

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public String getColor() {
        return color;
    }

    public String getColorHexa() {
        ArrayList<Color> colores = Micro.getColores();
        String colorName = "#000000";
        for(Color color : colores){
            if(this.getColor().equals(color.getDescripcion())){
                colorName = color.getHexa();
                break;
            }
        }
        return colorName;
    }//getColorName()

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Métodos sobreescritos
    @Override
    public String toString(){
        return this.getLinea() + " - " + this.getDescripcion() + " (" + this.getColor() + ")";
    }

    //Metodos con base de datos
    public boolean crear(Context context){
        boolean creado = false;
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);

        ContentValues registro = new ContentValues();
        registro.put("linea_mic", this.getLinea());
        registro.put("color_mic", this.getColor());
        registro.put("descr_mic", this.getDescripcion());

        try{
            adminDB.create(registro,TABLE_NAME);
            creado = true;
        }catch (Exception e){
            Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        adminDB.close();
        return creado;
    }//crear()

    public boolean borrar(Context context){
        boolean eliminado = false;
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        ArrayList<MicroParada> mps = MicroParada.buscarPorMicro(context, this.linea);
        if(mps.size() == 0){
            try{
                adminDB.delete(TABLE_NAME,"linea_mic=" + this.linea,null);
                eliminado = true;
            }catch (Exception e){
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "No se puede borrar porque aparece en " + mps.size() + " listas de paradas.", Toast.LENGTH_SHORT).show();
        }
        adminDB.close();
        return eliminado;
    }//borrar()

    //Métodos estáticos con base de datos
    public static ArrayList<Micro> buscarTodos(Context context){
        ArrayList<Micro> micros = new ArrayList<>();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        Cursor cursor = adminDB.findAll(TABLE_NAME,COLUMNAS);
        while (cursor.moveToNext()){
            Micro m = new Micro(cursor.getInt(0),cursor.getString(1));
            m.setDescripcion(cursor.getString(2));
            micros.add(m);
        }
        adminDB.close();
        cursor.close();
        return micros;
    }//buscarTodos()

    public static Micro buscaPorLinea(Context context, int linea){
        Micro micro = new Micro();
        AdminSQLiteOpenHelper adminDB = new AdminSQLiteOpenHelper(context);
        Cursor cursor = adminDB.findByField(TABLE_NAME,"linea_mic", linea, COLUMNAS);
        if(cursor.moveToFirst()){
            micro.setLinea(cursor.getInt(0));
            micro.setColor(cursor.getString(1));
            micro.setDescripcion(cursor.getString(2));
        } else {
            micro.setLinea(000);
            micro.setColor("Error");
            micro.setDescripcion("Error");
        }
        adminDB.close();
        cursor.close();
        return micro;
    }//buscarPorLinea

    //Otros métodos estáticos
    public static ArrayList<Color> getColores(){
        ArrayList<Color> colores = new ArrayList<>();
        //Grupo 1: Rojo
        colores.add(new Color("Rojo","#D50000"));
        //Grupo 2: Gris
        colores.add(new Color("Gris","#BDBDBD"));
        //Grupo 3: Verde
        colores.add(new Color("Verde","#4CAF50"));
        //Grupo 4: Amarillo
        colores.add(new Color("Amarillo","#FDD835"));
        //Grupo 5: Naranja
        colores.add(new Color("Naranja","#FF9800"));
        //Grupo 6: Celeste
        colores.add(new Color("Celeste","#4FC3F7"));
        //Grupo 7: Azul
        colores.add(new Color("Azul","#0D47A1"));
        //Grupo 8: Morado
        colores.add(new Color("Morado","#7B1FA2"));
        //Grupo 9: Rosa
        colores.add(new Color("Rosa","#FF4081"));

        return colores;
    }
}
