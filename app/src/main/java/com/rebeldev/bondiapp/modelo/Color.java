package com.rebeldev.bondiapp.modelo;

public class Color {
    private String descripcion;
    private String hexa;

    public Color(String descripcion, String hexa) {
        this.descripcion = descripcion;
        this.hexa = hexa;
    }//constructor()

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHexa() {
        return hexa;
    }

    public void setHexa(String hexa) {
        this.hexa = hexa;
    }
}
