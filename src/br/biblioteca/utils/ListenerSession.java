package br.biblioteca.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ListenerSession implements HttpSessionListener {
	
	Logger logger = Logger.getLogger(ListenerSession.class.getName());
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub		
		logger.log(Level.INFO, "Sessão criada: {0}", se.getSession().getId());
		
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		

        String ultimoAcesso = (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(new Date(se.getSession().getLastAccessedTime()));
        logger.log(Level.INFO, "Sessão expirada: {0}", se.getSession().getId() + ultimoAcesso);
        logger.log(Level.INFO, "Ultimo Acesso: {0}", ultimoAcesso);        
	}

}
