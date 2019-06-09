package com.rebeldev.bondiapp.modelo;

public class Micro {
    private int linea;
    private String color;

    //Constructores
    public Micro(int linea, String color) {
        this.linea = linea;
        this.color = color;
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

    public void setColor(String color) {
        this.color = color;
    }

    //Metodos
}
