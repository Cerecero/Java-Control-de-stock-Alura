package com.alura.jdbc.persistencia;

import java.sql.Connection;

public class PersistenciaProducto {
    private Connection con;
    public PersistenciaProducto(Connection con){
        this.con = con;
    }
}
