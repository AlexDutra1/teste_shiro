package br.com.modelo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="tb_endereco")
@SequenceGenerator(name="ENDERECO_SEQUENCE", sequenceName="ENDERECO_SEQUENCE", allocationSize=1, initialValue=0)
public class Endereco implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ENDERECO_SEQUENCE")
	private Long idEndereco;

	private String lagradouro;
	
	private String quadra;
	
	private String lote;
	
	private String numero;
	
	private String cep;
	
	private String bairro;
	
	//TORNA BIDIRECIONAL
	@OneToOne(mappedBy = "endereco")
	private Cliente cliente;
	
	/*
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = false, targetEntity = ProgramaTrabalho.class)
	@JoinColumn(insertable = true, unique = false, name = "ID_PROGRAMA_TRABALHO", updatable = true, nullable = false)
	*/
	
	//RELACIONAMENTO ESTADO
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "idEstado", nullable=false)
	private Estado estado;
	
	/*
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = false, targetEntity = Estado.class)
	@JoinColumn(insertable = true, unique = true, name = "idEstado", updatable = true, nullable = false)
	private Estado estado;
	*/
	
	/*
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = false, targetEntity = Estado.class)
	@JoinColumn(insertable = true, unique = true, name = "idEstado", updatable = true, nullable = false)
	private Estado estado;
	*/
	
	//RELACIONAMENTO MUNICIPIO
	@OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name="municipio_id", nullable=true)
    @PrimaryKeyJoinColumn
	private Municipio municipio;
	
	public Long getIdEndereco() {
		return idEndereco;
	}

	public void setIdEndereco(Long idEndereco) {
		this.idEndereco = idEndereco;
	}

	public String getLagradouro() {
		return lagradouro;
	}

	public void setLagradouro(String lagradouro) {
		this.lagradouro = lagradouro;
	}

	public String getQuadra() {
		return quadra;
	}

	public void setQuadra(String quadra) {
		this.quadra = quadra;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	

}
