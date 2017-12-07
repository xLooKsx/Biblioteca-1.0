package br.biblioteca.to;

import java.util.Date;

public class ReservaTO {
	
	private int id;
	private Date dataLimite;
	private int idAcervo;	
	private int ClienteMatricula;

	public ReservaTO() {
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDataLimite() {
		return dataLimite;
	}
	public void setDataLimite(Date dataLimite) {
		this.dataLimite = dataLimite;
	}
	public int getIdAcervo() {
		return idAcervo;
	}
	public void setIdAcervo(int idAcervo) {
		this.idAcervo = idAcervo;
	}
	public int getClienteMatricula() {
		return ClienteMatricula;
	}
	public void setClienteMatricula(int clienteMatricula) {
		ClienteMatricula = clienteMatricula;
	}
	
	
}
