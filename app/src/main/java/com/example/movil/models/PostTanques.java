package com.example.movil.models;

public class PostTanques {
    public int id_tanque;
    public String colonia;
    public float registro;
    public int estado;

    public void setId_tanque(int id_tanque) {
        this.id_tanque = id_tanque;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public void setRegistro(float registro) {
        this.registro = registro;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }



    public int getId_tanque() {
        return id_tanque;
    }

    public String getColonia() {
        return colonia;
    }

    public float getRegistro() {
        return registro;
    }

    public int getEstado() {
        return estado;
    }
}
