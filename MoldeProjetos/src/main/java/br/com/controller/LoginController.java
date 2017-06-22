package br.com.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import br.com.controller.formulario.LoginFormulario;
import br.com.servico.LoginService;


@Named("loginController")
@ApplicationScoped
public class LoginController implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private LoginService service;
	
	@Inject
	private LoginFormulario formulario;
	
	/*public String autenticar(){
		
		
		 AUTENTICAO ESTATICA
		System.out.println("Clicado");
		System.out.println("Usuario: "+this.formulario.getUsuario().getUsuario());
		System.out.println("Senha: "+this.formulario.getUsuario().getSenha());
		
		//Acessa o metodo de autenticacao do UsuarioDAO
		String resultado=this.service.getUsuarioService().getNegocios().getDao().autenticar(this.formulario.getUsuario());

		if(resultado.equals("autenticado")){
			return "autenticacao_ok";
		}else{
			return "recusado";
		}
		
		
		return null;
	}*/
	
	
	public void autenticar() throws IOException {
		
        FacesContext ctx = FacesContext.getCurrentInstance();
        
        //UsernamePasswordToken token = new UsernamePasswordToken(usuario.getEmail(), usuario.getSenha());
        UsernamePasswordToken token = new UsernamePasswordToken(this.formulario.getUsuario().getNome(),
        		this.formulario.getUsuario().getSenha());
        
        System.out.println("TESTEEE METODO AUTENTICAR");
        
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.login(token);
            //ctx.getExternalContext().redirect("secure/index.xhtml");
            ctx.getExternalContext().redirect("secure/index.jsp");
        } catch (AuthenticationException ae) {
 
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Usuário/senha inválido(s)!", "Usuário/senha inválido(s)!"));
        }
 
    }

	public LoginService getService() {
		return service;
	}

	public void setService(LoginService service) {
		this.service = service;
	}

	public LoginFormulario getFormulario() {
		return formulario;
	}

	public void setFormulario(LoginFormulario formulario) {
		this.formulario = formulario;
	}

	
	}
