package com.alura.jdbc.dao;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    final private Connection con;
    public ProductoDAO(Connection con){
        this.con = con;
    }

    public void guardar(Producto producto) {

        try(con) {

            try (PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO producto" + "(nombre, descripcion, cantidad, categoria_id)"
                            + "VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                System.out.println("TRY-CATCH");

                ejecutaRegistro(producto, statement);

            } catch (SQLException e) {
                throw new RuntimeException();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void ejecutaRegistro(Producto producto, PreparedStatement statement) throws SQLException {
        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());
        statement.setInt(4, producto.getCategoriaId());

        statement.execute();

        try(ResultSet resultSet = statement.getGeneratedKeys()){
            while (resultSet.next()){
                producto.setId(resultSet.getInt(1));
                resultSet.getInt(1);
            }
        }

    }

    public List<Producto> listar() {
        List<Producto> resultado = new ArrayList<>();

        try(PreparedStatement statement = con.prepareStatement(
                "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM producto")) {
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Producto fila = new Producto(resultSet.getInt("ID"),
                        resultSet.getString("NOMBRE"),
                        resultSet.getString("DESCRIPCION"),
                        resultSet.getInt("CANTIDAD")
                );

                resultado.add(fila);

            }
            return resultado;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Producto> listar(Object categoriaId) {
        List<Producto> resultado = new ArrayList<>();
        var querySelect = "SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM producto WHERE categoria_id = ?";
        try(PreparedStatement statement = con.prepareStatement(querySelect)) {
            statement.setInt(1, (Integer) categoriaId);
            statement.execute();

            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Producto fila = new Producto(resultSet.getInt("ID"),
                        resultSet.getString("NOMBRE"),
                        resultSet.getString("DESCRIPCION"),
                        resultSet.getInt("CANTIDAD")
                );

                resultado.add(fila);

            }
            return resultado;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

