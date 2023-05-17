package com.alura.jdbc.modelo;

public class Categoria {
    private Integer id;
    private String nombre;

    public Categoria(int i, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString(){
        return this.nombre;
    }
}
