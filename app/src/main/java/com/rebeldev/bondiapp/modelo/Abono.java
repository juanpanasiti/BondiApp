package com.rebeldev.bondiapp.modelo;

public class Abono {
    private String tipo;
    private float saldo;
    private int porcDescuento; //0..100
    private int cantViajes;
    private Tarjeta tarjeta;

    //Constructores
    public Abono(String tipo, int porcDescuento, int cantViajes, Tarjeta tarjeta) {
        this.tipo = tipo;
        this.saldo = 0.0f;
        this.porcDescuento = porcDescuento;
        this.cantViajes = cantViajes;
        this.tarjeta = tarjeta;
    }

    //Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }

    public int getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(int porcDescuento) {
        this.porcDescuento = porcDescuento;
    }

    public int getCantViajes() {
        return cantViajes;
    }

    public void setCantViajes(int cantViajes) {
        this.cantViajes = cantViajes;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    //Métodos
}
