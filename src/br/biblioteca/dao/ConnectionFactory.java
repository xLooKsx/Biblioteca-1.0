package br.biblioteca.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.biblioteca.utils.BibliotecaUtils;

public class ConnectionFactory {
	
	public Connection getConnection(){
		
		try{
			Class.forName(BibliotecaUtils.getProperty("config.bd.class"));
			return DriverManager.getConnection(BibliotecaUtils.getProperty("config.bd.biblioteca"), "impacta", "impacta");
		}catch(SQLException e){
			throw new RuntimeException(e);
		}catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

}
