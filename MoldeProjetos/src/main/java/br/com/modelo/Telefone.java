package br.com.modelo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tb_telefone")
public class Telefone implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
	private Long idTelefone;
	
	private String ddd;
	
	private String numero;
	
	//Torna o relacionamento Bidirecional
	@ManyToOne(cascade=CascadeType.ALL)
    private Cliente cliente; 

	public Long getIdTelefone() {
		return idTelefone;
	}

	public void setIdTelefone(Long idTelefone) {
		this.idTelefone = idTelefone;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	
}
