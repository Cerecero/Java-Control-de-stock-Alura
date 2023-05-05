package com.alura.jdbc.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoController {

	public void modificar(String nombre, String descripcion, Integer id) {
		// TODO
	}

	public void eliminar(Integer id) {
		// TODO
	}

	public List<?> listar() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
				"root",
				"password");
		Statement statement = con.createStatement();

		boolean result = statement.execute("SELECT ID, NOMBRE, DESCRIPCION, CANTIDAD FROM producto");

		ResultSet resultSet = statement.getResultSet();

		System.out.println(result);
		con.close();

		return new ArrayList<>();
	}

    public void guardar(Object producto) {
		// TODO
	}

}
