package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer cantidad, Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();

		statement.execute("UPDATE producto SET "
			+ "NOMBRE = '" + nombre + "'"
			+ ", DESCRIPCION = '"+ descripcion + "'"
			+ ", CANTIDAD = " + cantidad
			+ " WHERE ID = " + id);
		int updateCount = statement.getUpdateCount();

		con.close();

		return updateCount;

	}

	public int eliminar(Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();

		statement.execute("DELETE FROM producto WHERE ID = " + id);

		return statement.getUpdateCount();
	}

	public List<Map<String, String>> listar() throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();

		boolean result = statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM producto");

		ResultSet resultSet = statement.getResultSet();
		List<Map<String, String>> resultado = new ArrayList<>();
		while(resultSet.next()){
			Map<String, String> fila = new HashMap<>();
			fila.put("ID", String.valueOf(resultSet.getInt("ID")));
			fila.put("NOMBRE", resultSet.getString("NOMBRE"));
			fila.put("DESCRIPCION", resultSet.getString("DESCRIPCION"));
			fila.put("CANTIDAD", String.valueOf(resultSet.getInt("CANTIDAD")));

			resultado.add(fila);

		}

		con.close();


		return resultado;
	}

    public void guardar(Map<String, String> producto) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		Connection con = factory.recuperaConexion();

		PreparedStatement statement = con.prepareStatement(
				"INSERT INTO producto(nombre, descripcion, cantidad)"
						+ "(nombre, descripcion, cantidad)"
						+ "VALUES (?,?,?)",
				Statement.RETURN_GENERATED_KEYS);

		statement.setString(1, producto.get("NOMBRE"));
		statement.setString(2, producto.get("DESCRIPCION"));
		statement.setInt(3, Integer.valueOf(producto.get("CANTIDAD")));

		statement.execute();

		ResultSet resultSet = statement.getGeneratedKeys();

		while (resultSet.next()){
			resultSet.getInt(1);
		}

	}

}
