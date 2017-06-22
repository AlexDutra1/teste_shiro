package br.com.controller;

import javax.inject.Inject;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.realm.Realm;

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
 
		//this.service.getUsuarioService().getNegocios().getDao().autenticar(this.formulario.getUsuario());
		
		//this.service.getUsuarioService().getNegocios().getDao().consultarTodosUsuariosDAO()
		
		String credencial="1";
		
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
	   //valido o token 
	     //...
	   //tudo ok com ele
	    return true;  
	   //problema com o token
	    //return false;
	 
	}



	public LoginService getService() {
		return service;
	}



	public void setService(LoginService service) {
		this.service = service;
	}

}
