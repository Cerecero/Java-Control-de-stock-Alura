package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();

		try(PreparedStatement statement = con.prepareStatement("UPDATE producto SET "
				+ "NOMBRE = ?"
				+ ", DESCRIPCION = ?"
				+ ", CANTIDAD = ?"
				+ " WHERE ID = ?");){
			statement.setString(1, nombre);
			statement.setString(2, descripcion);
			statement.setInt(3, cantidad);
			statement.setInt(4, id);


			statement.execute();
			int updateCount = statement.getUpdateCount();

			con.close();

			return updateCount;
		}
	}

	public int eliminar(Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();

		try(PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE ID = ?");){
			statement.setInt(1, id);
			statement.execute();

		return statement.getUpdateCount();
		}
	}

	public List<Map<String, String>> listar() throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();

		try(PreparedStatement statement = con.prepareStatement(
				"SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM producto");) {
			statement.execute();

			ResultSet resultSet = statement.getResultSet();
			List<Map<String, String>> resultado = new ArrayList<>();
			while (resultSet.next()) {
				Map<String, String> fila = new HashMap<>();
				fila.put("ID", String.valueOf(resultSet.getInt("ID")));
				fila.put("NOMBRE", resultSet.getString("NOMBRE"));
				fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
				fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));

				resultado.add(fila);

			}
			return resultado;
		}
	}

    public void guardar(Producto producto) throws SQLException {



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
