package br.biblioteca.to;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginTO implements Serializable{
	
	private Integer usuario;
	private String password = "";
	private boolean bibliotecario;
	private boolean ativa;
	private String tipoUsuario;
	
	public LoginTO() {
	}
	
	public Integer getUsuario() {
		return usuario;
	}
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isBibliotecario() {
		return bibliotecario;
	}
	public void setBibliotecario(boolean bibliotecario) {
		this.bibliotecario = bibliotecario;
	}
	public boolean isAtiva() {
		return ativa;
	}
	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	
}
