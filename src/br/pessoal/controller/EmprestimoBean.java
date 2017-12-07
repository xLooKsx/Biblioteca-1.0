package br.pessoal.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.biblioteca.to.EmprestimoTO;
import br.biblioteca.to.LivroTO;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class EmprestimoBean implements Serializable{
	
	EmprestimoTO emprestimo = new EmprestimoTO();
	
	public void adicionaLivroCarrinho(){
		
		
	}

	public EmprestimoTO getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(EmprestimoTO emprestimo) {
		this.emprestimo = emprestimo;
	}	
}
