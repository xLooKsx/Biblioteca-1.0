package br.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.biblioteca.to.ClienteTO;
import br.biblioteca.to.EmprestimoTO;
import br.biblioteca.to.LivroTO;
import br.biblioteca.to.LoginTO;

public class ReservaDAO {
	
	private Connection connection;
	Logger logger = Logger.getLogger(LoginDAO.class.getName());
	
	public ReservaDAO() {
		super();
		this.connection = new ConnectionFactory().getConnection();
	}

	
	public void realizaReserva(LivroTO livro, ClienteTO cliente, EmprestimoTO emprestimo){
		
		java.util.Date dataLimite = emprestimo.getSaidaLivro();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 8);
		dataLimite.setTime(cal.getTime().getTime());
		
		String sql= "insert into reserva(data_limite, acervo_idacervo, usuarios_matricula)"
						+ "values(?, ?, ?);";
		
		try{
			PreparedStatement stm = this.connection.prepareStatement(sql);
			stm.setDate(1, new java.sql.Date(dataLimite.getTime()));
			stm.setInt(2, livro.getId());
			stm.setInt(3, cliente.getMatricula());

			stm.execute();
			logger.log(Level.INFO, "Reserva executada com sucesso!");
			stm.close();
		}catch (Exception e) {
			throw new RuntimeException("Falha ao realizar a reserva: " + e.getMessage());
		}
	}
	
	public boolean verificarReserva(LivroTO livro, EmprestimoTO emprestimo){
		
		java.util.Date dataLimite = emprestimo.getSaidaLivro();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 8);
		dataLimite.setTime(cal.getTime().getTime());
		
		
		String sql= "select * from reserva where data_limite = ? and acervo_idacervo  = ?";
		
		try{
			PreparedStatement stm = this.connection.prepareStatement(sql);
			stm.setDate(1, new java.sql.Date(dataLimite.getTime()));
			stm.setInt(2, livro.getId());

			stm.execute();
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {
				return true;
			}
			logger.log(Level.INFO, "Reserva executada com sucesso!");
			stm.close();
			return false;
		}catch (Exception e) {
			throw new RuntimeException("Falha ao realizar a verificar a reserva: " + e.getMessage());
		}
	}
}
