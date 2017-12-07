package br.biblioteca.utils;

import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.biblioteca.to.LivroTO;

public class BibliotecaUtils {
	
	public static HttpSession getSession(){
		
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest(){
		
		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
	}
	
	public static String getUsername(){
		
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getAttribute("username").toString();
		
	}
	
	public static String getUserSession(){
		
		HttpSession session = getSession();
		
		if (session != null){
			return (String) session.getAttribute("username");
		}else{
			return null;
		}
	}
	
	public static ResourceBundle getConfig(){
		
		ResourceBundle bundle = ResourceBundle.getBundle("br.com.mensagem.bundle.Mensagens");
		return bundle;
	}
	
	public static String getProperty(String key){
		
		return getConfig().getString(key);
	}

}
