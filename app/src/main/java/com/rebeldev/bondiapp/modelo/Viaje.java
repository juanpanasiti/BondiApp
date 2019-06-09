package com.rebeldev.bondiapp.modelo;

import java.time.LocalDateTime;

public class Viaje {
    private Micro micro;
    private Tarjeta tarjeta;
    private LocalDateTime fechaHora;
    private float monto;

    //Constructores
    public Viaje(Micro micro, Tarjeta tarjeta, LocalDateTime fechaHora, float monto) {
        this.micro = micro;
        this.tarjeta = tarjeta;
        this.fechaHora = fechaHora;
        this.monto = monto;
    }

    //Setters y Getters
    public Micro getMicro() {
        return micro;
    }

    public void setMicro(Micro micro) {
        this.micro = micro;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }
}
