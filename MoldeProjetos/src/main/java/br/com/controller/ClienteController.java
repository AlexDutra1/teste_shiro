package br.com.controller;

import java.io.Serializable;
import java.util.Arrays;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.controller.formulario.ClienteFormulario;
import br.com.modelo.Cliente;
import br.com.servico.ClienteService;


@Named("clienteController")
@ApplicationScoped
public class ClienteController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final String abreIncluir ="incluirClientes";
	private static final String abrePesquisar="pesquisarClientes";

	@Inject
	private ClienteService service;
	
	@Inject
	private ClienteFormulario formulario;
	
	public String abreIncluir(){
		
		limparFormulario();
		this.formulario.setTodosEstados(this.getService().getEstadoService().getNegocios().getDao().todosEstadosCombo());
		this.formulario.setTodosMunicipios(this.getService().getMunicipioService().getNegocios().getDao().consultaTodosMunicipios());
		
		return abreIncluir;
	}
	
	public String abrePesquisa(){
		
		limparFormulario();
		this.formulario.setTodosEstados(this.getService().getEstadoService().getNegocios().getDao().todosEstadosCombo());
		this.formulario.setTodosMunicipios(this.getService().getMunicipioService().getNegocios().getDao().consultaTodosMunicipios());
		this.formulario.setTodosClientes(this.getService().getNegocios().getDao().consultarTodosDAO());
		
		return "pesquisarClientes";
	}
	
	public void limparFormulario(){
		this.formulario.setCliente(new Cliente());
	}
	
	public void acaoAposAlterar(){
		
		//Salva no banco de dados
		this.service.getNegocios().getDao().guardar(this.getFormulario().getCliente());
		this.limparFormulario();
		
	}
	
	/*
	@PostConstruct
	public void init(){
		
		this.formulario.setTodosEstados(this.getService().getEstadoService().getNegocios().getDao().todosEstadosCombo());
		this.formulario.setTodosMunicipios(this.getService().getMunicipioService().getNegocios().getDao().consultaTodosMunicipios());
		this.formulario.setTodosClientes(this.getService().getNegocios().getDao().consultarTodosDAO());
	
	}
	*/
	
	public void acaoAposCadastrar(){
		
		//Salva no banco de dados
		this.service.getNegocios().getDao().guardar(this.getFormulario().getCliente());
		
		//Cria outro objeto cliente para ser preenchido novamente
		this.formulario.setCliente(new Cliente());
		
	}	
	
	public void excluirRegistro(Cliente clienteExcluir){
		
		this.service.getNegocios().getDao().excluir(clienteExcluir);
		
		this.formulario.setTodosClientes(this.service.getNegocios().getDao().consultarTodosDAO());
		
		//ATUALIZA TABELA E CAMPO DE PESQUISA
		RequestContext.getCurrentInstance().update(Arrays.asList("formPesquisaCliente:tabelaClientes"));
	
	}
	
	
	public String preparaEdicao(Cliente clienteEditar){
		
		this.formulario.setCliente(clienteEditar);
		
		//Carrega lista toda de telefones
		this.formulario.setListaTelefones(clienteEditar.getTelefone());
	
		
		return "editarCliente";
	}
	
	
	

	//CONSULTAS
	public void pesquisar(){
		
	
		//Faz consulta pelo nome
		this.formulario.setTodosClientes(this.service
				.getNegocios().getDao()
				.consultaPorCriterios(
						this.formulario.getCliente(),
						this.formulario.getEnumGenero(),
						this.formulario.getTelefone(),
						this.formulario.getEndereco(),
						this.formulario.getEstadoSelecionado(),
						this.formulario.getMunicipioSelecionado()
						));
		/*
		System.out.println("TESTE 1: "+this.formulario.getCliente().getDataNascimento());
		
		if(this.formulario.getCliente().getDataNascimento()==null){
			System.out.println("É NULO "+this.formulario.getCliente().getDataNascimento());
		}else{
			System.out.println("NÃO É NULO "+this.formulario.getCliente().getDataNascimento());
		}
		*/
		//.name da nullpointer
		//System.out.println("TESTE 2: "+this.formulario.getEnumGenero().name());
		
		//ATUALIZA TABELA E CAMPO DE PESQUISA
		RequestContext.getCurrentInstance().update(Arrays.asList("formPesquisaCliente:tabelaClientes"));
		RequestContext.getCurrentInstance().update("formPesquisaCliente:input_nome");
		
		//Limpa campos apos cadastro
		this.formulario.getCliente().setNome("");
	
	}
	
	public ClienteService getService() {
		return service;
	}




	public void setService(ClienteService service) {
		this.service = service;
	}




	public ClienteFormulario getFormulario() {
		return formulario;
	}




	public void setFormulario(ClienteFormulario formulario) {
		this.formulario = formulario;
	}

	
	




}
