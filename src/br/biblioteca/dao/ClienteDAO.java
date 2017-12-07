package br.biblioteca.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.biblioteca.to.ClienteTO;
import br.biblioteca.to.LivroTO;

public class ClienteDAO {
	
	private Connection connection;
	Logger logger = Logger.getLogger(LivrosDAO.class.getName());

	public ClienteDAO() {
	
		this.connection = new ConnectionFactory().getConnection();
	}
	
	public ClienteTO buscaCliente(int id){
		
		try{
			String sql = "select matricula, nome, is_ativa, tipo_usuario, qtd_livro, qtd_revista from usuarios "
						+ "WHERE matricula = ? and is_ativa = true";
			PreparedStatement stm = this.connection.prepareStatement(sql);
			
			stm.setInt(1, id);
			stm.execute();
			ResultSet rs = stm.executeQuery();
			
			if(rs.next()){
					
				ClienteTO cliente = new ClienteTO();
				cliente.setMatricula(rs.getInt(1));
				cliente.setNome(rs.getString(2));
				cliente.setAtiva(rs.getBoolean(3));
				cliente.setTipoUsuario(rs.getString(4));
				cliente.setQtdLivro(rs.getInt(5));
				cliente.setQtdRevista(rs.getInt(6));
				
				logger.log(Level.INFO, "Cliente recuperado com sucesso");
				rs.close();
				stm.close();
				return cliente;				
			}else {
				logger.log(Level.INFO, "Cliente Não existe");
				rs.close();
				stm.close();
				return null;
			}		
		}catch (Exception e) {
			throw new RuntimeException("Não foi possivel retornar o Cliente: {0}", e);
		}finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	public void adicionaMaterialUsuario(LivroTO livro, ClienteTO cliente){
		
		int qtdMaterial = 0;
		String sql;
		
		try{
			if (livro.getTipo().toUpperCase().charAt(0) == 'L') {
				qtdMaterial = cliente.getQtdLivro()+1;
				sql = "update usuarios set qtd_livro = ? where matricula = ?";
			}else{
				qtdMaterial = cliente.getQtdRevista()+1;
				sql = "update usuarios set qtd_revista = ? where matricula = ?";
			}
			
			PreparedStatement stm = this.connection.prepareStatement(sql);
			
			stm.setInt(1, qtdMaterial);
			stm.setInt(2, cliente.getMatricula());
			
			stm.execute();
			stm.close();
			logger.log(Level.INFO, "Material do usuario atualizado");
		
		}catch (Exception e) {
			throw new RuntimeException("Não foi possivel atualizar o material do usuario: " + e.getMessage());
		}finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void retirarMaterialUsuario(LivroTO livro, ClienteTO cliente){
		
		int qtdMaterial = 0;
		String sql;
		
		try{
			if (livro.getTipo().toUpperCase().charAt(0) == 'L') {
				qtdMaterial = cliente.getQtdLivro()-1;
				sql = "update usuarios set qtd_livro = ? where matricula = ?";
			}else{
				qtdMaterial = cliente.getQtdRevista()-1;
				sql = "update usuarios set qtd_revista = ? where matricula = ?";
			}
			
			PreparedStatement stm = this.connection.prepareStatement(sql);
			
			stm.setInt(1, qtdMaterial);
			stm.setInt(2, cliente.getMatricula());
			
			stm.execute();
			stm.close();
			logger.log(Level.INFO, "Material do usuario atualizado");
		
		}catch (Exception e) {
			throw new RuntimeException("Não foi possivel atualizar o material do usuario: " + e.getMessage());
		}finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean atualizaCliente(ClienteTO user) {

		String sql = "UPDATE usuarios SET senha=?, nome=?, ultimo_nome=?, logradouro=?, tipo_logradouro=?,"
				     + " comp_logradouro=?, telefone=?, email=?, is_ativa=?, is_biblioteca=?, tipo_usuario=? where matricula=?";

		try {
			PreparedStatement stm = this.connection.prepareStatement(sql);

			stm.setString(1, user.getSenha());
			stm.setString(2, user.getNome());
			stm.setString(3, user.getUltimo_nome());
			stm.setString(4, user.getLogradouro());
			stm.setString(5, user.getTipo_logradouro());
			stm.setString(6, user.getComp_logradouro());
			stm.setLong(7, user.getTelefone());
			stm.setString(8, user.getEmail());
			stm.setBoolean(9, user.isAtiva());
			stm.setBoolean(10, user.isBiblioteca());
			stm.setString(11, user.getTipoUsuario());
			stm.setLong(12, user.getMatricula());
			
			stm.execute();
			stm.close();

			return true;

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Não foi possivel atualizar o registro do livro desejado: {0}", e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public ClienteTO existeUsuario(Integer usuario) {

		String sql = "select * from usuarios WHERE matricula=?;";

		try {
			PreparedStatement stm = this.connection.prepareStatement(sql);
			stm.setLong(1, usuario != null ? usuario : 0);
			stm.execute();

			ResultSet rs = stm.executeQuery();
			ClienteTO user = new ClienteTO();

			if (rs.next()) {

				// aqui setar atributos do banco
				user.setMatricula(rs.getInt(1));
				user.setSenha(rs.getString(2));
				user.setNome(rs.getString(3));
				user.setUltimo_nome(rs.getString(4));
				user.setLogradouro(rs.getString(5));
				user.setTipo_logradouro(rs.getString(6));
				user.setComp_logradouro(rs.getString(7));
				user.setTelefone(rs.getLong(8));
				user.setEmail(rs.getString(9));
				user.setAtiva(rs.getBoolean(10));
				user.setBiblioteca(rs.getBoolean(11));
				user.setTipoUsuario(rs.getString(12));

				rs.close();
				stm.close();
				logger.log(Level.INFO, "Usuario encontrado!");
				return user;
			}

			rs.close();
			stm.close();
			return null;
		} catch (SQLException e) {
			throw new RuntimeException("Não foi possivel fazer a consulta de usuarios: " + e.getMessage());
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean cadastrarCliente(ClienteTO user) {

		String sql = "INSERT INTO usuarios(matricula, senha, nome, ultimo_nome, logradouro, tipo_logradouro,comp_logradouro, telefone, "
							+ "email, is_ativa, is_biblioteca, tipo_usuario, qtd_livro, qtd_revista)"
							+ "values(?, ?, ?, ?, ?, ?, ?, ?, ?, true, ?, ?, 0, 0);";

		try {

			PreparedStatement stmt = connection.prepareStatement(sql);

			// seta os valores
			stmt.setLong(1, user.getMatricula());
			stmt.setString(2, user.getSenha());
			stmt.setString(3, user.getNome());
			stmt.setString(4, user.getUltimo_nome());
			stmt.setString(5, user.getLogradouro());
			stmt.setString(6, user.getTipo_logradouro());
			stmt.setString(7, user.getComp_logradouro());
			stmt.setLong(8, user.getTelefone());
			stmt.setString(9, user.getEmail());
			stmt.setBoolean(10, user.isBiblioteca());
			stmt.setString(11, user.getTipoUsuario());

			stmt.execute();
			stmt.close();
			return true;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				this.connection.close();
			} catch (SQLException e2) {
				throw new RuntimeException("Não foi possivel fechar a conexão:{0}", e2);
			}
		}
	}

}
