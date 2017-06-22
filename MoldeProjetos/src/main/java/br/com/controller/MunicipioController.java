package br.com.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import com.sun.faces.facelets.tag.jstl.core.ForEachHandler;

import br.com.controller.formulario.MunicipioFormulario;
import br.com.modelo.Estado;
import br.com.modelo.Municipio;
import br.com.servico.MunicipioService;

@Named("municipioController")
@RequestScoped
public class MunicipioController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String abreCadastrar="cadastroMunicipio";
	
	@Inject
	private MunicipioService service;

	@Inject
	private MunicipioFormulario formulario;

	private String titulo_pagina;
	private boolean habilitarEditar = false;
	private boolean esconderBotoes = true;
	
	//É chamado a cada requisição , já que o é escopo da bean é request
	/*
	@PostConstruct
	public void init() {
		this.getFormulario().setTodosEstados(this.getService().getEstadoService()
				.getNegocios().getDao().todosEstadosCombo());
	}
	 */
	
	//Nao executa o conteudo do bloco , apenas redireciona
	public String abreCadastro() {
		
		this.getFormulario().setTodosEstados(this.getService().getEstadoService().getNegocios().getDao().todosEstadosCombo());
		
		List <Estado> listat=this.getService().getEstadoService().getNegocios().getDao().todosEstadosCombo();
		for (Estado estado : listat) {
			System.out.println("TESTEEEE "+estado.getNome() );
		}
		
		return abreCadastrar;
	}
	
	public String abreTela(String tela) {
		return tela;
	}

	public String iniciarDados() {
		
		// Carrega Estados na Combobox
		this.getFormulario().setTodosEstados(this.getService().getEstadoService()
				.getNegocios().getDao().todosEstadosCombo());
	
		return null;
	}

	public String abrePesquisa() {

		// Limpa formulario para pesquisa
		// this.formulario.getMunicipio().setNome("");
		// this.formulario.setEstadoSelecionado(null);

		// Tentativa de atualização do painel-depois de usar o voltar painel não
		// funciona
		RequestContext.getCurrentInstance().update(":menuForm");
		RequestContext.getCurrentInstance().update("menuForm");
		RequestContext.getCurrentInstance().update("layout_padrao");
		RequestContext.getCurrentInstance().update("layout_padrao:menuForm");
		RequestContext.getCurrentInstance().update(":layout_padrao");

		// Carrega Estados na Combobox
		this.formulario.setTodosEstados(this.getService().getEstadoService()
				.getNegocios().getDao().todosEstadosCombo());
		this.formulario.setTodosMunicipios(this.getService().getNegocios()
				.getDao().consultaTodosMunicipios());

		return "pesquisaMunicipio";
	}

	public void acaoAposCadastrar() {

		// this.formulario.getEstadoSelecionado().setMunicipios(null);

		// Associa municipio ao estado
		this.getFormulario().getMunicipio().setEstado(
				this.getFormulario().getEstadoSelecionado());

		// Salva no banco de dados o municipio associado ao estado
		this.getService().getNegocios().getDao()
				.guardar(this.getFormulario().getMunicipio());

		// Limpa campos do municipio
		this.getFormulario().setMunicipio(new Municipio());
		this.getFormulario().setEstadoSelecionado(new Estado());
		this.getFormulario().setTodosEstados(new ArrayList<Estado>());
	}

	

	public void pesquisar() {

		// Criar metodo que limpa formulario
		this.getFormulario().setMunicipio(null);
		this.getFormulario().setEstadoSelecionado(null);

		System.out.println("TESTANDO MUNICIPIO: "
				+ this.formulario.getMunicipio().getNome());
		System.out.println("TESTANDO ESTADO: "
				+ this.formulario.getEstadoSelecionado().getNome());

		this.getFormulario().setTodosMunicipios(this.service
				.getNegocios()
				.getDao()
				.pesquisaComCriterios(this.getFormulario().getMunicipio(),
						this.getFormulario().getEstadoSelecionado()));

		// ATUALIZA TABELA E CAMPO DE PESQUISA
		RequestContext.getCurrentInstance().update(
				Arrays.asList("formPesquisaMunicipios:tabelaMunicipio"));

		/*
		 * //Limpa campos apos cadastro
		 * this.formulario.getUsuario().setNome("");
		 * this.formulario.getUsuario().setEmail("");
		 * this.formulario.getUsuario().setUsuario("");
		 * 
		 * //ATUALIZA CAMPOS RequestContext.getCurrentInstance().update(
		 * "formPesquisaUsuario:input_nome");
		 * RequestContext.getCurrentInstance()
		 * .update("formPesquisaUsuario:input_usuario");
		 * RequestContext.getCurrentInstance
		 * ().update("formPesquisaUsuario:input_email");
		 */

	}

	public String visualizar(Municipio municipioVisualizar) {

		// Configura nome da pagina
		this.titulo_pagina = MunicipioFormulario.VISUALIZACAO_MUNICIPIO;

		// Esconde botão Alterar
		this.esconderBotoes = false;

		// Desabilita campo para edição
		this.habilitarEditar = true;

		this.getFormulario().setMunicipio(municipioVisualizar);
		this.getFormulario().setEstadoSelecionado(municipioVisualizar.getEstado());

		return "editarMunicipio";
	}

	public void excluirRegistro(Municipio municipioExcluir) {

		this.service.getNegocios().getDao().excluir(municipioExcluir);

	}

	public String preparaEdicao(Municipio municipioEditar) {

		// Configura nome da pagina
		this.titulo_pagina = MunicipioFormulario.EDICAO_MUNICIPIO;

		// Habilita campo para edição
		this.habilitarEditar = false;

		// Exibe botão Alterar
		this.esconderBotoes = true;

		this.getFormulario().setMunicipio(municipioEditar);
		this.getFormulario().setEstadoSelecionado(municipioEditar.getEstado());

		System.out.println("ESTADO DESSE MUNICIPIO: "
				+ municipioEditar.getEstado().getNome());
		System.out.println("ESTADO DESSE MUNICIPIO: "
				+ municipioEditar.getEstado().getId());
		System.out
				.println("NOME DESSE MUNICIPIO: " + municipioEditar.getNome());

		// Tentativa de atualização do combobo
		RequestContext.getCurrentInstance().update(":estado");

		return "editarMunicipio";
	}

	public void atualizaComboEstado(AjaxBehaviorEvent event) {
		/*
		 * this.formulario.setTodosEstados(this.service.getEstadoService()
		 * .getNegocios().getDao().consultaEstadoPeloNomeCombo(this.formulario
		 * .getEstadoSelecionado()));
		 */

		/*
		 * this.formulario.setTodosMunicipios(this.service.getMunicipioService()
		 * .getNegocios().getDao()
		 * .consultaMunicipiosPeloEstado(this.formulario.
		 * getEstadoSelecionado()));
		 */
	}

	// GETS AND SETTERS

	public MunicipioService getService() {
		return service;
	}

	public void setService(MunicipioService service) {
		this.service = service;
	}

	public MunicipioFormulario getFormulario() {
		return formulario;
	}

	public void setFormulario(MunicipioFormulario formulario) {
		this.formulario = formulario;
	}

	public String getTitulo_pagina() {
		return titulo_pagina;
	}

	public void setTitulo_pagina(String titulo_pagina) {
		this.titulo_pagina = titulo_pagina;
	}

	public boolean isHabilitarEditar() {
		return habilitarEditar;
	}

	public void setHabilitarEditar(boolean habilitarEditar) {
		this.habilitarEditar = habilitarEditar;
	}

	public boolean isEsconderBotoes() {
		return esconderBotoes;
	}

	public void setEsconderBotoes(boolean esconderBotoes) {
		this.esconderBotoes = esconderBotoes;
	}


}
