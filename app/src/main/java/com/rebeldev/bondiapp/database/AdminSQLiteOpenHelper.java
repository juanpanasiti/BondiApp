package com.rebeldev.bondiapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/*
    NULL: El valor es un valor NULL.
    INTEGER: El valor es un entero con signo, almacenado en 1, 2, 3, 4, 6 u 8 bytes dependiendo de la magnitud del valor.
    REAL: El valor es un valor de punto flotante, almacenado como un número de punto flotante IEEE de 8 bytes.
    TEXT: El valor es una cadena de texto, almacenada utilizando la codificación de la base de datos (UTF-8, UTF-16BE o UTF-16LE).
    BLOB: El valor es una gota de datos, almacenados exactamente como se introdujo.
 */

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    //V. globales
    private static int DB_VERSION = 1;
    private static String DB_FILE_NAME = "bondiapp";

    public AdminSQLiteOpenHelper(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    //Crear base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tabla TARJETAS
        String sqlTarjetas = "CREATE TABLE tarjetas(" +
                                "numero INTEGER PRIMARY KEY," +
                                "nombre TEXT NOT NULL," +
                                "saldo REAL" +
                                ")";

        //Tabla ABONOS
        String sqlAbonos = "CREATE TABLE abonos(" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "tipo TEXT NOT NULL," +
                                "saldo REAL NOT NULL," +
                                "descuento INTEGER NOT NULL," +
                                "viajes INTEGER NOT NULL," +
                                "tarjeta_numero INTEGER NOT NULL" +
                                ")";

        //Tabla BOLETOS
        String sqlBoletos = "CREATE TABLE boletos(" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "precio REAL NOT NULL," +
                                "descripcion TEXT NOT NULL" +
                                ")";
        //Tabla MICROS
        String sqlMicros = "CREATE TABLE micros(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "linea INTEGER," +
                "color VARCHAR(6) NOT NULL" +
                ")";
        //Tabla VIAJES
        String sqlViajes = "CREATE TABLE viajes(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "micro_id INTEGER NOT NULL," +
                "tarjeta_numero INTEGER NOT NULL," +
                "monto_pagado REAL NOT NULL," +
                "fechayhora TEXT NOT NULL" +
                ")";
        //Tabla PARADAS
        String sqlParadas = "CREATE TABLE paradas(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "latitud REAL NOT NULL," +
                "longitud REAL NOT NULL," +
                "direccion TEXT NOT NULL," +
                "fotoPath TEXT" +
                ")";
        //Tabla rel MICRO-PARADA
        String sqlMP = "CREATE TABLE micros_paradas(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "micro_id INTEGER NOT NULL," +
                "parada_id INTEGER NOT NULL" +
                ")";
        //Tabla MICROS
        //db.execSQL("CREATE TABLE nombre_tabla(nombre_col tipo_col options)");

        db.execSQL(sqlTarjetas);
        db.execSQL(sqlAbonos);
        db.execSQL(sqlBoletos);
        db.execSQL(sqlMicros);
        db.execSQL(sqlViajes);
        db.execSQL(sqlParadas);
        db.execSQL(sqlMP);
    }//onCreate()

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }//onUpgrade()

    public void create(ContentValues record, String tableName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(tableName,null, record);
    }
     public void update(ContentValues record, String tableName, String whereClause, String[] whereArgs){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(tableName,record,whereClause,whereArgs);
     }

     public void delete(String tableName, String whereClause, String[] whereArgs){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, whereClause, whereArgs);
     }

     public Cursor findByFK(String tableName, String fk, int fkVal, String[] columns){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, columns, fk + "=" + fkVal, null,null,null,null,"1");
        return cursor;
     }
     public Cursor findAll(String tableName, String[] columns){
        SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.query(tableName, columns, null, null,null,null,null);
         return cursor;
     }

}//CLASS