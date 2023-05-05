package com.alura.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreaConexion {
    public Connecion recuperaConexion(){
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
                "root",
                "password");
        Statement statement = con.createStatement();
    }

}
