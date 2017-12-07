package br.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.biblioteca.to.ClienteTO;
import br.biblioteca.to.EmprestimoTO;
import br.biblioteca.to.LivroTO;
import br.biblioteca.to.LoginTO;

public class EmprestimoDAO {

	private Connection connection;
	Logger logger = Logger.getLogger(LivrosDAO.class.getName());

	public EmprestimoDAO() {

		this.connection = new ConnectionFactory().getConnection();
	}

	public EmprestimoTO checkarEmprestimo(int matricula, LivroTO livro) {

		String sql = "select idemprestimo, data_devolucao, acervo_idacervo from emprestimo where usuarios_matricula = ? "
							+ "and encerrado = false and acervo_idacervo = ? ;";

		try {
			PreparedStatement stm = this.connection.prepareStatement(sql);

			stm.setInt(1, matricula);
			stm.setInt(2, livro.getId());
			stm.execute();

			EmprestimoTO emprestimo = new EmprestimoTO();
			emprestimo.setMatricula(matricula);
			emprestimo.setIdLivro(livro.getId());
			
			ResultSet rs = stm.executeQuery();
			if (rs.next()) {


//				emprestimo.setEntradaLivro(rs.getDate(3));
				emprestimo.setSaidaLivro(rs.getDate(2));
				emprestimo.setId(rs.getInt(1));
				
				rs.close();
				stm.close();
				logger.log(Level.INFO, "emprestimo achado com sucesso");
				return emprestimo;
			} else {
				stm.close();
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException("Falha ao realizar o emprestimo: " + e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void encerrarEmprestimo(EmprestimoTO emprestimo) {

		String sql = "update emprestimo set encerrado = true where usuarios_matricula = ? "
								+ "and acervo_idacervo = ? and idemprestimo = ? and data_devolucao = ?";

		try {
			PreparedStatement stm = this.connection.prepareStatement(sql);

			stm.setInt(1, emprestimo.getMatricula());
			stm.setInt(2, emprestimo.getIdLivro());
			stm.setInt(3, emprestimo.getId());
			stm.setDate(4, (java.sql.Date) emprestimo.getSaidaLivro());

			stm.execute();
			stm.close();

			logger.log(Level.INFO, "Devolução realizado com sucesso");

		} catch (SQLException e) {
			throw new RuntimeException("Falha ao realizar o emprestimo: " + e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void adicionarEmprestimo(LivroTO livro, ClienteTO cliente) {

		java.util.Date dataAtual = Calendar.getInstance().getTime();
		java.util.Date dataDevolucao = Calendar.getInstance().getTime();

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 7);
		dataDevolucao.setTime(cal.getTime().getTime());

		String sql = "insert into emprestimo(usuarios_matricula, data_emprestimo, data_devolucao, acervo_idacervo, encerrado)"
				+ "values(?, ?, ?,?,false);";

		try {
			PreparedStatement stm = this.connection.prepareStatement(sql);

			stm.setInt(1, cliente.getMatricula());
			stm.setDate(2, new java.sql.Date(dataAtual.getTime()));
			stm.setDate(3, new java.sql.Date(dataDevolucao.getTime()));
			stm.setInt(4, livro.getId());

			stm.execute();
			stm.close();

			logger.log(Level.INFO, "Emprestimo realizado com sucesso");

		} catch (SQLException e) {
			throw new RuntimeException("Falha ao realizar o emprestimo: " + e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
