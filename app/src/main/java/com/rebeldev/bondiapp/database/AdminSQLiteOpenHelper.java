package com.rebeldev.bondiapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
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
        String sqlTarjetas = "CREATE TABLE tarjeta(" +
                                "numero_tar INTEGER PRIMARY KEY," +
                                "nombre_tar TEXT NOT NULL," +
                                "saldo_tar REAL" +
                                ")";

        //Tabla ABONOS
        String sqlAbonos = "CREATE TABLE abono(" +
                                "id_abo INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "tipo_abo TEXT NOT NULL," +
                                "saldo_abo REAL NOT NULL," +
                                "descuento_abo INTEGER NOT NULL," +
                                "viajes_abo INTEGER NOT NULL," +
                                "tarjeta_numero_abo INTEGER NOT NULL" +
                                ")";

        //Tabla BOLETOS
        String sqlBoletos = "CREATE TABLE boleto(" +
                                "id_bol INTEGER PRIMARY KEY AUTOINCREMENT," +
                                "precio_bol REAL NOT NULL," +
                                "descripcion_bol TEXT NOT NULL" +
                                ")";
        //Tabla MICROS
        String sqlMicros = "CREATE TABLE micro(" +
                "linea_mic INTEGER PRIMARY KEY," +
                "color_mic VARCHAR(7) NOT NULL," +
                "descr_mic TEXT" +
                ")";
        //Tabla VIAJES
        String sqlViajes = "CREATE TABLE viaje(" +
                "id_via INTEGER PRIMARY KEY AUTOINCREMENT," +
                "micro_via TEXT NOT NULL," +
                "tarjeta_via TEXT NOT NULL," +
                "monto_pagado_via REAL NOT NULL," +
                "fecha_hora_via TEXT NOT NULL" +
                ")";
        //Tabla PARADAS
        String sqlParadas = "CREATE TABLE parada(" +
                "id_par INTEGER PRIMARY KEY AUTOINCREMENT," +
                "latitud_par REAL," +
                "longitud_par REAL," +
                "direccion_par TEXT NOT NULL," +
                "fotoPath_par TEXT" +
                ")";
        //Tabla rel MICRO-PARADA
        String sqlMP = "CREATE TABLE micro_parada(" +
                "id_mp INTEGER PRIMARY KEY AUTOINCREMENT," +
                "linea_mic_mp INTEGER NOT NULL," +
                "id_par_mp INTEGER NOT NULL" +
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
        db.close();
    }
     public int update(ContentValues record, String tableName, String whereClause, String[] whereArgs){
        SQLiteDatabase db = this.getWritableDatabase();
        int code;
        code = db.update(tableName,record,whereClause,whereArgs);
        db.close();
        return code;
     }

     public void delete(String tableName, String whereClause, String[] whereArgs){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tableName, whereClause, whereArgs);
        db.close();
     }

     public Cursor findByField(String tableName, String field, int value, String[] columns){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(tableName, columns, field + "=" + value, null,null,null,null);
        //db.close();
        return cursor;
     }
     public Cursor findAll(String tableName, String[] columns){
        SQLiteDatabase db = this.getReadableDatabase();
         Cursor cursor = db.query(tableName, columns, null, null,null,null,null);
         //db.close();
         return cursor;
     }

     public Cursor exeRawQuery(String rawQuery){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(rawQuery,null);

        return cursor;
     }

}//CLASS