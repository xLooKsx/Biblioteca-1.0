package br.pessoal.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.TabChangeEvent;

import br.biblioteca.dao.ClienteDAO;
import br.biblioteca.dao.EmprestimoDAO;
import br.biblioteca.dao.LivrosDAO;
import br.biblioteca.dao.ReservaDAO;
import br.biblioteca.to.ClienteTO;
import br.biblioteca.to.EmprestimoTO;
import br.biblioteca.to.LivroTO;
import br.biblioteca.to.LoginTO;
import br.biblioteca.to.ReservaTO;
import br.biblioteca.utils.BibliotecaUtils;

@SuppressWarnings("serial")
@ManagedBean(name = "menuBean")
@ViewScoped
public class MenuBean implements Serializable {

	ReservaTO reserva = new ReservaTO();
	LivroTO livro = new LivroTO();
	ClienteTO cliente = new ClienteTO();	
	EmprestimoTO emprestimo = new EmprestimoTO();
	private boolean painelEmprestimo = false;
	private boolean painelDevolucao = false;
	Date dataAtual = Calendar.getInstance().getTime();

	public List<LivroTO> getLivros() {

		List<LivroTO> livros = new LivrosDAO().getLivros();
		return livros;
	}

	public void alterarLivro() {

		FacesContext fc = FacesContext.getCurrentInstance();
		if (new LivrosDAO().atualizaLivro(livro)) {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					BibliotecaUtils.getProperty("msg_Situacao_positiva_alteracao_Livro"), null));
		} else {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					BibliotecaUtils.getProperty("msg_Situacao_negativa_alteracao_Livro"), null));
		}
	}

	public void mensagem() {
		FacesContext fc = FacesContext.getCurrentInstance();
		System.out.println("Olha eu aqui :)" + livro.isCirc());
		// fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
		// BibliotecaUtils.getProperty("msg_Situacao_negativa_encontro_livro"),
		// null));
	}

	public void apagarLivro() {

		FacesContext fc = FacesContext.getCurrentInstance();
		if (new LivrosDAO().apagarLivro(livro.getId())) {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					BibliotecaUtils.getProperty("msg_Situacao_positiva_deleta_livro"), null));
		} else {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					BibliotecaUtils.getProperty("msg_Situacao_negativa_deleta_livro"), null));
		}
	}
	
	public void checkarDataDevolucao(){
		
		if (dataAtual.getTime() > emprestimo.getSaidaLivro().getTime()) {
			cliente.setAtiva(false);
		}		
	}
	
	public void checkarDataReserva(){
		
		if (new ReservaDAO().verificarReserva(livro, emprestimo)) {
			livro.setReserva(true);
		}else {
			livro.setReserva(false);
		}		
	}
	
	
	public void realizaReserva(){
		
		FacesContext fc = FacesContext.getCurrentInstance();
		EmprestimoTO emprestimoTemp = new EmprestimoDAO().checkarEmprestimo(cliente.getMatricula(), livro);
		ClienteTO clienteTemp = new ClienteDAO().buscaCliente(emprestimoTemp.getMatricula());
		LivroTO livroTemp = new LivrosDAO().buscaLivro(emprestimoTemp.getIdLivro());
		if (emprestimoTemp != null && clienteTemp != null && livroTemp != null) {
			this.cliente = clienteTemp;
			this.emprestimo = emprestimoTemp;
			this.livro = livroTemp;
			checkarDataReserva();
			if (!livro.isReserva()) {
				new ReservaDAO().realizaReserva(livro, cliente, emprestimo);
				new LivrosDAO().adicionaReserva(livro.getId());
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						BibliotecaUtils.getProperty("msg_Situacao_positiva_reserva_livro"), null));
				this.cliente.setMatricula(0);
			}else {
				this.cliente.setMatricula(0);
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						BibliotecaUtils.getProperty("msg_Situacao_negativa_reserva_livro_emprestado"), null));
			}
			
		}else{
			this.cliente.setMatricula(0);
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					BibliotecaUtils.getProperty("msg_Situacao_negativa_reserva_livro"), null));
		}
	}
	
	public void realizaDevolucao() {

		FacesContext fc = FacesContext.getCurrentInstance();
		EmprestimoTO emprestimoTemp = new EmprestimoDAO().checkarEmprestimo(cliente.getMatricula(), livro);
		ClienteTO clienteTemp = new ClienteDAO().buscaCliente(cliente.getMatricula());
		LivroTO livroTemp = new LivrosDAO().buscaLivro(emprestimoTemp.getIdLivro());
		if (emprestimoTemp != null && clienteTemp != null && livroTemp != null) {
			this.cliente = clienteTemp;
			this.emprestimo = emprestimoTemp;
			this.livro = livroTemp;
			if (cliente.getQtdLivro() > 0 || cliente.getQtdRevista() > 0) {
				checkarDataDevolucao();
				new EmprestimoDAO().encerrarEmprestimo(emprestimo);
				new ClienteDAO().retirarMaterialUsuario(this.livro, this.cliente);
				new LivrosDAO().finalizaEmprestimo(this.livro.getId());
				this.livro = new LivrosDAO().buscaLivro(livro.getId());
				this.cliente.setMatricula(0);
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						BibliotecaUtils.getProperty("msg_Situacao_positiva_devolucao_livro"), null));
			} else {
				this.cliente.setMatricula(0);
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						BibliotecaUtils.getProperty("msg_Situacao_negativa_devolucao_livro"), null));
			}

		} else {
			this.livro = new LivrosDAO().buscaLivro(livro.getId());
			this.cliente.setMatricula(0);
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					BibliotecaUtils.getProperty("msg_Situacao_negativa_busca_cliente"), null));
		}

	}
	

	public void realizaEmprestimo() {

		FacesContext fc = FacesContext.getCurrentInstance();
		ClienteTO clienteTemp = new ClienteDAO().buscaCliente(cliente.getMatricula());
		if (clienteTemp != null) {
			this.cliente = clienteTemp;
			switch (cliente.getTipoUsuario().toUpperCase().charAt(0)) {
				case 'A':
					if (cliente.getQtdLivro() < 3 && cliente.getQtdRevista() < 2) {
						new EmprestimoDAO().adicionarEmprestimo(this.livro, this.cliente);
						new ClienteDAO().adicionaMaterialUsuario(this.livro, this.cliente);
						new LivrosDAO().realizaEmprestimo(this.livro.getId());
						this.livro = new LivrosDAO().buscaLivro(livro.getId());
						this.cliente.setMatricula(0);
						fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
								BibliotecaUtils.getProperty("msg_Situacao_positiva_emprestimo_livro"), null));
					}else{
						this.cliente.setMatricula(0);
						fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
								BibliotecaUtils.getProperty("msg_Situacao_negativa_emprestimo_livro"), null));
					}
					break;
				case 'P':
					if (cliente.getQtdLivro() < 5 && cliente.getQtdRevista() < 3){
						new EmprestimoDAO().adicionarEmprestimo(this.livro, this.cliente);
						new ClienteDAO().adicionaMaterialUsuario(this.livro, this.cliente);
						new LivrosDAO().realizaEmprestimo(this.livro.getId());
						this.livro = new LivrosDAO().buscaLivro(livro.getId());
						this.cliente.setMatricula(0);
						fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
								BibliotecaUtils.getProperty("msg_Situacao_positiva_emprestimo_livro"), null));
					}else{
						this.cliente.setMatricula(0);
						fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
								BibliotecaUtils.getProperty("msg_Situacao_negativa_emprestimo_livro"), null));
					}
					break;
				case 'F':
					if (cliente.getQtdLivro() < 3 || cliente.getQtdRevista() < 2) {
						new EmprestimoDAO().adicionarEmprestimo(this.livro, this.cliente);
						new ClienteDAO().adicionaMaterialUsuario(this.livro, this.cliente);
						new LivrosDAO().realizaEmprestimo(this.livro.getId());
						this.livro = new LivrosDAO().buscaLivro(livro.getId());
						this.cliente.setMatricula(0);
						fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
								BibliotecaUtils.getProperty("msg_Situacao_positiva_emprestimo_livro"), null));
						
					}else{
						this.cliente.setMatricula(0);
						this.livro = new LivrosDAO().buscaLivro(livro.getId());
						fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
								BibliotecaUtils.getProperty("msg_Situacao_negativa_emprestimo_livro"), null));
					}
					break;
					
			default:
				this.livro = new LivrosDAO().buscaLivro(livro.getId());
				this.cliente.setMatricula(0);
				fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						BibliotecaUtils.getProperty("msg_Situacao_desconhecida"), null));
				break;
			}

		} else {
			this.livro = new LivrosDAO().buscaLivro(livro.getId());
			this.cliente.setMatricula(0);
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					BibliotecaUtils.getProperty("msg_Situacao_negativa_busca_cliente"), null));
		}

	}
	
	public void casdastrarCliente() {        
		 FacesContext fc = FacesContext.getCurrentInstance();
	        if (new ClienteDAO().cadastrarCliente(cliente)){
	        	fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, BibliotecaUtils.getProperty("msg_Situacao_positiva_cadastro_Usuario"), null));
	        }else{
	        	fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, BibliotecaUtils.getProperty("msg_Situacao_negativa_cadastro_Usuario"), null));
	        }
	 }
	
	
	public void alterarCliente() {

		FacesContext fc = FacesContext.getCurrentInstance();
		if (new ClienteDAO().atualizaCliente(cliente)) {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					BibliotecaUtils.getProperty("msg_Situacao_positiva_alteracao_Usuario"), null));
		} else {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					BibliotecaUtils.getProperty("msg_Situacao_negativa_alteracao_Usuario"), null));
		}
	}
	
	public void cadastrarLivro(){
		new LivrosDAO().cadastrarLivro(livro);
	}
	
	public void devolucaoLivro() {
		FacesContext fc = FacesContext.getCurrentInstance();
		LivroTO livroTemp = new LivrosDAO().buscaLivro(livro.getId());
		if (livroTemp != null) {
			this.livro = livroTemp;
			this.painelDevolucao = true;
		} else {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					BibliotecaUtils.getProperty("msg_Situacao_negativa_encontro_livro"), null));
			this.painelEmprestimo = false;
		}

	}

	public void emprestimoLivro() {
		FacesContext fc = FacesContext.getCurrentInstance();
		LivroTO livroTemp = new LivrosDAO().buscaLivro(livro.getId());
		if (livroTemp != null) {
			this.livro = livroTemp;
			this.painelEmprestimo = true;
		} else {
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					BibliotecaUtils.getProperty("msg_Situacao_negativa_encontro_livro"), null));
			this.painelEmprestimo = false;
		}

	}
	
	public void validaLivro(LivroTO livro){
		
		if (livro.getNome() == "" || livro.getNome() == null) {
			
		}
		
	}

	public void onChange(TabChangeEvent event) {
		this.painelEmprestimo = false;
		this.painelDevolucao = false;
		this.livro = new LivroTO();
	}

	public boolean isPainelEmprestimo() {
		return painelEmprestimo;
	}

	public void setPainelEmprestimo(boolean painelEmprestimo) {
		this.painelEmprestimo = painelEmprestimo;
	}

	public LivroTO getLivro() {
		return livro;
	}

	public void setLivro(LivroTO livro) {
		this.livro = livro;
	}

	public ClienteTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteTO cliente) {
		this.cliente = cliente;
	}

	public boolean isPainelDevolucao() {
		return painelDevolucao;
	}

	public void setPainelDevolucao(boolean painelDevolucao) {
		this.painelDevolucao = painelDevolucao;
	}

	public EmprestimoTO getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(EmprestimoTO emprestimo) {
		this.emprestimo = emprestimo;
	}

	public ReservaTO getReserva() {
		return reserva;
	}

	public void setReserva(ReservaTO reserva) {
		this.reserva = reserva;
	}
	
	
}
