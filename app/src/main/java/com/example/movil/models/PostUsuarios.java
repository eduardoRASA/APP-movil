package com.example.movil.models;

public class PostUsuarios {
    private String colonia;
    private String email;
    private int id;
    private String password;

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email2) {
        this.email = email2;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password2) {
        this.password = password2;
    }

    public String getColonia() {
        return this.colonia;
    }

    public void setColonia(String colonia2) {
        this.colonia = colonia2;
    }
}
