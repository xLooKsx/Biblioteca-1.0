package br.biblioteca.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.biblioteca.to.LivroTO;

public class LivrosDAO {
	
	private Connection connection;
	Logger logger = Logger.getLogger(LivrosDAO.class.getName());

	public LivrosDAO() {
	
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public List<LivroTO> getLivros(){
		
		List<LivroTO> livros = new ArrayList<LivroTO>();
		
		try {
			String sql = "select * from acervo";
			PreparedStatement stm = this.connection.prepareStatement(sql);
			stm.execute();
			
			ResultSet rs = stm.executeQuery();
			
			while(rs.next()){
				
				LivroTO livro = new LivroTO();	
				livro.setId(rs.getInt(1));
				livro.setNome(rs.getString(2));
				livro.setDescricao(rs.getString(3));
				livro.setAutor(rs.getString(4));
				
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate(5));
				livro.setAno(data.getTime());
				
				livro.setCirc(rs.getBoolean(6));
				livro.setEdicao(rs.getInt(7));
				livro.setEditora(rs.getString(8));
				livro.setEmprestado(rs.getBoolean(9));	
				livro.setTipo(rs.getString(10));
				
				livros.add(livro);
			}
			
			rs.close();			
			stm.close();
			logger.log(Level.INFO, "Lista de livros recuperdada com sucesso!");
			return livros;
		} catch (SQLException e) {
//			logger.log(Level.WARNING, "Não foi possivel retornar a lista de livros: {0}", e);
			throw new RuntimeException("Não foi possivel retornar a lista de livros: {0}", e);
		}finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean atualizaLivro(LivroTO livro){
		
		String sql = "UPDATE acervo SET nome_do_livro=?, descricao=?, autor=?, "
				+ "publicacao=?, circ=?, edicao=?, editora=?,is_emprestado=?, tipo=?  "
				+ "WHERE id_acervo=?;";
		
		try{
			PreparedStatement stm = this.connection.prepareStatement(sql);
			
			stm.setString(1, livro.getNome());
			stm.setString(2, livro.getDescricao());
			stm.setString(3, livro.getAutor());
			stm.setDate(4, new Date(livro.getAno().getTime()));
			stm.setBoolean(5, livro.isCirc());
			stm.setInt(6, livro.getEdicao());
			stm.setString(7, livro.getEditora());
			stm.setBoolean(8, livro.isEmprestado());
			stm.setString(9, livro.getTipo());
			stm.setLong(10, livro.getId());
			
			stm.execute();
			logger.log(Level.INFO, "Livro atualizado com sucesso!");
			logger.log(Level.SEVERE, "Informaçoes de parametros recebidos: {0}", new Date(livro.getAno().getTime()));
			stm.close();
			
			return true;
			
		}catch(SQLException e){
			logger.log(Level.SEVERE, "Não foi possivel atualizar o registro do livro desejado: {0}", e.getMessage());
		}finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public LivroTO buscaLivro(int id){
		
		try{
			String sql = "select * from acervo where id_acervo = ?";
			PreparedStatement stm = this.connection.prepareStatement(sql);
			
			stm.setInt(1, id);
			stm.execute();
			ResultSet rs = stm.executeQuery();
			
			if(rs.next()){
				
				LivroTO livro = new LivroTO();	
				livro.setId(rs.getInt(1));
				livro.setNome(rs.getString(2));
				livro.setDescricao(rs.getString(3));
				livro.setAutor(rs.getString(4));
				
				Calendar data = Calendar.getInstance();
				data.setTime(rs.getDate(5));
				livro.setAno(data.getTime());
				
				livro.setCirc(rs.getBoolean(6));
				livro.setEdicao(rs.getInt(7));
				livro.setEditora(rs.getString(8));
				livro.setEmprestado(rs.getBoolean(9));	
				livro.setTipo(rs.getString(10));
				livro.setReserva(rs.getBoolean(11));
				
				rs.close();
				stm.close();
				return livro;
			}else {
				rs.close();
				stm.close();
				return null;
			}		
		}catch (Exception e) {
			throw new RuntimeException("Não foi possivel retornar o livro: {0}", e);
		}finally {
			try {
				
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
//	public LivroTO emprestimoLivro(int id){
//		
//		try{
//			String sql = "select * from acervo where id_acervo = ? and circ = true";
//			PreparedStatement stm = this.connection.prepareStatement(sql);
//			
//			stm.setInt(1, id);
//			stm.execute();
//			ResultSet rs = stm.executeQuery();
//			
//			if(rs.next()){
//				
//				LivroTO livro = new LivroTO();	
//				livro.setId(rs.getInt(1));
//				livro.setNome(rs.getString(2));
//				livro.setDescricao(rs.getString(3));
//				livro.setAutor(rs.getString(4));
//				
//				Calendar data = Calendar.getInstance();
//				data.setTime(rs.getDate(5));
//				livro.setAno(data.getTime());
//				
//				livro.setCirc(rs.getBoolean(6));
//				livro.setEdicao(rs.getInt(7));
//				livro.setEditora(rs.getString(8));
//				livro.setEmprestado(rs.getBoolean(9));	
//				livro.setTipo(rs.getString(10));
//				
//				stm.close();
//				return livro;
//			}else {
//				stm.close();
//				return null;
//			}		
//		}catch (Exception e) {
//			throw new RuntimeException("Não foi possivel retornar o livro: {0}", e);
//		}finally {
//			try {
//				
//				this.connection.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	public boolean apagarLivro(int id){
		
		String sql = "delete from acervo where id_acervo = ?;";
		
		PreparedStatement stm;
		try {
			stm = this.connection.prepareStatement(sql);
			stm.setInt(1, id);
			stm.execute();		
			stm.close();
			logger.log(Level.INFO, "Livro deletado com sucesso!");
			return true;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Não foi possivel apagar o livro: {0}", e);			
		}finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public void realizaEmprestimo(int id){
		
		String sql = "update acervo set is_emprestado = true where id_acervo = ?;";
		
		try{
			PreparedStatement stm = this.connection.prepareStatement(sql);
			stm.setInt(1, id);
			stm.execute();
			stm.close();
			logger.log(Level.INFO, "Alteração do estado de emprestimo feito com sucesso");
		}catch (Exception e) {
			logger.log(Level.INFO, "Falha ao alterar o estado de emprestimo do livro");
		}
		
	}
	
	public void adicionaReserva(int id){
		
		String sql = "update acervo set reservado = true where id_acervo = ?;";
		
		try{
			PreparedStatement stm = this.connection.prepareStatement(sql);
			stm.setInt(1, id);
			stm.execute();
			stm.close();
			logger.log(Level.INFO, "Alteração do estado de emprestimo feito com sucesso");
		}catch (Exception e) {
			logger.log(Level.INFO, "Falha ao alterar o estado de emprestimo do livro");
		}
		
	}
	
	public void removeReserva(int id){
		
		String sql = "update acervo set reservado = false where id_acervo = ?;";
		
		try{
			PreparedStatement stm = this.connection.prepareStatement(sql);
			stm.setInt(1, id);
			stm.execute();
			stm.close();
			logger.log(Level.INFO, "Alteração do estado de emprestimo feito com sucesso");
		}catch (Exception e) {
			logger.log(Level.INFO, "Falha ao alterar o estado de emprestimo do livro");
		}
		
	}
	
	public boolean situacaoReserva(int id){

		boolean reserva = false;
		String sql = "select reservado from acervo where id_acervo = ?;";
		
		try{
			PreparedStatement stm = this.connection.prepareStatement(sql);
			stm.setInt(1, id);
			stm.execute();
			stm.close();
			ResultSet rs = stm.executeQuery();			
			if(rs.next()){
				
				reserva = rs.getBoolean(1);
				
				rs.close();
				stm.close();
				logger.log(Level.INFO, "Alteração do estado de reserva foi um sucesso");
				return reserva;
			}
			stm.close();
		}catch (Exception e) {
			logger.log(Level.INFO, "Alteração do estado de reserva foi falho");
		}
		
		return reserva;	
	}
	
	public void finalizaEmprestimo(int id){
		
		String sql = "update acervo set is_emprestado = false where id_acervo = ?;";
		
		try{
			PreparedStatement stm = this.connection.prepareStatement(sql);
			stm.setInt(1, id);
			stm.execute();
			stm.close();
			logger.log(Level.INFO, "Alteração do estado de emprestimo feito com sucesso");
		}catch (Exception e) {
			logger.log(Level.INFO, "Falha ao alterar o estado de emprestimo do livro");
		}
		
	}
	
	public void cadastrarLivro(LivroTO livro){
		
		String sql = "INSERT INTO acervo(id_acervo , nome_do_livro, descricao, autor, publicacao, circ, edicao, editora, is_emprestado, tipo, reservado)"
															+ "values(?, ?, ?, ?, ?, ?, ?, ?, false, ?, false);";
		
		try{
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			
			//seta os valores
			stmt.setLong(1, livro.getId());
			stmt.setString(2, livro.getNome());
			stmt.setString(3, livro.getDescricao());
			stmt.setString(4, livro.getAutor());
			stmt.setDate(5, new Date(livro.getPublicacao().getTime()));
			stmt.setBoolean(6, livro.isCirc());
			stmt.setInt(7, livro.getEdicao());
			stmt.setString(8, livro.getEditora());
			stmt.setString(9, livro.getTipo());
			
			
			stmt.execute();
			stmt.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
