package com.alura.jdbc.dao;

import com.alura.jdbc.modelo.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private Connection con;
    public CategoriaDAO(Connection recuperaConexion) {
        this.con = con;
    }

    public List<Categoria> listar() {
        List<Categoria> resultado = new ArrayList<>();

        try {
            final PreparedStatement statement = con.prepareStatement(
                    "SELECT ID, NOMBRE FROM categoria");
            try(statement){
                try(ResultSet resultSet = statement.executeQuery()){
                    while(resultSet.next()) {
                        var categoria = new Categoria(resultSet.getInt("ID"),
                                resultSet.getString("Nombre"));

                        resultado.add(categoria);
                    }
                };
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
}
