package com.rebeldev.bondiapp.modelo;

import java.util.ArrayList;

public class ParadaMicro {
    private double latitud;
    private double longitud;
    private ArrayList<Micro> micros;
    private String direccion;
    private String fotoPath;

    //Constructores
    public ParadaMicro(double latitud, double longitud, String direccion) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion = direccion;
        this.micros = new ArrayList<Micro>();
    }

    //Setters y Getters

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

    public ArrayList<Micro> getMicros() {
        return micros;
    }

    public void addMicros(Micro micro) {
        this.micros.add(micro);
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
}
