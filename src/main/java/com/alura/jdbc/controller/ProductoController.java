package com.alura.jdbc.controller;

import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Producto;
import com.alura.jdbc.dao.ProductoDAO;

import java.sql.*;
import java.util.List;

public class ProductoController {

	private ProductoDAO productoDAO;
	public ProductoController(){
		this.productoDAO = new ProductoDAO(new ConnectionFactory().recuperaConexion());
	}

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

	public List<Producto> listar(){

		return productoDAO.listar();
	}

    public void guardar(Producto producto){
		productoDAO.guardar(producto);
	}



}
