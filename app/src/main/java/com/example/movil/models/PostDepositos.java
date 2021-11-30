package com.example.movil.models;

public class PostDepositos {
    public int id_deposito;
    public String nombre;
    public float registro;
    public int estado;

    public void setId_deposito(int id_deposito) {
        this.id_deposito = id_deposito;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRegistro(float registro) {
        this.registro = registro;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }



    public int getId_deposito() {
        return id_deposito;
    }

    public String getNombre() {
        return nombre;
    }

    public float getRegistro() {
        return registro;
    }

    public int getEstado() {
        return estado;
    }
}
