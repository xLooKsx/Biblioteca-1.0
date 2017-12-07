package br.biblioteca.to;

public class ClienteTO {
	
	private int matricula;
	private String senha;
	private String nome;
	private String ultimo_nome;
	private String logradouro;
	private String tipo_logradouro;
	private String comp_logradouro;
	private long telefone;
	private String email;
	private boolean isAtiva;
	private boolean biblioteca;
	private String tipoUsuario;
	private int qtdLivro;
	private int qtdRevista;
	
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean isAtiva() {
		return isAtiva;
	}
	public void setAtiva(boolean isAtiva) {
		this.isAtiva = isAtiva;
	}
	public int getQtdLivro() {
		return qtdLivro;
	}
	public void setQtdLivro(int qtdLivro) {
		this.qtdLivro = qtdLivro;
	}
	public int getQtdRevista() {
		return qtdRevista;
	}
	public void setQtdRevista(int qtdRevista) {
		this.qtdRevista = qtdRevista;
	}
	public String getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getUltimo_nome() {
		return ultimo_nome;
	}
	public void setUltimo_nome(String ultimo_nome) {
		this.ultimo_nome = ultimo_nome;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getTipo_logradouro() {
		return tipo_logradouro;
	}
	public void setTipo_logradouro(String tipo_logradouro) {
		this.tipo_logradouro = tipo_logradouro;
	}
	public String getComp_logradouro() {
		return comp_logradouro;
	}
	public void setComp_logradouro(String comp_logradouro) {
		this.comp_logradouro = comp_logradouro;
	}
	public long getTelefone() {
		return telefone;
	}
	public void setTelefone(long telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isBiblioteca() {
		return biblioteca;
	}
	public void setBiblioteca(boolean biblioteca) {
		this.biblioteca = biblioteca;
	}
	
}
