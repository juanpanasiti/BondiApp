package com.rebeldev.bondiapp.modelo;

public class Boleto {
    private float precio;
    private String descripcion;

    //Constructores
    public Boleto(float precio, String descripcion) {
        this.precio = precio;
        this.descripcion = descripcion;
    }

    //Getters y Setter
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

    //Metodos
}
