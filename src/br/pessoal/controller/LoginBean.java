package br.pessoal.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.biblioteca.dao.LoginDAO;
import br.biblioteca.to.LoginTO;
import br.biblioteca.utils.BibliotecaUtils;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable{	
	
	private LoginTO login = new LoginTO();
	Logger logger = Logger.getLogger(LoginBean.class.getName());
	
	public String validateUsernamePassword(){
		
//		boolean valid = new LoginDAO().existeUsario(login.getUser());
		LoginTO loginTemp = new LoginDAO().existeUsario(login.getUsuario());
		FacesContext fc = FacesContext.getCurrentInstance();
		
		if (loginTemp != null && loginTemp.getPassword().equals(login.getPassword()) && loginTemp.isAtiva()) {			
		
			login = loginTemp;			
			HttpSession session = BibliotecaUtils.getSession();
			session.setAttribute("username", login.getUsuario());
			return "menu";
		}else{
			
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario ou senha incorreto(s)", "Insira usuario e senha corretos"));
			fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, BibliotecaUtils.getProperty("label_mensagem_erro_usuario_senha_invalida"), null));
//			fc.addMessage(null, new FacesMessage("Erro", "Login ou senha invalidos"));
//			return "login";
			return "";
		}
	}
	
	public String logout(){
		
		new BibliotecaUtils();
		HttpSession session = BibliotecaUtils.getSession();
		session.invalidate();
		login = null;
		return "login";
	}

	public LoginTO getLogin() {
		return login;
	}

	public void setLogin(LoginTO login) {
		this.login = login;
	}	
}
