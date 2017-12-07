package br.biblioteca.to;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class EmprestimoTO implements Serializable {
	
	private Date saidaLivro;
	private Date entradaLivro;
	private String usuario;
	private int id;
	private boolean atraso;
	private int matricula;
	private int idLivro;
	
	public Date getSaidaLivro() {
		return saidaLivro;
	}
	public void setSaidaLivro(Date saidaLivro) {
		this.saidaLivro = saidaLivro;
	}
	public Date getEntradaLivro() {
		return entradaLivro;
	}
	public void setEntradaLivro(Date entradaLivro) {
		this.entradaLivro = entradaLivro;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isAtraso() {
		return atraso;
	}
	public void setAtraso(boolean atraso) {
		this.atraso = atraso;
	}
	public int getIdLivro() {
		return idLivro;
	}
	public void setIdLivro(int idLivro) {
		this.idLivro = idLivro;
	}
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
}
