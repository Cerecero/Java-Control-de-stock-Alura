package com.alura.jdbc.persistencia;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

import java.sql.*;

public class PersistenciaProducto {
    private Connection con;
    public PersistenciaProducto(Connection con){
        this.con = con;
    }

    public void guardarProducto(Producto producto) throws SQLException {
        ConnectionFactory factory = new ConnectionFactory();
        Connection con = factory.recuperaConexion();


        try(con) {
            con.setAutoCommit(false);

            try (PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO producto" + "(nombre, descripcion, cantidad)"
                            + "VALUES (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);) {
                System.out.println("TRY-CATCH");

                ejecutaRegistro(producto, statement);

                con.commit();
                System.out.println("COMMIT");
            } catch (Exception e) {
                con.rollback();
                System.out.println("ROLLBACK");
            }
        }
    }
    private static void ejecutaRegistro(Producto producto, PreparedStatement statement) throws SQLException {
        statement.setString(1, producto.getNombre());
        statement.setString(2, producto.getDescripcion());
        statement.setInt(3, producto.getCantidad());

        statement.execute();

        try(ResultSet resultSet = statement.getGeneratedKeys();){
            while (resultSet.next()){
                producto.setId(resultSet.getInt(1));
                resultSet.getInt(1);
            }
        }

    }
}
