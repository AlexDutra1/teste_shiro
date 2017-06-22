package br.com.controller;

import java.util.List;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.realm.Realm;

import br.com.modelo.Usuario;
import br.com.servico.LoginService;

public class MeuRealm implements Realm {

	@Inject
	private LoginService service;
	
	
	
	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
		    throws AuthenticationException {
		
		String principal = (String) token.getPrincipal();
		 
        //MeuDAO dao = new MeuDAO();
		//String credencial = dao.buscarCredencial(principal);
		
	
		//Implementacao
		/*
		Primeiro você verifica se existe o usuário contido no Principal do seu token
		e retorna a credencial dele
		*/
		
		String credencial=this.service.getUsuarioService().getNegocios().getDao().buscarCredencial(principal);
		
		
        if(credencial != null) {
             AuthenticationInfo info = new SimpleAuthenticationInfo(principal,credencial,getName());
             SimpleCredentialsMatcher cmatcher = new SimpleCredentialsMatcher();
 
             boolean credentialsMatch = cmatcher.doCredentialsMatch(token, info);
             if(credentialsMatch) {
                 return info;
             }
 
         }
         throw new AuthenticationException();
	}
	
	

	@Override
	public String getName() {
	 
	return "MeuApp";
	}
	 
	@Override
	public boolean supports(AuthenticationToken token) {
	   
		//String credencial=this.service.getUsuarioService().getNegocios().getDao().buscarCredencial(principal);
		/*
		if(credencial.equals(token)){
			return true;  
		}else{
			return false;
		}
		*/
		//valido o token 
	     //...
	   //tudo ok com ele
	    
	   //problema com o token
	    //return false;
		
	 return true;
	}



	public LoginService getService() {
		return service;
	}



	public void setService(LoginService service) {
		this.service = service;
	}

}
