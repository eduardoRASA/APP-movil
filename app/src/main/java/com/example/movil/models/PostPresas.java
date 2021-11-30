package com.example.movil.models;

public class PostPresas {
    public int estado;
    public int id_presa;
    public String nombre;
    public float registro;

    public void setId_presa(int id_presa2) {
        this.id_presa = id_presa2;
    }

    public void setNombre(String nombre2) {
        this.nombre = nombre2;
    }

    public void setRegistro(float registro2) {
        this.registro = registro2;
    }

    public void setEstado(int estado2) {
        this.estado = estado2;
    }

    public int getId_presa() {
        return this.id_presa;
    }

    public String getNombre() {
        return this.nombre;
    }

    public float getRegistro() {
        return this.registro;
    }

    public int getEstado() {
        return this.estado;
    }
}
