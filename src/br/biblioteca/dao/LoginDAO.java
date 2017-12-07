package br.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.biblioteca.to.LoginTO;


public class LoginDAO {
	
	private Connection connection;
	Logger logger = Logger.getLogger(LoginDAO.class.getName());

	public LoginDAO() {	
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public LoginTO existeUsario(Integer usuario){
		
		
		String sql = "select * from usuarios WHERE matricula=?;";
		
		try{
			PreparedStatement stm = this.connection.prepareStatement(sql);
			stm.setLong(1, usuario!=null?usuario:0);
			stm.execute();
			
			ResultSet rs = stm.executeQuery();
			LoginTO login = new LoginTO();
			
			if (rs.next()) {		
				
				login.setPassword(rs.getString(2));
				login.setUsuario(rs.getInt(1));
				login.setAtiva(rs.getBoolean(10));
				login.setBibliotecario(rs.getBoolean(11));
				login.setTipoUsuario(rs.getString(12));
				
				logger.log(Level.INFO, "Usuario encontrado!");
				return login;	
			}
			
			return null;
		}catch(SQLException e){
//			logger.log(Level.SEVERE, "Não foi possivel fazer a consulta de usuarios: {0}", e);
			throw new RuntimeException("Não foi possivel fazer a consulta de usuarios: " + e.getMessage());
		}finally {			
			try {
				this.connection.close();
			} catch (SQLException e) {			
				e.printStackTrace();
			}
		}
	}		
}
